package com.github.lppedd.idea.pomsky.editor;

import com.github.lppedd.idea.pomsky.Workaround;
import com.intellij.ide.ui.UISettingsListener;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.fileEditor.TextEditorWithPreview;
import com.intellij.openapi.project.DumbAware;
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
    ApplicationManager.getApplication()
        .getMessageBus()
        .connect(this)
        .subscribe(UISettingsListener.TOPIC, (UISettingsListener) settings -> {
          if (!isDisposed()) {
            hackSplitterWidth();
          }
        });
  }

  /**
   * Returns whether the editor has been disposed.
   */
  public boolean isDisposed() {
    return isDisposed;
  }

  @Override
  protected boolean isShowActionsInTabs() {
    return true;
  }

  @NotNull
  @Override
  protected AnAction @NotNull [] createTabActions() {
    return new AnAction[] {
        getShowEditorAction(),
        new PomskyChangeViewModeAction(Layout.SHOW_EDITOR_AND_PREVIEW)
    };
  }

  @NotNull
  @Override
  public JComponent getComponent() {
    final var component = super.getComponent();
    hackSplitterWidth();
    return component;
  }

  @Override
  public void dispose() {
    isDisposed = true;
    super.dispose();
  }

  @Workaround
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

  /**
   * The original action is {@code TextEditorWithPreview$ChangeViewModeAction}.
   * <p>
   * This is an enhanced version that allows toggling horizontal/vertical split.
   */
  private class PomskyChangeViewModeAction extends ToggleAction implements DumbAware {
    private final Layout myActionLayout;

    PomskyChangeViewModeAction(@NotNull final Layout layout) {
      super(layout.getName(), layout.getName(), layout.getIcon(PomskyEditorWithPreview.this));
      myActionLayout = layout;
    }

    @Override
    public boolean isSelected(@NotNull final AnActionEvent e) {
      return getLayout() == myActionLayout;
    }

    @NotNull
    @Override
    public ActionUpdateThread getActionUpdateThread() {
      return ActionUpdateThread.BGT;
    }

    @Override
    public void setSelected(@NotNull final AnActionEvent e, final boolean state) {
      if (state) {
        setLayout(myActionLayout);
      } else if (myActionLayout == Layout.SHOW_EDITOR_AND_PREVIEW) {
        setVerticalSplit(!isVerticalSplit());
      }
    }

    @Override
    public void update(@NotNull final AnActionEvent e) {
      super.update(e);
      e.getPresentation().setIcon(myActionLayout.getIcon(PomskyEditorWithPreview.this));
    }
  }
}
