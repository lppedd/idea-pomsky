package com.github.lppedd.idea.pomsky.lang;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyNamedCapturingGroupExpressionPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyVariableDeclarationPsiElement;
import com.intellij.lang.HelpID;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyFindUsagesProvider implements FindUsagesProvider {
  @Override
  public boolean canFindUsagesFor(@NotNull final PsiElement psiElement) {
    return psiElement instanceof PomskyVariableDeclarationPsiElement ||
           psiElement instanceof PomskyNamedCapturingGroupExpressionPsiElement;
  }

  @NonNls
  @NotNull
  @Override
  public String getHelpId(@NotNull final PsiElement psiElement) {
    return HelpID.FIND_OTHER_USAGES;
  }

  @Nls
  @NotNull
  @Override
  public String getType(@NotNull final PsiElement element) {
    if (element instanceof PomskyVariableDeclarationPsiElement) {
      return "local variable";
    }

    if (element instanceof PomskyNamedCapturingGroupExpressionPsiElement) {
      return "named capturing group";
    }

    return "";
  }

  @Nls
  @NotNull
  @Override
  public String getDescriptiveName(@NotNull final PsiElement element) {
    return "";
  }

  @Nls
  @NotNull
  @Override
  public String getNodeText(@NotNull final PsiElement element, final boolean useFullName) {
    return element.getText();
  }
}
