package com.github.lppedd.idea.pomsky.lang.psi.impl;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyExpressionPsiElement;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyExpressionPsiElementImpl extends ASTWrapperPsiElement implements PomskyExpressionPsiElement {
  public PomskyExpressionPsiElementImpl(@NotNull final ASTNode node) {
    super(node);
  }
}
