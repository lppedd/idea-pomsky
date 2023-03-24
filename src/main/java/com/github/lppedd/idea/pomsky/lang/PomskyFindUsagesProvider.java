package com.github.lppedd.idea.pomsky.lang;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyNamedCapturingGroupExpressionPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyVariableDeclarationPsiElement;
import com.intellij.lang.HelpID;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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
      return "Variable";
    }

    if (element instanceof PomskyNamedCapturingGroupExpressionPsiElement) {
      return "Named capturing group";
    }

    throw new IllegalArgumentException("Cannot get type for '%s'".formatted(element));
  }

  @Nls
  @NotNull
  @Override
  public String getDescriptiveName(@NotNull final PsiElement element) {
    return element instanceof final PsiNamedElement namedElement
        ? Objects.requireNonNullElse(namedElement.getName(), element.getText())
        : element.getText();
  }

  @Nls
  @NotNull
  @Override
  public String getNodeText(@NotNull final PsiElement element, final boolean useFullName) {
    return element.getText();
  }
}
