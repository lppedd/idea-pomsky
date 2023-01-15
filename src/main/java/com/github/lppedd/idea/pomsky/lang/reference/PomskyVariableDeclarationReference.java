package com.github.lppedd.idea.pomsky.lang.reference;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyGroupExpressionPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyIdentifierPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyVariableDeclarationPsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Edoardo Luppi
 */
public class PomskyVariableDeclarationReference extends PsiReferenceBase<PomskyIdentifierPsiElement> {
  public PomskyVariableDeclarationReference(@NotNull final PomskyIdentifierPsiElement referenceElement) {
    super(referenceElement);
  }

  @Nullable
  @Override
  public PsiElement resolve() {
    final var element = getElement();

    // Progressively search in outer scopes, up to the entire file
    for (var scope = findElementScope(element); scope != null; scope = findElementScope(scope)) {
      final var reference = findDeclarationInScope(element, scope);

      if (reference != null) {
        return reference;
      }
    }

    // Search at the file level
    return findDeclarationInScope(element, element.getContainingFile());
  }

  @Nullable
  private PsiElement findElementScope(@NotNull final PsiElement element) {
    return PsiTreeUtil.findFirstParent(element, true, PomskyGroupExpressionPsiElement.class::isInstance);
  }

  @Nullable
  private PsiElement findDeclarationInScope(
      @NotNull final PomskyIdentifierPsiElement identifier,
      @NotNull final PsiElement scope) {
    final var declarations = PsiTreeUtil.getChildrenOfTypeAsList(scope, PomskyVariableDeclarationPsiElement.class);
    PsiElement result = null;

    for (final var declaration : declarations) {
      // Do not include variables declared after the reference/usage element
      if (declaration.getTextOffset() > identifier.getTextOffset()) {
        break;
      }

      if (identifier.getName().equals(declaration.getName())) {
        result = declaration;
      }
    }

    return result;
  }
}
