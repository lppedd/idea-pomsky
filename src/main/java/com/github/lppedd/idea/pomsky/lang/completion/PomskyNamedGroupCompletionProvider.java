package com.github.lppedd.idea.pomsky.lang.completion;

import com.github.lppedd.idea.pomsky.PomskyIcons;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyNamedCapturingGroupExpressionPsiElement;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import one.util.streamex.StreamEx;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
class PomskyNamedGroupCompletionProvider extends CompletionProvider<CompletionParameters> {
  @Override
  protected void addCompletions(
      @NotNull final CompletionParameters parameters,
      @NotNull final ProcessingContext context,
      @NotNull final CompletionResultSet result) {
    final var file = parameters.getOriginalFile();
    final var namedGroups = PsiTreeUtil.findChildrenOfType(file, PomskyNamedCapturingGroupExpressionPsiElement.class);
    StreamEx.of(namedGroups)
        .distinct(PsiNamedElement::getName)
        .map(this::createLookupElement)
        .forEach(result::addElement);
  }

  @NotNull
  private LookupElement createLookupElement(@NotNull final PsiNamedElement element) {
    return LookupElementBuilder.create(element)
        .withIcon(PomskyIcons.LOGO)
        .withTypeText("Named group");
  }
}
