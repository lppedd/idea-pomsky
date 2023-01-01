package com.github.lppedd.idea.pomsky.lang.psi;

import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyGroupNamePsiElement extends LeafPsiElement {
  public PomskyGroupNamePsiElement(
      @NotNull final IElementType type,
      @NotNull final CharSequence text) {
    super(type, text);
  }
}
