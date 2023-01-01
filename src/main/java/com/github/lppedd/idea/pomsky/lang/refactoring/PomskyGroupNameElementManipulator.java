package com.github.lppedd.idea.pomsky.lang.refactoring;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyGroupNamePsiElement;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.AbstractElementManipulator;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyGroupNameElementManipulator extends AbstractElementManipulator<PomskyGroupNamePsiElement> {
  @NotNull
  @Override
  public PomskyGroupNamePsiElement handleContentChange(
      @NotNull final PomskyGroupNamePsiElement element,
      @NotNull final TextRange range,
      @NotNull final String newContent) throws IncorrectOperationException {
    return (PomskyGroupNamePsiElement) element.replaceWithText(newContent);
  }
}
