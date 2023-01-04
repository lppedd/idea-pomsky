package com.github.lppedd.idea.pomsky;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Entry point for notifying Pomsky-related info to the user.
 *
 * @author Edoardo Luppi
 */
@Service(Service.Level.PROJECT)
public final class PomskyNotifier {
  public static final String GROUP_ERROR_COMPILE = "com.github.lppedd.idea.pomsky.error.compile";

  private final Project project;

  PomskyNotifier(@NotNull final Project project) {
    this.project = project;
  }

  public void notifyError(
      @NotNull final String groupId,
      @NotNull final String subtitle,
      @NotNull final String content,
      @NotNull final Collection<AnAction> actions) {
    NotificationGroupManager.getInstance()
        .getNotificationGroup(groupId)
        .createNotification("Pomsky", content, NotificationType.ERROR)
        .setSubtitle(subtitle)
        .addActions(actions)
        .notify(project);
  }

  @NotNull
  public static PomskyNotifier getInstance(@NotNull final Project project) {
    return project.getService(PomskyNotifier.class);
  }
}
