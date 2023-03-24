package com.github.lppedd.idea.pomsky.lang.psi;

import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a named capturing group.
 * <pre>:groupname('str' ident)</pre>
 * <p>
 * This type of group can then be referenced by its name.
 * <pre>::groupname</pre>
 *
 * @author Edoardo Luppi
 */
public interface PomskyNamedCapturingGroupExpressionPsiElement
    extends PomskyCapturingGroupExpressionPsiElement,
            PsiNameIdentifierOwner {
  @NotNull
  PomskyGroupNamePsiElement getGroupName();

  /**
   * Returns the group's name.
   */
  @NotNull
  @Override
  String getName();
}
