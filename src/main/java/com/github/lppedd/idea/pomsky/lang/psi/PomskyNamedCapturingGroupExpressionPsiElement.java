package com.github.lppedd.idea.pomsky.lang.psi;

import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public interface PomskyNamedCapturingGroupExpressionPsiElement extends PomskyGroupExpressionPsiElement, PsiNameIdentifierOwner {
  @NotNull
  PomskyGroupNamePsiElement getGroupName();
}
