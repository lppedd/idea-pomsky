package com.github.lppedd.idea.pomsky.lang.psi;

import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a string literal, used inside an expression.
 *
 * @author Edoardo Luppi
 */
public class PomskyStringLiteralPsiElement extends LeafPsiElement {
  public PomskyStringLiteralPsiElement(
      @NotNull final IElementType type,
      @NotNull final CharSequence text) {
    super(type, text);
  }

  @Override
  public void accept(@NotNull final PsiElementVisitor visitor) {
    if (visitor instanceof PomskyPsiElementVisitor pomskyVisitor) {
      pomskyVisitor.visitStringLiteral(this);
    } else {
      visitor.visitElement(this);
    }
  }
}
