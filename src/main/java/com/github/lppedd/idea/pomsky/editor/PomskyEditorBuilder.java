package com.github.lppedd.idea.pomsky.editor;

import com.github.lppedd.idea.pomsky.PomskyTopics;
import com.github.lppedd.idea.pomsky.process.PomskyCompileListener;
import com.github.lppedd.idea.pomsky.process.PomskyCompileResult;
import com.github.lppedd.idea.pomsky.process.PomskyRegexpFlavor;
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
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.HyperlinkAdapter;
import com.intellij.util.ui.JBUI;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.intellij.lang.regexp.RegExpFileType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.event.ItemEvent;

/**
 * @author Edoardo Luppi
 */
class PomskyEditorBuilder extends AsyncFileEditorProvider.Builder {
  private static final Logger logger = Logger.getInstance(PomskyEditorBuilder.class);

  private final Project project;
  private final VirtualFile file;

  PomskyEditorBuilder(
      @NotNull final Project project,
      @NotNull final VirtualFile file) {
    this.project = project;
    this.file = file;
  }

  @NotNull
  @Override
  public FileEditor build() {
    final var langEditor = (TextEditor) TextEditorProvider.getInstance().createEditor(project, file);
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
          final var isCompileEnabled = settings.getCliExecutablePath() != null;
          final var compileHyperlink = previewHeader.getCompileHyperlink();
          compileHyperlink.setEnabled(isCompileEnabled);

          if (!isCompileEnabled) {
            compileHyperlink.setToolTipText("Set the Pomsky CLI executable to compile");
          }
        }
      }
    });

    connection.subscribe(PomskyTopics.TOPIC_COMPILE, new PomskyCompileListener() {
      @Override
      public void compileRequested(@NotNull final VirtualFile compiledFile) {
        updateUIState(compiledFile, false);
      }

      @Override
      public void compileFinished(
          @NotNull final VirtualFile compiledFile,
          @NotNull final PomskyCompileResult result) {
        if (compositeEditor.isDisposed() || !compiledFile.equals(file)) {
          return;
        }

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

        previewHeader.getCompileHyperlink().setEnabled(true);
      }

      @Override
      public void compileCanceled(@NotNull final VirtualFile compiledFile) {
        updateUIState(compiledFile, true);
      }

      @Override
      public void compileFailed(
          @NotNull final VirtualFile compiledFile,
          @NotNull final Throwable error) {
        updateUIState(compiledFile, true);
      }

      void updateUIState(@NotNull final VirtualFile compiledFile, final boolean isEnabled) {
        if (!compositeEditor.isDisposed() && compiledFile.equals(file)) {
          final var compileHyperlink = previewHeader.getCompileHyperlink();
          compileHyperlink.setEnabled(isEnabled);
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
    return Pair.create(TextEditorProvider.getInstance().getTextEditor(editor), header);
  }

  @NotNull
  private PomskyPreviewEditorHeader createHeaderComponent() {
    final var header = new PomskyPreviewEditorHeader();
    final var projectSettings = PomskyProjectSettingsService.getInstance(project);
    final var regexpFlavorComboBox = header.getRegexpFlavorComboBox();
    regexpFlavorComboBox.setItem(projectSettings.getRegexpFlavor());
    regexpFlavorComboBox.addItemListener(e -> {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        projectSettings.setRegexpFlavor((PomskyRegexpFlavor) e.getItem());
      }
    });

    final var compileHyperlink = header.getCompileHyperlink();

    // Enable the hyperlink only if the CLI executable has been set
    if (PomskySettingsService.getInstance().getCliExecutablePath() == null) {
      compileHyperlink.setEnabled(false);
      compileHyperlink.setToolTipText("Set the Pomsky CLI executable to compile");
    }

    compileHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
      @Override
      protected void hyperlinkActivated(final @NotNull HyperlinkEvent e) {
        final var compileService = PomskyCompileEditorService.getInstance(project);
        compileService.compileAndUpdateEditorAsync(file);
      }
    });

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
      logger.error("Error while hacking the preview editor gutter component", e);
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
