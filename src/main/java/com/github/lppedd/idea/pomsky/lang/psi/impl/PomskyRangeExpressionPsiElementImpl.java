package com.github.lppedd.idea.pomsky.lang.psi.impl;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyRangeExpressionPsiElement;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyRangeExpressionPsiElementImpl extends ASTWrapperPsiElement implements PomskyRangeExpressionPsiElement {
  public PomskyRangeExpressionPsiElementImpl(@NotNull final ASTNode node) {
    super(node);
  }
}
