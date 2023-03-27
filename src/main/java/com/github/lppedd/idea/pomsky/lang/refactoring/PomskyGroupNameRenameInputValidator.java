package com.github.lppedd.idea.pomsky.lang.refactoring;

import com.github.lppedd.idea.pomsky.lang.PomskyNameHelper;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyGroupExpressionPsiElement;
import com.intellij.openapi.project.Project;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.refactoring.rename.RenameInputValidatorEx;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Edoardo Luppi
 */
public class PomskyGroupNameRenameInputValidator implements RenameInputValidatorEx {
  @NotNull
  @Override
  public ElementPattern<? extends PsiElement> getPattern() {
    return PlatformPatterns.psiElement(PomskyGroupExpressionPsiElement.class);
  }

  @Override
  public boolean isInputValid(
      @NotNull final String newName,
      @NotNull final PsiElement element,
      @NotNull final ProcessingContext context) {
    return PomskyNameHelper.getInstance().validateGroupName(newName) == null;
  }

  @Nullable
  @Override
  public String getErrorMessage(
      @NotNull final String newName,
      @NotNull final Project project) {
    return PomskyNameHelper.getInstance().validateGroupName(newName);
  }
}
