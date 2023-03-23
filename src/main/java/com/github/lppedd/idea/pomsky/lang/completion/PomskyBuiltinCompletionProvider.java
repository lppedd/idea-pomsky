package com.github.lppedd.idea.pomsky.lang.completion;

import com.github.lppedd.idea.pomsky.PomskyIcons;
import com.github.lppedd.idea.pomsky.lang.PomskyBuiltins;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author Edoardo Luppi
 */
class PomskyBuiltinCompletionProvider implements PomskyCompletionProvider {
  private final Collection<PomskyBuiltins.Builtin> builtins;
  private final String type;

  PomskyBuiltinCompletionProvider(
      @NotNull final Collection<PomskyBuiltins.Builtin> builtins,
      @NotNull final String type) {
    this.builtins = builtins;
    this.type = type;
  }

  @Override
  public void addCompletions(
      @NotNull final CompletionParameters parameters,
      @NotNull final Collection<LookupElement> lookupElements) {
    for (final var builtin : builtins) {
      ProgressManager.checkCanceled();

      final var name = builtin.getName();
      lookupElements.add(createLookupElement(name));

      for (final var alias : builtin.getAliases()) {
        lookupElements.add(createAliasLookupElement(name, alias));
      }
    }
  }

  @NotNull
  private LookupElement createLookupElement(@NotNull final String name) {
    final var lookupElement = LookupElementBuilder.create(name)
        .withIcon(PomskyIcons.LOGO)
        .withTypeText(type);

    lookupElement.putUserData(PomskyCompletionContributor.KEY_WEIGHT, 1);
    return lookupElement;
  }

  @NotNull
  private LookupElement createAliasLookupElement(
      @NotNull final String name,
      @NotNull final String alias) {
    final var lookupElement = LookupElementBuilder.create(alias)
        .withIcon(PomskyIcons.LOGO)
        .withTailText(" (" + name + ")")
        .withTypeText(type);

    lookupElement.putUserData(PomskyCompletionContributor.KEY_WEIGHT, 1);
    return lookupElement;
  }
}
