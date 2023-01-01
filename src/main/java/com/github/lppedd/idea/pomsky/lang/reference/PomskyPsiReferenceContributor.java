package com.github.lppedd.idea.pomsky.lang.reference;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyGroupReferencePsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyIdentifierPsiElement;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;

import static com.intellij.patterns.PlatformPatterns.psiElement;

/**
 * @author Edoardo Luppi
 */
public class PomskyPsiReferenceContributor extends PsiReferenceContributor {
  @Override
  public void registerReferenceProviders(@NotNull final PsiReferenceRegistrar registrar) {
    // Variable references in expressions
    final var varDeclProvider = new PomskyVariableDeclarationPsiReferenceProvider();
    registrar.registerReferenceProvider(psiElement(PomskyIdentifierPsiElement.class), varDeclProvider);

    // Named group references in expressions
    final var namedGroupProvider = new PomskyNamedCapturingGroupExpressionPsiReferenceProvider();
    registrar.registerReferenceProvider(psiElement(PomskyGroupReferencePsiElement.class), namedGroupProvider);
  }
}
