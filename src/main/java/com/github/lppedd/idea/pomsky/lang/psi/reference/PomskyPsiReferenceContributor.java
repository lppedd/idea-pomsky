package com.github.lppedd.idea.pomsky.lang.psi.reference;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyIdentifierPsiElement;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyPsiReferenceContributor extends PsiReferenceContributor {
  @Override
  public void registerReferenceProviders(@NotNull final PsiReferenceRegistrar registrar) {
    // Variable references in expressions
    final var varDeclProvider = new PomskyIdentifierPsiReferenceProvider();
    registrar.registerReferenceProvider(PlatformPatterns.psiElement(PomskyIdentifierPsiElement.class), varDeclProvider);
  }
}
