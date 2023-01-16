package com.github.lppedd.idea.pomsky.lang.inspections;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyVariableDeclarationPsiElement;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 * @see PomskyUnusedVariableDeclarationInspection
 */
class PomskyRemoveUnusedVariableDeclarationFix implements LocalQuickFix {
  private final String variableName;

  PomskyRemoveUnusedVariableDeclarationFix(@NotNull final String variableName) {
    this.variableName = variableName;
  }

  @IntentionName
  @NotNull
  @Override
  public String getName() {
    return "Remove variable '%s'".formatted(variableName);
  }

  @IntentionFamilyName
  @NotNull
  @Override
  public String getFamilyName() {
    return "Remove variable";
  }

  @SuppressWarnings("RedundantCast")
  @Override
  public void applyFix(
      @NotNull final Project project,
      @NotNull final ProblemDescriptor descriptor) {
    // Explicit cast to be sure we are targeting a variable declaration
    ((PomskyVariableDeclarationPsiElement) descriptor.getPsiElement()).delete();
  }
}
