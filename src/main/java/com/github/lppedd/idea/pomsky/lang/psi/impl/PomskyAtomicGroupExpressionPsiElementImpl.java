package com.github.lppedd.idea.pomsky.lang.psi.impl;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyGroupExpressionPsiElement;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an atomic group expression.
 * <pre>atomic('str' ident)</pre>
 * <p>
 * Atomic groups improve matching performance by preventing backtracking.
 *
 * @author Edoardo Luppi
 */
public class PomskyAtomicGroupExpressionPsiElementImpl extends ASTWrapperPsiElement implements PomskyGroupExpressionPsiElement {
  public PomskyAtomicGroupExpressionPsiElementImpl(@NotNull final ASTNode node) {
    super(node);
  }
}
