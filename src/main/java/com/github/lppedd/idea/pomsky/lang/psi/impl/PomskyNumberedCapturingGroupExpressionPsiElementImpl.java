package com.github.lppedd.idea.pomsky.lang.psi.impl;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyCapturingGroupExpressionPsiElement;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a numbered capturing group.
 * <pre>:('str' ident)</pre>
 * <p>
 * This type of group can then be referenced by its declaration order number.
 * <pre>::1</pre>
 *
 * @author Edoardo Luppi
 */
public class PomskyNumberedCapturingGroupExpressionPsiElementImpl extends ASTWrapperPsiElement implements PomskyCapturingGroupExpressionPsiElement {
  public PomskyNumberedCapturingGroupExpressionPsiElementImpl(@NotNull final ASTNode node) {
    super(node);
  }
}
