package com.github.lppedd.idea.pomsky.lang.psi;

import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public interface PomskyVariableDeclarationPsiElement extends PsiNameIdentifierOwner, NavigatablePsiElement {
  @NotNull
  PomskyIdentifierPsiElement getIdentifier();

  /**
   * Returns the variable's name.
   */
  @NotNull
  @Override
  String getName();
}
