package com.github.lppedd.idea.pomsky.editor;

import com.github.lppedd.idea.pomsky.PomskyIcons;
import com.github.lppedd.idea.pomsky.lang.PomskyFileType;
import com.github.lppedd.idea.pomsky.settings.PomskySettingsService;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
class PomskyCompileEditorAction extends DumbAwareAction {
  private static final Logger logger = Logger.getInstance(PomskyCompileEditorAction.class);

  @NotNull
  @Override
  public ActionUpdateThread getActionUpdateThread() {
    return ActionUpdateThread.EDT;
  }

  @Override
  public void update(@NotNull final AnActionEvent e) {
    final var presentation = e.getPresentation();
    final var project = e.getProject();

    if (project == null) {
      logger.error("Could not show compile action because Project is null");
      presentation.setEnabledAndVisible(false);
      return;
    }

    final var file = e.getData(CommonDataKeys.VIRTUAL_FILE);

    if (file == null) {
      logger.error("Could not show compile action because VirtualFile is null");
      presentation.setEnabledAndVisible(false);
      return;
    }

    final var isVisible = FileTypeManager.getInstance().isFileOfType(file, PomskyFileType.INSTANCE);
    presentation.setVisible(isVisible);
    presentation.setIcon(PomskyIcons.LOGO);

    // The action must be enabled only if a Pomsky CLI executable has been set.
    // Obviously we are not sure if it's still valid at this point,
    // so we will throw an error afterward in case, shown to the user via notifications
    final var isExecutableSet = isVisible && PomskySettingsService.getInstance().getCliExecutablePath() != null;
    presentation.setEnabled(isExecutableSet && PomskyCompileEditorService.getInstance(project).canCompile(file));
  }

  @Override
  public void actionPerformed(@NotNull final AnActionEvent e) {
    final var project = e.getProject();

    if (project == null) {
      logger.error("Could not request compilation because Project is null");
      return;
    }

    final var virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);

    if (virtualFile == null) {
      logger.error("Could not request compilation because VirtualFile is null");
      return;
    }

    final var compileEditorService = PomskyCompileEditorService.getInstance(project);
    compileEditorService.compileAndUpdateEditorAsync(virtualFile);
  }
}
