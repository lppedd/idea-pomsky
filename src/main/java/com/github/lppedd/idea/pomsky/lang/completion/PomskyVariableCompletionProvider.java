package com.github.lppedd.idea.pomsky.lang.completion;

import com.github.lppedd.idea.pomsky.PomskyIcons;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyGroupExpressionPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyVariableDeclarationPsiElement;
import com.intellij.codeInsight.TailType;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import one.util.streamex.StreamEx;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Edoardo Luppi
 */
class PomskyVariableCompletionProvider extends CompletionProvider<CompletionParameters> {
  @Override
  protected void addCompletions(
      @NotNull final CompletionParameters parameters,
      @NotNull final ProcessingContext context,
      @NotNull final CompletionResultSet result) {
    final var element = parameters.getPosition();
    final var currentOffset = element.getTextOffset();
    final var declarations = new ArrayList<PsiNamedElement>(16);

    // Progressively search in outer scopes, up to the entire file
    for (var scope = findElementScope(element); scope != null; scope = findElementScope(scope)) {
      declarations.addAll(findDeclarationsInScope(scope, currentOffset));
    }

    // Search at the file level
    declarations.addAll(findDeclarationsInScope(element.getContainingFile(), currentOffset));

    StreamEx.of(declarations)
        .distinct(PsiNamedElement::getName)
        .map(this::createLookupElement)
        .append(createKeywordLookupElement("let"))
        .forEach(result::addElement);
  }

  @Nullable
  private PsiElement findElementScope(@NotNull final PsiElement element) {
    return PsiTreeUtil.findFirstParent(element, true, PomskyGroupExpressionPsiElement.class::isInstance);
  }

  @NotNull
  private Collection<? extends PsiNamedElement> findDeclarationsInScope(
      @NotNull final PsiElement scope,
      final int currentOffset) {
    // TODO: optimize by only searching up to current offset
    final var declarations = PsiTreeUtil.getChildrenOfTypeAsList(scope, PomskyVariableDeclarationPsiElement.class);
    declarations.removeIf(d -> d.getTextOffset() > currentOffset);
    return declarations;
  }

  @NotNull
  private LookupElement createLookupElement(@NotNull final PsiNamedElement element) {
    return LookupElementBuilder.create(element)
        .withIcon(PomskyIcons.LOGO)
        .withTypeText("Variable");
  }

  @NotNull
  @SuppressWarnings("SameParameterValue")
  private LookupElement createKeywordLookupElement(@NotNull final String keyword) {
    final var element = LookupElementBuilder.create(keyword)
        .withInsertHandler((ctx, item) -> TailType.insertChar(ctx.getEditor(), ctx.getTailOffset(), ' '))
        .bold();

    element.putUserData(PomskyCompletionContributor.KEY_KEYWORD, Boolean.TRUE);
    return element;
  }
}
