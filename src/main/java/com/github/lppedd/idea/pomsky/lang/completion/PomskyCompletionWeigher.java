package com.github.lppedd.idea.pomsky.lang.completion;

import com.intellij.codeInsight.completion.CompletionLocation;
import com.intellij.codeInsight.completion.CompletionWeigher;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyCompletionWeigher extends CompletionWeigher {
  @NotNull
  @Override
  public Boolean weigh(
      @NotNull final LookupElement element,
      @NotNull final CompletionLocation location) {
    return element.getUserData(PomskyCompletionContributor.KEY_KEYWORD) != Boolean.TRUE;
  }
}
