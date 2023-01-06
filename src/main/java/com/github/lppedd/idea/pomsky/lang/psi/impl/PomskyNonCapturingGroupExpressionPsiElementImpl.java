package com.github.lppedd.idea.pomsky.lang.psi.impl;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyGroupExpressionPsiElement;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a non-capturing group, which is also the default group type.
 * <pre>('str' ident)</pre>
 *
 * @author Edoardo Luppi
 */
public class PomskyNonCapturingGroupExpressionPsiElementImpl extends ASTWrapperPsiElement implements PomskyGroupExpressionPsiElement {
  public PomskyNonCapturingGroupExpressionPsiElementImpl(@NotNull final ASTNode node) {
    super(node);
  }
}
