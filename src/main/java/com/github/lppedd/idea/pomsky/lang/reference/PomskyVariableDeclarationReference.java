package com.github.lppedd.idea.pomsky.lang.reference;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyGroupExpressionPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyVariableDeclarationPsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.psi.util.PsiTreeUtil.findChildrenOfType;
import static com.intellij.psi.util.PsiTreeUtil.findFirstParent;

/**
 * @author Edoardo Luppi
 */
public class PomskyVariableDeclarationReference extends PsiReferenceBase<PsiElement> {
  public PomskyVariableDeclarationReference(@NotNull final PsiElement referenceElement) {
    super(referenceElement);
  }

  @Nullable
  @Override
  public PsiElement resolve() {
    final var element = getElement();

    // When searching from the variable declaration, don't include the declaration identifier
    if (element.getParent() instanceof PomskyVariableDeclarationPsiElement) {
      return null;
    }

    // Progressively search in outer scopes, up to the entire file
    for (var scope = findElementScope(element); scope != null; scope = findElementScope(scope)) {
      final var reference = findDeclarationInScope(element, scope);

      if (reference != null) {
        return reference;
      }
    }

    return findDeclarationInScope(element, element.getContainingFile());
  }

  @Nullable
  private PsiElement findElementScope(@NotNull final PsiElement element) {
    return findFirstParent(element, true, PomskyGroupExpressionPsiElement.class::isInstance);
  }

  @Nullable
  private PsiElement findDeclarationInScope(
      @NotNull final PsiElement element,
      @NotNull final PsiElement scopeElement) {
    for (final var declaration : findChildrenOfType(scopeElement, PomskyVariableDeclarationPsiElement.class)) {
      // Do not include variables declared after the reference/usage element
      if (declaration.getTextOffset() > element.getTextOffset()) {
        return null;
      }

      if (element.getText().equals(declaration.getName())) {
        return declaration;
      }
    }

    return null;
  }
}
