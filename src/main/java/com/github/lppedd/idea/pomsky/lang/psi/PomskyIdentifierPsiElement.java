package com.github.lppedd.idea.pomsky.lang.psi;

import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyIdentifierPsiElement extends LeafPsiElement {
  public PomskyIdentifierPsiElement(
      @NotNull final IElementType type,
      @NotNull final CharSequence text) {
    super(type, text);
  }

  @Override
  public PsiReference @NotNull [] getReferences() {
    return ReferenceProvidersRegistry.getReferencesFromProviders(this);
  }
}
