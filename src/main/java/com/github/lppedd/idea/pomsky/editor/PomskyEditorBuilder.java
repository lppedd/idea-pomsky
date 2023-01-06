package com.github.lppedd.idea.pomsky.editor;

import com.github.lppedd.idea.pomsky.PomskyTopics;
import com.github.lppedd.idea.pomsky.Workaround;
import com.github.lppedd.idea.pomsky.process.PomskyCompileListener;
import com.github.lppedd.idea.pomsky.process.PomskyCompileResult;
import com.github.lppedd.idea.pomsky.settings.PomskyProjectSettingsService;
import com.github.lppedd.idea.pomsky.settings.PomskySettingsListener;
import com.github.lppedd.idea.pomsky.settings.PomskySettingsService;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.EditorKind;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.ex.EditorGutterComponentEx;
import com.intellij.openapi.editor.highlighter.EditorHighlighterFactory;
import com.intellij.openapi.fileEditor.AsyncFileEditorProvider;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.fileTypes.PlainTextFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.concurrency.AppExecutorUtil;
import com.intellij.util.ui.JBUI;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.intellij.lang.regexp.RegExpFileType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.concurrent.Future;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * @author Edoardo Luppi
 */
class PomskyEditorBuilder extends AsyncFileEditorProvider.Builder {
  private static final Logger logger = Logger.getInstance(PomskyEditorBuilder.class);
  private static final Key<Future<?>> KEY_LOADING = Key.create("pomskyShowLoadingFuture");

  private final Project project;
  private final VirtualFile virtualFile;

  PomskyEditorBuilder(
      @NotNull final Project project,
      @NotNull final VirtualFile virtualFile) {
    this.project = project;
    this.virtualFile = virtualFile;
  }

  @NotNull
  @Override
  public FileEditor build() {
    final var langEditor = (TextEditor) TextEditorProvider.getInstance().createEditor(project, virtualFile);
    final var previewEditorAndHeader = getPreviewEditor();
    final var previewEditor = previewEditorAndHeader.getFirst();
    final var previewHeader = previewEditorAndHeader.getSecond();
    final var compositeEditor = new PomskyEditorWithPreview(langEditor, previewEditor);

    // We want to receive compilation/settings events to update UI components accordingly.
    // This connection is going to stay open as long as the editor is shown on the screen
    final var messageBus = project.getMessageBus();
    final var connection = messageBus.connect(compositeEditor);
    connection.subscribe(PomskyTopics.TOPIC_SETTINGS, new PomskySettingsListener() {
      @Override
      public void settingsChanged(@NotNull final PomskySettingsService settings) {
        if (!compositeEditor.isDisposed()) {
          if (settings.getCliExecutablePath() != null) {
            previewHeader.setCompileEnabled(true, null);
          } else {
            previewHeader.setCompileEnabled(false, "Set the Pomsky CLI executable to compile");
          }
        }
      }
    });

    connection.subscribe(PomskyTopics.TOPIC_COMPILE, new PomskyCompileListener() {
      @Override
      public void compileRequested(@NotNull final VirtualFile compiledFile) {
        final var regexpFlavor = previewHeader.getRegexpFlavor();
        PomskyProjectSettingsService.getInstance(project).setRegexpFlavor(regexpFlavor);
        updateUI(compiledFile, false);
      }

      @Override
      public void compileFinished(
          @NotNull final VirtualFile compiledFile,
          @NotNull final PomskyCompileResult result) {
        if (compositeEditor.isDisposed() || !compiledFile.equals(virtualFile)) {
          return;
        }

        updateUI(compiledFile, true);

        final var editor = (EditorEx) previewEditor.getEditor();
        final var highlighterFactory = EditorHighlighterFactory.getInstance();

        if (result.hasError()) {
          editor.setHighlighter(highlighterFactory.createEditorHighlighter(project, PlainTextFileType.INSTANCE));
          editor.getSettings().setUseSoftWraps(false);
          WriteAction.run(() -> editor.getDocument().setText(result.getErrorMessage()));
        } else {
          editor.setHighlighter(highlighterFactory.createEditorHighlighter(project, RegExpFileType.INSTANCE));
          editor.getSettings().setUseSoftWraps(true);
          WriteAction.run(() -> editor.getDocument().setText(result.getCompiledRegexp()));
        }
      }

      @Override
      public void compileCanceled(@NotNull final VirtualFile compiledFile) {
        updateUI(compiledFile, true);
      }

      @Override
      public void compileFailed(
          @NotNull final VirtualFile compiledFile,
          @NotNull final Throwable error) {
        updateUI(compiledFile, true);
      }

      void updateUI(@NotNull final VirtualFile file, final boolean isEnabled) {
        if (!compositeEditor.isDisposed() && file.equals(virtualFile)) {
          if (isEnabled) {
            KEY_LOADING.getRequired(file).cancel(false);
            previewHeader.setCompileLoading(false);
          } else {
            final var executor = AppExecutorUtil.getAppScheduledExecutorService();
            final var loadingFuture = executor.schedule(() -> previewHeader.setCompileLoading(true), 400, MILLISECONDS);
            KEY_LOADING.set(file, loadingFuture);
          }

          previewHeader.setCompileEnabled(isEnabled, null);
        }
      }
    });

    return compositeEditor;
  }

