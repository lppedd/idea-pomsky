package com.github.lppedd.idea.pomsky.lang.refactoring;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyGroupReferencePsiElement;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.AbstractElementManipulator;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyGroupReferenceElementManipulator extends AbstractElementManipulator<PomskyGroupReferencePsiElement> {
  @NotNull
  @Override
  public PomskyGroupReferencePsiElement handleContentChange(
      @NotNull final PomskyGroupReferencePsiElement element,
      @NotNull final TextRange range,
      @NotNull final String newContent) throws IncorrectOperationException {
    return (PomskyGroupReferencePsiElement) element.replaceWithText("::" + newContent);
  }

  @NotNull
  @Override
  public TextRange getRangeInElement(@NotNull final PomskyGroupReferencePsiElement element) {
    // Take out the initial '::' in group references like '::my_group_name'
    return TextRange.create(2, element.getTextLength());
  }
}
