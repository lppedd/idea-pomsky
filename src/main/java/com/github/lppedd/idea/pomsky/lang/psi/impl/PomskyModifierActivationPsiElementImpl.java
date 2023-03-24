package com.github.lppedd.idea.pomsky.lang.psi.impl;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyModifierActivationPsiElement;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyModifierActivationPsiElementImpl extends ASTWrapperPsiElement implements PomskyModifierActivationPsiElement {
  public PomskyModifierActivationPsiElementImpl(@NotNull final ASTNode node) {
    super(node);
  }
}
