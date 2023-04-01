package com.github.lppedd.idea.pomsky.lang.psi;

import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyPsiElementVisitor extends PsiElementVisitor {
  public void visitStringLiteral(@NotNull final PomskyStringLiteralPsiElement element) {
    visitElement(element);
  }

  public void visitIdentifier(@NotNull final PomskyIdentifierPsiElement element) {
    visitElement(element);
  }

  public void visitGroupReference(@NotNull final PomskyGroupReferencePsiElement element) {
    visitElement(element);
  }

  public void visitModifierActivation(@NotNull final PomskyModifierActivationPsiElement element) {
    visitElement(element);
  }

  public void visitVariableDeclaration(@NotNull final PomskyVariableDeclarationPsiElement element) {
    visitElement(element);
  }

  public void visitNamedCapturingGroup(@NotNull final PomskyNamedCapturingGroupExpressionPsiElement element) {
    visitElement(element);
  }
}
