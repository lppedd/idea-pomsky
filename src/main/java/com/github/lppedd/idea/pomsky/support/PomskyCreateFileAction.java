package com.github.lppedd.idea.pomsky.support;

import com.github.lppedd.idea.pomsky.PomskyIcons;
import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
class PomskyCreateFileAction extends CreateFileFromTemplateAction implements DumbAware {
  PomskyCreateFileAction() {
    super("Pomsky File", "Creates a new Pomsky file", PomskyIcons.LOGO);
  }

  @Override
  protected void buildDialog(
      @NotNull final Project project,
      @NotNull final PsiDirectory directory,
      @NotNull final CreateFileFromTemplateDialog.Builder builder) {
    builder
        .setTitle("New Pomsky File")
        .addKind("Pomsky file", PomskyIcons.LOGO, "Pomsky File" /* Template is empty on purpose */);
  }

  @NotNull
  @Override
  protected String getActionName(
      @NotNull final PsiDirectory directory,
      @NotNull final String newName,
      @NotNull final String templateName) {
    return "Creating Pomsky file " + newName;
  }
}
