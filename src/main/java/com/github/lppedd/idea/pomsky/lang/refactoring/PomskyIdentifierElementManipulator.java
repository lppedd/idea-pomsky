package com.github.lppedd.idea.pomsky.lang.refactoring;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyIdentifierPsiElement;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.AbstractElementManipulator;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyIdentifierElementManipulator extends AbstractElementManipulator<PomskyIdentifierPsiElement> {
  @NotNull
  @Override
  public PomskyIdentifierPsiElement handleContentChange(
      @NotNull final PomskyIdentifierPsiElement element,
      @NotNull final TextRange range,
      @NotNull final String newContent) throws IncorrectOperationException {
    return (PomskyIdentifierPsiElement) element.replaceWithText(newContent);
  }
}
