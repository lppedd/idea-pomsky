package com.github.lppedd.idea.pomsky.lang.completion;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyExpressionPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyGroupReferencePsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyIdentifierPsiElement;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.Key;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyCompletionContributor extends CompletionContributor implements DumbAware {
  public static final Key<Boolean> KEY_KEYWORD = Key.create("pomskyCompletionKeyword");

  PomskyCompletionContributor() {
    extend(CompletionType.BASIC, variablePattern(), new PomskyVariableCompletionProvider());
    extend(CompletionType.BASIC, namedGroupPattern(), new PomskyNamedGroupCompletionProvider());
  }

  @NotNull
  private PsiElementPattern.Capture<PomskyIdentifierPsiElement> variablePattern() {
    return PlatformPatterns
        .psiElement(PomskyIdentifierPsiElement.class)
        .inside(PomskyExpressionPsiElement.class);
  }

  @NotNull
  private PsiElementPattern.Capture<PomskyGroupReferencePsiElement> namedGroupPattern() {
    return PlatformPatterns
        .psiElement(PomskyGroupReferencePsiElement.class)
        .inside(PomskyExpressionPsiElement.class);
  }
}
