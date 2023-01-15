package com.github.lppedd.idea.pomsky.lang.reference;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyExpressionPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyGroupReferencePsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyIdentifierPsiElement;
import com.intellij.patterns.ElementPattern;
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
    registrar.registerReferenceProvider(
        identifierPattern(),
        new PomskyVariableDeclarationPsiReferenceProvider()
    );

    // Named group references in expressions
    registrar.registerReferenceProvider(
        groupReferencePattern(),
        new PomskyCapturingGroupExpressionPsiReferenceProvider()
    );
  }

  @NotNull
  private ElementPattern<PomskyIdentifierPsiElement> identifierPattern() {
    return PlatformPatterns
        .psiElement(PomskyIdentifierPsiElement.class)
        .withParent(PomskyExpressionPsiElement.class);
  }

  @NotNull
  private ElementPattern<PomskyGroupReferencePsiElement> groupReferencePattern() {
    return PlatformPatterns.psiElement(PomskyGroupReferencePsiElement.class);
  }
}
