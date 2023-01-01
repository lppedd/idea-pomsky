package com.github.lppedd.idea.pomsky.lang.refactoring;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyNamedCapturingGroupExpressionPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyVariableDeclarationPsiElement;
import com.intellij.lang.refactoring.RefactoringSupportProvider;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Adds the ability to rename Pomsky variables and named groups in-place.
 *
 * @author Edoardo Luppi
 */
public class PomskyRefactoringSupportProvider extends RefactoringSupportProvider {
  @Override
  public boolean isMemberInplaceRenameAvailable(
      @NotNull final PsiElement element,
      @Nullable final PsiElement context) {
    return element instanceof PomskyVariableDeclarationPsiElement ||
           element instanceof PomskyNamedCapturingGroupExpressionPsiElement;
  }
}
