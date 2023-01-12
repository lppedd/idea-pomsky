package com.github.lppedd.idea.pomsky.lang.psi;

import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public interface PomskyVariableDeclarationPsiElement extends PsiNameIdentifierOwner {
  @NotNull
  PomskyIdentifierPsiElement getIdentifier();

  @NotNull
  @Override
  String getName();
}
