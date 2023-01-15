package com.github.lppedd.idea.pomsky.lang.psi.impl;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyGroupNamePsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyNamedCapturingGroupExpressionPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyPsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Represents a named capturing group.
 * <pre>:groupname('str' ident)</pre>
 * <p>
 * This type of group can then be referenced by its name.
 * <pre>::groupname</pre>
 *
 * @author Edoardo Luppi
 */
public class PomskyNamedCapturingGroupExpressionPsiElementImpl extends ASTWrapperPsiElement implements PomskyNamedCapturingGroupExpressionPsiElement {
  public PomskyNamedCapturingGroupExpressionPsiElementImpl(@NotNull final ASTNode node) {
    super(node);
  }

  @NotNull
  @Override
  public PomskyGroupNamePsiElement getGroupName() {
    final var groupName = findChildByClass(PomskyGroupNamePsiElement.class);
    return Objects.requireNonNull(groupName, "The group name must be present in the parsed tree");
  }

  @Override
  public int getTextOffset() {
    return getGroupName().getTextOffset();
  }

  @Nullable
  @Override
  public PsiElement getNameIdentifier() {
    return getGroupName();
  }

  @NotNull
  @Override
  public PsiElement getNavigationElement() {
    return getGroupName();
  }

  @NotNull
  @Override
  public String getName() {
    return getGroupName().getText();
  }

  @NotNull
  @Override
  public PsiElement setName(@NotNull final String name) throws IncorrectOperationException {
    getGroupName().replaceWithText(name);
    return this;
  }

  @NotNull
  @Override
  public GlobalSearchScope getResolveScope() {
    return GlobalSearchScope.fileScope(getContainingFile());
  }

  @NotNull
  @Override
  public SearchScope getUseScope() {
    return GlobalSearchScope.fileScope(getContainingFile());
  }

  @Override
  public void accept(@NotNull final PsiElementVisitor visitor) {
    if (visitor instanceof PomskyPsiElementVisitor pomskyVisitor) {
      pomskyVisitor.visitNamedCapturingGroup(this);
    } else {
      super.accept(visitor);
    }
  }
}