  @NotNull
  private Pair<TextEditor, PomskyPreviewEditorHeader> getPreviewEditor() {
    final var editorFactory = EditorFactory.getInstance();
    final var document = editorFactory.createDocument("");
    final var editor = (EditorEx) editorFactory.createViewer(document, project, EditorKind.PREVIEW);
    final var header = createHeaderComponent();
    initEditor(editor, header);

    final var textEditor = TextEditorProvider.getInstance().getTextEditor(editor);
    Disposer.register(textEditor, () -> editorFactory.releaseEditor(editor));
    return Pair.create(textEditor, header);
  }

  @NotNull
  private PomskyPreviewEditorHeader createHeaderComponent() {
    final var header = new PomskyPreviewEditorHeader();
    header.setRegexpFlavor(PomskyProjectSettingsService.getInstance(project).getRegexpFlavor());
    header.addRegexpFlavorListener(regexpFlavor -> {
      final var projectSettings = PomskyProjectSettingsService.getInstance(project);
      projectSettings.setRegexpFlavor(regexpFlavor);
    });

    header.addCompileListener(flavor -> {
      final var projectSettings = PomskyProjectSettingsService.getInstance(project);
      projectSettings.setRegexpFlavor(flavor);

      final var compileService = PomskyCompileEditorService.getInstance(project);
      compileService.compileAndUpdateEditorAsync(virtualFile);
    });

    // Enable the hyperlink only if the CLI executable has been set
    if (PomskySettingsService.getInstance().getCliExecutablePath() == null) {
      header.setCompileEnabled(false, "Set the Pomsky CLI executable to compile");
    }

    return header;
  }

  private void initEditor(
      @NotNull final EditorEx editor,
      @NotNull final JComponent header) {
    editor.setHeaderComponent(header);
    editor.setPermanentHeaderComponent(header);
    editor.setPlaceholder("Waiting for compilation");
    editor.getScrollPane().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

    try {
      final var gutterComponent = editor.getGutterComponentEx();
      hackEditorGutterLayout(gutterComponent);
      gutterComponent.setPaintBackground(false);
    } catch (final Exception e) {
      logger.error("Error while overriding the preview editor's gutter component layout", e);
    }

    final var settings = editor.getSettings();
    settings.setCaretRowShown(false);
    settings.setLineNumbersShown(false);
    settings.setLineMarkerAreaShown(false);
    settings.setGutterIconsShown(false);
    settings.setFoldingOutlineShown(false);
    settings.setIndentGuidesShown(false);
    settings.setRightMarginShown(false);
  }

  @Workaround
  private void hackEditorGutterLayout(@NotNull final EditorGutterComponentEx gutterComponent) throws Exception {
    final var layoutField = gutterComponent.getClass().getDeclaredField("myLayout");
    layoutField.trySetAccessible();

    final var currentLayout = layoutField.get(gutterComponent);
    final var enhancer = new Enhancer();
    enhancer.setClassLoader(currentLayout.getClass().getClassLoader());
    enhancer.setSuperclass(currentLayout.getClass());
    enhancer.setCallback(
        (MethodInterceptor) (obj, method, args, proxy) -> "getWidth".equals(method.getName())
            ? Integer.valueOf(JBUI.scale(4))
            : proxy.invokeSuper(obj, args)
    );

    final var newLayout = enhancer.create(
        new Class[] { gutterComponent.getClass() },
        new Object[] { gutterComponent }
    );

    layoutField.set(gutterComponent, newLayout);
  }
}
