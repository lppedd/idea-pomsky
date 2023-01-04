package com.github.lppedd.idea.pomsky;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Entry point for notifying Pomsky-related info to the user.
 *
 * @author Edoardo Luppi
 */
@Service
public final class PomskyNotifier {
  public static final String GROUP_ERROR_COMPILE = "com.github.lppedd.idea.pomsky.error.compile";

  private final Project project;

  PomskyNotifier(@NotNull final Project project) {
    this.project = project;
  }

  public void notifyError(
      @NotNull final String groupId,
      @NotNull final String subtitle,
      @NotNull final String content) {
    NotificationGroupManager.getInstance()
        .getNotificationGroup(groupId)
        .createNotification("Pomsky", content, NotificationType.ERROR)
        .setSubtitle(subtitle)
        .notify(project);
  }

  @NotNull
  public static PomskyNotifier getInstance(@NotNull final Project project) {
    return project.getService(PomskyNotifier.class);
  }
}
