package com.github.lppedd.idea.pomsky.lang.psi;

import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Edoardo Luppi
 */
public class PomskyGroupReferencePsiElement extends LeafPsiElement {
  public PomskyGroupReferencePsiElement(
      @NotNull final IElementType type,
      @NotNull final CharSequence text) {
    super(type, text);
  }

  @Nullable
  @Override
  public PsiReference getReference() {
    final var references = getReferences();
    return references.length > 0
        ? references[0]
        : null;
  }

  @NotNull
  @Override
  public PsiReference @NotNull [] getReferences() {
    return ReferenceProvidersRegistry.getReferencesFromProviders(this);
  }

  @Override
  public void accept(@NotNull final PsiElementVisitor visitor) {
    if (visitor instanceof PomskyPsiElementVisitor pomskyVisitor) {
      pomskyVisitor.visitGroupReference(this);
    } else {
      super.accept(visitor);
    }
  }
}
