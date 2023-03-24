package com.github.lppedd.idea.pomsky.lang.refactoring;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyGroupReferencePsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyNamedCapturingGroupExpressionPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyVariableDeclarationPsiElement;
import com.intellij.lang.refactoring.RefactoringSupportProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
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
           element instanceof PomskyNamedCapturingGroupExpressionPsiElement && isNamedGroupReference(context);
  }

  private boolean isNamedGroupReference(@Nullable final PsiElement context) {
    if (context instanceof final PomskyGroupReferencePsiElement groupReference) {
      return groupReference.isNamed();
    }

    final var adjustedContext = context == null
        ? null
        : PsiTreeUtil.prevVisibleLeaf(context);

    if (adjustedContext instanceof final PomskyGroupReferencePsiElement groupReference) {
      return groupReference.isNamed();
    }

    return true;
  }
}
