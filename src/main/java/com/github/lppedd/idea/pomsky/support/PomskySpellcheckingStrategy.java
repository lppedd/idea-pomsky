package com.github.lppedd.idea.pomsky.support;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyGroupNamePsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyGroupReferencePsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyIdentifierPsiElement;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.spellchecker.inspections.IdentifierSplitter;
import com.intellij.spellchecker.tokenizer.SpellcheckingStrategy;
import com.intellij.spellchecker.tokenizer.TokenConsumer;
import com.intellij.spellchecker.tokenizer.Tokenizer;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
class PomskySpellcheckingStrategy extends SpellcheckingStrategy {
  @NotNull
  @Override
  public Tokenizer<?> getTokenizer(@NotNull final PsiElement element) {
    if (element instanceof PsiComment) {
      return new PomskyCommentTokenizer();
    }

    if (element instanceof PomskyGroupReferencePsiElement) {
      return new PomskyGroupReferenceTokenizer();
    }

    if (element instanceof PomskyIdentifierPsiElement ||
        element instanceof PomskyGroupNamePsiElement) {
      return new PomskyLeafElementTokenizer(true);
    }

    return SpellcheckingStrategy.EMPTY_TOKENIZER;
  }

  private static class PomskyCommentTokenizer extends PomskyLeafElementTokenizer {
    PomskyCommentTokenizer() {
      super(false);
    }

    @NotNull
    @Override
    protected TextRange getRangeToCheck(
        @NotNull final LeafPsiElement element,
        @NotNull final String elementText) {
      // Exclude the start of the comment with its '#' characters from spell checking
      var startIndex = 0;

      for (final var c : elementText.toCharArray()) {
        if (c == '#' || Character.isWhitespace(c)) {
          startIndex++;
        } else {
          break;
        }
      }

      return TextRange.create(startIndex, elementText.length());
    }
  }

  private static class PomskyGroupReferenceTokenizer extends PomskyLeafElementTokenizer {
    PomskyGroupReferenceTokenizer() {
      super(true);
    }

    @NotNull
    @Override
    protected TextRange getRangeToCheck(
        @NotNull final LeafPsiElement element,
        @NotNull final String elementText) {
      // Exclude the group reference '::' starting characters
      final var length = elementText.length();
      return length > 2
          ? TextRange.create(2, length)
          : TextRange.EMPTY_RANGE;
    }
  }

  private static class PomskyLeafElementTokenizer extends Tokenizer<LeafPsiElement> {
    private final boolean useRename;

    protected PomskyLeafElementTokenizer(final boolean useRename) {
      this.useRename = useRename;
    }

    public void tokenize(
        @NotNull final LeafPsiElement element,
        @NotNull final TokenConsumer consumer) {
      final var text = element.getText();

      if (text.length() > 0) {
        final var rangeToCheck = getRangeToCheck(element, text);

        if (!rangeToCheck.isEmpty()) {
          consumer.consumeToken(
              element,
              text,
              useRename,
              0,
              rangeToCheck,
              IdentifierSplitter.getInstance()
          );
        }
      }
    }

    @NotNull
    protected TextRange getRangeToCheck(
        @NotNull final LeafPsiElement element,
        @NotNull final String elementText) {
      return TextRange.allOf(elementText);
    }
  }
}
