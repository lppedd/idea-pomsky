package com.github.lppedd.idea.pomsky.lang.psi.impl;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyIdentifierPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyVariableDeclarationPsiElement;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
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
}
