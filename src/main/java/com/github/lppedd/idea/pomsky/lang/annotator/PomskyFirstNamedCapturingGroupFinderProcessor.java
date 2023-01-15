package com.github.lppedd.idea.pomsky.lang.annotator;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyNamedCapturingGroupExpressionPsiElement;
import com.intellij.psi.search.PsiElementProcessor.FindElement;
import org.jetbrains.annotations.NotNull;

/**
 * Given a named capturing group element, tries to find the first group declaration
 * using the same name (the found element is going to be a different one, if any).
 *
 * @author Edoardo Luppi
 */
class PomskyFirstNamedCapturingGroupFinderProcessor extends FindElement<PomskyNamedCapturingGroupExpressionPsiElement> {
  private final PomskyNamedCapturingGroupExpressionPsiElement group;

  PomskyFirstNamedCapturingGroupFinderProcessor(@NotNull final PomskyNamedCapturingGroupExpressionPsiElement group) {
    this.group = group;
  }

  @Override
  public boolean execute(@NotNull final PomskyNamedCapturingGroupExpressionPsiElement other) {
    // We can stop processing as soon as the other group is declared after our own
    if (other.getTextOffset() > group.getTextOffset()) {
      return false;
    }

    if (other.getName().equals(group.getName()) &&
        other.getTextOffset() < group.getTextOffset()) {
      setFound(other);
      return false;
    }

    return true;
  }
}
