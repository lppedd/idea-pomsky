package com.github.lppedd.idea.pomsky.lang.reference;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyGroupExpressionPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyNamedCapturingGroupExpressionPsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.psi.util.PsiTreeUtil.findChildrenOfType;
import static com.intellij.psi.util.PsiTreeUtil.findFirstParent;

/**
 * Given a named capturing group reference ({@code ::my_group_name}),
 * resolves to that group declaration.
 *
 * @author Edoardo Luppi
 */
public class PomskyNamedCapturingGroupExpressionPsiReference extends PsiReferenceBase<PsiElement> {
  public PomskyNamedCapturingGroupExpressionPsiReference(@NotNull final PsiElement element) {
    super(element);
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
    for (final var declaration : findChildrenOfType(scopeElement, PomskyNamedCapturingGroupExpressionPsiElement.class)) {
      // Do not include named capturing groups declared after the reference/usage element
      if (declaration.getTextOffset() > element.getTextOffset()) {
        return null;
      }

      if (element.getText().substring(2).equals(declaration.getName())) {
        return declaration;
      }
    }

    return null;
  }
}
