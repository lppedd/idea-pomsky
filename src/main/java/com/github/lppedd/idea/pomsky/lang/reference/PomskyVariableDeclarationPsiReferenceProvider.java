package com.github.lppedd.idea.pomsky.lang.reference;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyIdentifierPsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
class PomskyVariableDeclarationPsiReferenceProvider extends PsiReferenceProvider {
  @NotNull
  @Override
  public PsiReference @NotNull [] getReferencesByElement(
      @NotNull final PsiElement element,
      @NotNull final ProcessingContext context) {
    return new PsiReference[] {
        new PomskyVariableDeclarationReference((PomskyIdentifierPsiElement) element)
    };
  }
}
