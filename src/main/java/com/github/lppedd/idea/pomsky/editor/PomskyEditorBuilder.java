package com.github.lppedd.idea.pomsky.editor;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.ex.EditorGutterComponentEx;
import com.intellij.openapi.fileEditor.AsyncFileEditorProvider;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.fileEditor.TextEditorWithPreview;
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFileFactory;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.intellij.lang.regexp.RegExpLanguage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

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
    final var mainEditor = (TextEditor) TextEditorProvider.getInstance().createEditor(project, file);
    final var previewEditor = getPreviewEditor();
    return new PomskyEditorWithPreview(mainEditor, previewEditor, TextEditorWithPreview.Layout.SHOW_EDITOR);
  }

  @NotNull
  private TextEditor getPreviewEditor() {
    final var psiFileFactory = PsiFileFactory.getInstance(project);
    final var inMemoryPsiFile = psiFileFactory.createFileFromText(RegExpLanguage.INSTANCE, "");
    final var inMemoryVirtualFile = inMemoryPsiFile.getVirtualFile();
    final var textEditor = (TextEditor) TextEditorProvider.getInstance().createEditor(project, inMemoryVirtualFile);
    final var editor = textEditor.getEditor();
    initEditor(editor);
    return textEditor;
  }

  private void initEditor(@NotNull final Editor editor) {
    editor.setHeaderComponent(getHeaderComponent());

    final var settings = editor.getSettings();
    settings.setCaretRowShown(false);
    settings.setLineNumbersShown(false);
    settings.setLineMarkerAreaShown(false);
    settings.setGutterIconsShown(false);
    settings.setFoldingOutlineShown(false);
    settings.setIndentGuidesShown(false);

    if (editor instanceof EditorEx) {
      final var editorEx = (EditorEx) editor;
      editorEx.setViewer(true);
      editorEx.setPermanentHeaderComponent(getHeaderComponent());

      final var gutterComponentEx = editorEx.getGutterComponentEx();
      gutterComponentEx.setRightFreePaintersAreaWidth(0);
      gutterComponentEx.setForceShowRightFreePaintersArea(false);
      gutterComponentEx.setLeftFreePaintersAreaWidth(0);
      gutterComponentEx.setForceShowLeftFreePaintersArea(false);

      try {
        hackEditorGutterLayout(gutterComponentEx);
        gutterComponentEx.setPaintBackground(false);
      } catch (final Exception e) {
        logger.error("Error while hacking the preview editor gutter component", e);
      }
    }
  }

  @NotNull
  private JComponent getHeaderComponent() {
    return new PomskyPreviewEditorHeader();
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
            ? Integer.valueOf(4)
            : proxy.invokeSuper(obj, args)
    );

    final var newLayout = enhancer.create(
        new Class[] {gutterComponent.getClass()},
        new Object[] {gutterComponent}
    );

    layoutField.set(gutterComponent, newLayout);
  }
}
