package com.github.lppedd.idea.pomsky.lang.psi;

import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyKeywordPsiElement extends LeafPsiElement {
  public PomskyKeywordPsiElement(
      @NotNull final IElementType type,
      @NotNull final CharSequence text) {
    super(type, text);
  }
}
