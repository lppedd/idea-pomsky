package com.github.lppedd.idea.pomsky.lang.psi;

import com.intellij.psi.PsiNamedElement;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public interface PomskyVariableDeclarationPsiElement extends PsiNamedElement {
  @NotNull
  PomskyIdentifierPsiElement getIdentifier();
}
