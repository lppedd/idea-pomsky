package com.github.lppedd.idea.pomsky.lang.psi.impl;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyIdentifierPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyPsiElementVisitor;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyVariableDeclarationPsiElement;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyVariableDeclarationPsiElementImpl extends ASTWrapperPsiElement implements PomskyVariableDeclarationPsiElement {
  public PomskyVariableDeclarationPsiElementImpl(@NotNull final ASTNode node) {
    super(node);
  }

  @NotNull
  @Override
  public PomskyIdentifierPsiElement getIdentifier() {
    final var identifier = findChildByClass(PomskyIdentifierPsiElement.class);
    return requireNonNull(identifier, "The variable identifier must be present");
  }

  @Override
  public int getTextOffset() {
    return getIdentifier().getTextOffset();
  }

  @NotNull
  @Override
  public PsiElement getNameIdentifier() {
    return getIdentifier();
  }

  @NotNull
  @Override
  public PsiElement getNavigationElement() {
    return getIdentifier();
  }

  @NotNull
  @Override
  public String getName() {
    return getIdentifier().getText();
  }

  @NotNull
  @Override
  public PsiElement setName(@NotNull final String name) throws IncorrectOperationException {
    getIdentifier().replaceWithText(name);
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
      pomskyVisitor.visitVariableDeclaration(this);
    } else {
      super.accept(visitor);
    }
  }
}
