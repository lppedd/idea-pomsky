package com.github.lppedd.idea.pomsky.editor;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.fileEditor.TextEditorWithPreview;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
class PomskyEditorWithPreview extends TextEditorWithPreview {
  public PomskyEditorWithPreview(
      @NotNull final TextEditor editor,
      @NotNull final FileEditor preview,
      @NotNull final Layout defaultLayout) {
    super(editor, preview, "Pomsky Editor", defaultLayout, false);
  }

  @NotNull
  @Override
  protected ActionGroup createViewActionGroup() {
    return new DefaultActionGroup(
        getShowEditorAction(),
        getShowEditorAndPreviewAction()
    );
  }

  @Override
  protected boolean isShowFloatingToolbar() {
    return false;
  }
}
