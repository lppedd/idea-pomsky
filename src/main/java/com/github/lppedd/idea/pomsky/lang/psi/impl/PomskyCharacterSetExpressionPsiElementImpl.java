package com.github.lppedd.idea.pomsky.lang.psi.impl;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyCharacterSetExpressionPsiElement;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyCharacterSetExpressionPsiElementImpl extends ASTWrapperPsiElement implements PomskyCharacterSetExpressionPsiElement {
  public PomskyCharacterSetExpressionPsiElementImpl(@NotNull final ASTNode node) {
    super(node);
  }
}
