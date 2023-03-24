package com.github.lppedd.idea.pomsky.lang.completion;

import com.github.lppedd.idea.pomsky.lang.PomskyBuiltins;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyCharacterSetExpressionPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyExpressionPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyGroupReferencePsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyIdentifierPsiElement;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.KeyWithDefaultValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * @author Edoardo Luppi
 */
public class PomskyCompletionContributor extends CompletionContributor implements DumbAware {
  /**
   * Less weight (number towards zero) equals further down in the list.
   */
  public static final Key<Integer> KEY_WEIGHT = KeyWithDefaultValue.create("pomskyCompletionWeight", Integer.MAX_VALUE);

  /**
   * Provides completion items for keywords.
   */
  private static final PomskyCompletionProvider KEYWORDS = new PomskyKeywordCompletionProvider();

  /**
   * Provides completion items for user-defined variables.
   */
  private static final PomskyCompletionProvider VARIABLES = new PomskyVariableCompletionProvider();

  /**
   * Provides completion items for user-defined named groups.
   */
  private static final PomskyCompletionProvider NAMED_GROUPS = new PomskyNamedGroupCompletionProvider();

  /**
   * Provides completion items for built-in variables.
   */
  private static final PomskyCompletionProvider BUILTIN_VARIABLES =
      new PomskyBuiltinCompletionProvider(PomskyBuiltins.Variables.all(), "Built-in variable");

  /**
   * Provides completion items for built-in character classes.
   * Should be used only inside character sets.
   */
  private static final PomskyCompletionProvider BUILTIN_CHARACTER_CLASSES =
      new PomskyBuiltinCompletionProvider(PomskyBuiltins.CharacterClasses.all(), "Character class");

  /**
   * Provides completion items for built-in unicode categories.
   */
  private static final PomskyCompletionProvider BUILTIN_CATEGORIES =
      new PomskyBuiltinCompletionProvider(PomskyBuiltins.Properties.Categories.all(), "Category property");

  /**
   * Provides completion items for built-in unicode categories.
   */
  private static final PomskyCompletionProvider BUILTIN_SCRIPTS =
      new PomskyBuiltinCompletionProvider(PomskyBuiltins.Properties.Scripts.all(), "Script property");

  /**
   * Provides completion items for built-in unicode categories.
   */
  private static final PomskyCompletionProvider BUILTIN_BLOCKS =
      new PomskyBuiltinCompletionProvider(PomskyBuiltins.Properties.Blocks.all(), "Block property");

  /**
   * Provides completion items for built-in unicode categories.
   */
  private static final PomskyCompletionProvider BUILTIN_OTHERS =
      new PomskyBuiltinCompletionProvider(PomskyBuiltins.Properties.Blocks.all(), "Property");

  @Override
  public void fillCompletionVariants(
      @NotNull final CompletionParameters parameters,
      @NotNull final CompletionResultSet result) {
    final var position = parameters.getPosition();
    final var positionParent = position.getParent();
    final var lookupElements = new ArrayList<LookupElement>(32);

    ProgressManager.checkCanceled();

    if (position instanceof PomskyIdentifierPsiElement) {
      if (positionParent instanceof PomskyExpressionPsiElement) {
        KEYWORDS.addCompletions(parameters, lookupElements);
        VARIABLES.addCompletions(parameters, lookupElements);
        BUILTIN_VARIABLES.addCompletions(parameters, lookupElements);
      } else if (positionParent instanceof PomskyCharacterSetExpressionPsiElement) {
        BUILTIN_CHARACTER_CLASSES.addCompletions(parameters, lookupElements);
        BUILTIN_CATEGORIES.addCompletions(parameters, lookupElements);
        BUILTIN_SCRIPTS.addCompletions(parameters, lookupElements);
        BUILTIN_BLOCKS.addCompletions(parameters, lookupElements);
        BUILTIN_OTHERS.addCompletions(parameters, lookupElements);
      }
    } else if (position instanceof PomskyGroupReferencePsiElement &&
               positionParent instanceof PomskyExpressionPsiElement) {
      NAMED_GROUPS.addCompletions(parameters, lookupElements);
    }

    lookupElements.forEach(result::addElement);
  }
}
