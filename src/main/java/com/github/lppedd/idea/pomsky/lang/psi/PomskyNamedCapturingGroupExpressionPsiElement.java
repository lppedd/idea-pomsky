package com.github.lppedd.idea.pomsky.lang.psi;

import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public interface PomskyNamedCapturingGroupExpressionPsiElement
    extends PomskyCapturingGroupExpressionPsiElement,
            PsiNameIdentifierOwner {
  @NotNull
  PomskyGroupNamePsiElement getGroupName();

  /**
   * Returns the group's name.
   */
  @NotNull
  @Override
  String getName();
}
