package com.github.lppedd.idea.pomsky.editor;

import com.github.lppedd.idea.pomsky.PomskyNotifier;
import com.github.lppedd.idea.pomsky.process.PomskyCompileListener;
import com.github.lppedd.idea.pomsky.process.PomskyProcessException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * Notifies the user of important compilation errors.
 *
 * @author Edoardo Luppi
 */
class PomskyNotificationCompileListener implements PomskyCompileListener {
  private final Project project;

  PomskyNotificationCompileListener(@NotNull final Project project) {
    this.project = project;
  }

  @Override
  public void compileFailed(
      @NotNull final VirtualFile compiledFile,
      @NotNull final Throwable error) {
    if (error instanceof PomskyProcessException && error.getCause() == null) {
      final var subtitle = getPresentableFileName(compiledFile);
      final var content = error.getMessage();
      final var notifier = PomskyNotifier.getInstance(project);
      notifier.notifyError(PomskyNotifier.GROUP_ERROR_COMPILE, subtitle, content);
    }
  }

  @NotNull
  private String getPresentableFileName(@NotNull final VirtualFile file) {
    final var name = file.getName();
    return name.toLowerCase().contains("pomsky fragment")
        ? "Fragment"
        : name;
  }
}
