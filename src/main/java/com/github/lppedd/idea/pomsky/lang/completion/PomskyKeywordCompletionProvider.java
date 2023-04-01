package com.github.lppedd.idea.pomsky.lang.completion;

import com.intellij.codeInsight.TailType;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author Edoardo Luppi
 */
class PomskyKeywordCompletionProvider implements PomskyCompletionProvider {
  @Override
  public void addCompletions(
      @NotNull final CompletionParameters parameters,
      @NotNull final Collection<LookupElement> lookupElements) {
    lookupElements.add(createLookupElement("let", true));
    lookupElements.add(createLookupElement("range", true));
    lookupElements.add(createLookupElement("regex", true));
    lookupElements.add(createLookupElement("atomic", true));
    lookupElements.add(createLookupElement("enable", true));
    lookupElements.add(createLookupElement("disable", true));
    lookupElements.add(createLookupElement("base", true));
    lookupElements.add(createLookupElement("lazy", false));
    lookupElements.add(createLookupElement("unicode", false));
    lookupElements.add(createLookupElement("greedy", false));
  }

  @NotNull
  private LookupElement createLookupElement(@NotNull final String keyword, final boolean doAddSpace) {
    final InsertHandler<LookupElement> insertHandler = doAddSpace
        ? (ctx, item) -> TailType.insertChar(ctx.getEditor(), ctx.getTailOffset(), ' ')
        : null;
    final var lookupElement = LookupElementBuilder.create(keyword)
        .withTypeText("Keyword")
        .withInsertHandler(insertHandler)
        .bold();

    lookupElement.putUserData(PomskyCompletionContributor.KEY_WEIGHT, 0);
    return lookupElement;
  }
}
