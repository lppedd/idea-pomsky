package com.github.lppedd.idea.pomsky.editor;

import com.github.lppedd.idea.pomsky.lang.PomskyFileType;
import com.github.lppedd.idea.pomsky.settings.PomskySettingsConfigurable;
import com.github.lppedd.idea.pomsky.settings.PomskySettingsService;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.EditorNotificationPanel;
import com.intellij.ui.EditorNotificationProvider;
import com.intellij.ui.EditorNotifications;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.function.Function;

/**
 * Shows an editor banner if the Pomsky CLI executable has not been set.
 *
 * @author Edoardo Luppi
 */
public class PomskyEditorNotificationProvider implements EditorNotificationProvider, DumbAware {
  @Nullable
  @Override
  public Function<? super @NotNull FileEditor, ? extends @Nullable JComponent> collectNotificationData(
      @NotNull final Project project,
      @NotNull final VirtualFile file) {
    if (!FileTypeManager.getInstance().isFileOfType(file, PomskyFileType.INSTANCE)) {
      return null;
    }

    final var settings = PomskySettingsService.getInstance();
    return settings.getCliExecutablePath() != null || !settings.isShowMissingCliExecutableBanner()
        ? null
        : fileEditor -> createPanel(project, fileEditor);
  }

  @NotNull
  private EditorNotificationPanel createPanel(
      @NotNull final Project project,
      @NotNull final FileEditor fileEditor) {
    final var panel = new EditorNotificationPanel(fileEditor, EditorNotificationPanel.Status.Info);
    panel.icon(AllIcons.General.BalloonInformation);
    panel.createActionLabel("Setup CLI", () -> openSettings(project));
    panel.createActionLabel("Dismiss", () -> dismissBanner(project));
    panel.setText("Pomsky CLI executable is not defined");
    return panel;
  }

  private void openSettings(@NotNull final Project project) {
    final var settingsUtil = ShowSettingsUtil.getInstance();
    settingsUtil.showSettingsDialog(project, PomskySettingsConfigurable.class);
    EditorNotifications.getInstance(project).updateAllNotifications();
  }

  private void dismissBanner(@NotNull final Project project) {
    final var settings = PomskySettingsService.getInstance();
    settings.setShowMissingCliExecutableBanner(false);
    EditorNotifications.getInstance(project).updateAllNotifications();
  }
}
