package com.github.lppedd.idea.pomsky.lang.psi;

import com.github.lppedd.idea.pomsky.lang.refactoring.PomskyIdentifierElementManipulator;
import com.intellij.psi.ElementManipulators;
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
public class PomskyIdentifierPsiElement extends LeafPsiElement {
  public PomskyIdentifierPsiElement(
      @NotNull final IElementType type,
      @NotNull final CharSequence text) {
    super(type, text);
  }

  /**
   * Returns the identifier's name.
   *
   * @see PomskyIdentifierElementManipulator
   */
  @NotNull
  @Override
  public String getName() {
    return ElementManipulators.getValueText(this);
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
      pomskyVisitor.visitIdentifier(this);
    } else {
      super.accept(visitor);
    }
  }
}
