package com.github.lppedd.idea.pomsky.editor;

import com.github.lppedd.idea.pomsky.settings.PomskySettingsConfigurable;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.fileEditor.TextEditorWithPreview;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.GotItTooltip;
import com.intellij.util.Alarm;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * @author Edoardo Luppi
 */
class PomskyTooltipsFileEditorManagerListener implements FileEditorManagerListener {
  @Override
  public void fileOpened(
      @NotNull final FileEditorManager manager,
      @NotNull final VirtualFile file) {
    final var editor = manager.getSelectedEditor(file);

    if (editor instanceof final PomskyEditorWithPreview pomskyEditor) {
      final var project = manager.getProject();

      if (pomskyEditor.getLayout() != TextEditorWithPreview.Layout.SHOW_EDITOR) {
        showTooltips(project, pomskyEditor);
      } else {
        pomskyEditor.addLayoutListener((oldValue, newValue) -> {
          if (newValue != TextEditorWithPreview.Layout.SHOW_EDITOR) {
            showTooltips(project, pomskyEditor);
          }
        });
      }
    }
  }

  private void showTooltips(
      @NotNull final Project project,
      @NotNull final PomskyEditorWithPreview pomskyEditor) {
    final var previewEditor = pomskyEditor.getPreviewEditor();

    if (previewEditor instanceof final TextEditor textEditor && textEditor.isValid()) {
      final var header = textEditor.getEditor().getHeaderComponent();

      if (header instanceof final PomskyPreviewEditorHeader pomskyHeader && pomskyHeader.isVisible()) {
        showRegexpFlavorTooltip(pomskyHeader.getRegexpFlavorComboBox(), pomskyEditor);
        showCompileTooltip(project, pomskyHeader.getCompileHyperlink(), pomskyEditor);
      }
    }
  }

  private void showRegexpFlavorTooltip(
      @NotNull final JComponent target,
      @NotNull final Disposable disposable) {
    final var tooltip = new GotItTooltip(
        "com.github.lppedd.idea.pomsky.editor.tooltip.flavor",
        "Easily switch to a different RegExp target",
        disposable
    );

    tooltip.withPosition(Balloon.Position.below);
    tooltip.withMaxWidth(JBUI.scale(300));
    tooltip.withHeader("Flavor");

    new Alarm(disposable).addRequest(() -> {
      if (tooltip.canShow()) {
        tooltip.show(target, (c, b) -> new Point(JBUI.scale(15), c.getHeight() + JBUI.scale(2)));
      }
    }, 300);
  }

  private void showCompileTooltip(
      @NotNull final Project project,
      @NotNull final JComponent target,
      @NotNull final Disposable disposable) {
    final var tooltip = new GotItTooltip(
        "com.github.lppedd.idea.pomsky.editor.tooltip.compile",
        "Compile manually or automatically using Live preview",
        disposable
    );

    tooltip.withPosition(Balloon.Position.below);
    tooltip.withMaxWidth(JBUI.scale(400));
    tooltip.withHeader("Compilation");
    tooltip.withLink("Open Settings", () -> {
      final var settingsUtil = ShowSettingsUtil.getInstance();
      settingsUtil.showSettingsDialog(project, PomskySettingsConfigurable.class);
    });

    new Alarm(disposable).addRequest(() -> {
      if (tooltip.canShow()) {
        tooltip.show(target, (c, b) -> new Point(c.getWidth() / 2, c.getHeight() + JBUI.scale(2)));
      }
    }, 300);
  }
}
