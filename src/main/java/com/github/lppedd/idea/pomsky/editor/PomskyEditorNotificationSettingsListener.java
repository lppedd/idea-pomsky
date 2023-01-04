package com.github.lppedd.idea.pomsky.editor;

import com.github.lppedd.idea.pomsky.settings.PomskySettingsListener;
import com.github.lppedd.idea.pomsky.settings.PomskySettingsService;
import com.intellij.openapi.project.Project;
import com.intellij.ui.EditorNotifications;
import org.jetbrains.annotations.NotNull;

/**
 * Refreshes the Pomsky editor notification banners when application-level settings are changed.
 *
 * @author Edoardo Luppi
 */
class PomskyEditorNotificationSettingsListener implements PomskySettingsListener {
  private final Project project;

  PomskyEditorNotificationSettingsListener(@NotNull final Project project) {
    this.project = project;
  }

  @Override
  public void settingsChanged(@NotNull final PomskySettingsService settings) {
    EditorNotifications.getInstance(project).updateAllNotifications();
  }
}
