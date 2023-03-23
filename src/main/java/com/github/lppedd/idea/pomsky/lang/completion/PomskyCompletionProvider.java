package com.github.lppedd.idea.pomsky.lang.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author Edoardo Luppi
 */
interface PomskyCompletionProvider {
  void addCompletions(
      @NotNull final CompletionParameters parameters,
      @NotNull final Collection<LookupElement> lookupElements);
}
