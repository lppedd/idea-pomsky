package com.github.lppedd.idea.pomsky.lang.psi.impl;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyModifierActivationPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyPsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyModifierActivationPsiElementImpl extends ASTWrapperPsiElement implements PomskyModifierActivationPsiElement {
  public PomskyModifierActivationPsiElementImpl(@NotNull final ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull final PsiElementVisitor visitor) {
    if (visitor instanceof final PomskyPsiElementVisitor pomskyVisitor) {
      pomskyVisitor.visitModifierActivation(this);
    } else {
      super.accept(visitor);
    }
  }
}
