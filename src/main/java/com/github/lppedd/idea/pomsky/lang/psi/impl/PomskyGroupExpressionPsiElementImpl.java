package com.github.lppedd.idea.pomsky.lang.psi.impl;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyGroupExpressionPsiElement;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyGroupExpressionPsiElementImpl extends ASTWrapperPsiElement implements PomskyGroupExpressionPsiElement {
  public PomskyGroupExpressionPsiElementImpl(@NotNull final ASTNode node) {
    super(node);
  }
}
