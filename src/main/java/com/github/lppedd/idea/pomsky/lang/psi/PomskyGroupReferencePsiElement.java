package com.github.lppedd.idea.pomsky.lang.psi;

import com.github.lppedd.idea.pomsky.PomskyPatterns;
import com.github.lppedd.idea.pomsky.lang.refactoring.PomskyGroupReferenceElementManipulator;
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
public class PomskyGroupReferencePsiElement extends LeafPsiElement {
  public PomskyGroupReferencePsiElement(
      @NotNull final IElementType type,
      @NotNull final CharSequence text) {
    super(type, text);
  }

  /**
   * Returns the referenced group name or number.
   *
   * @see PomskyGroupReferenceElementManipulator
   */
  @NotNull
  public String getName() {
    return ElementManipulators.getValueText(this);
  }

  /**
   * Whether this reference is using the name notation to refer to a group.
   */
  public boolean isNamed() {
    return !isNumbered();
  }

  /**
   * Whether this reference is using the numeric notation to refer to a group.
   */
  public boolean isNumbered() {
    return PomskyPatterns.matchesNumber(getName());
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
