package com.github.lppedd.idea.pomsky.editor;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.fileEditor.TextEditorWithPreview;
import com.intellij.openapi.ui.Splitter;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Edoardo Luppi
 */
public class PomskyEditorWithPreview extends TextEditorWithPreview {
  private static final Logger logger = Logger.getInstance(PomskyEditorWithPreview.class);
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
  public JComponent getComponent() {
    final var component = super.getComponent();
    hackSplitterWidth();
    return component;
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

  private void hackSplitterWidth() {
    try {
      final var splitterField = TextEditorWithPreview.class.getDeclaredField("mySplitter");
      splitterField.trySetAccessible();

      final var splitter = (Splitter) splitterField.get(this);
      splitter.setDividerWidth(JBUI.scale(3));
    } catch (final NoSuchFieldException | IllegalAccessException e) {
      logger.error("Error while overriding the splitter width", e);
    }
  }
}
