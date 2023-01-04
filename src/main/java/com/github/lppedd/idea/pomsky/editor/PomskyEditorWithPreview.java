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
public class PomskyEditorWithPreview extends TextEditorWithPreview {
  private boolean isDisposed;

  PomskyEditorWithPreview(
      @NotNull final TextEditor editor,
      @NotNull final FileEditor preview) {
    super(editor, preview, "Pomsky Editor", Layout.SHOW_EDITOR_AND_PREVIEW, false);
  }

  /**
   * Returns whether the editor has been disposed.
   */
  public boolean isDisposed() {
    return isDisposed;
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

  @Override
  public void dispose() {
    isDisposed = true;
    super.dispose();
  }
}
