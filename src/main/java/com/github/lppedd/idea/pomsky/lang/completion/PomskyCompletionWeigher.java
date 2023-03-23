package com.github.lppedd.idea.pomsky.lang.completion;

import com.intellij.codeInsight.completion.CompletionLocation;
import com.intellij.codeInsight.completion.CompletionWeigher;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author Edoardo Luppi
 */
class PomskyCompletionWeigher extends CompletionWeigher {
  @NotNull
  @Override
  public Integer weigh(
      @NotNull final LookupElement element,
      @NotNull final CompletionLocation location) {
    return Objects.requireNonNull(element.getUserData(PomskyCompletionContributor.KEY_WEIGHT));
  }
}
