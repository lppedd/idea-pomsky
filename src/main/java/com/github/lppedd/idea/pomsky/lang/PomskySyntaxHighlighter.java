package com.github.lppedd.idea.pomsky.lang;

import com.github.lppedd.idea.pomsky.lang.lexer.PomskyLexer;
import com.github.lppedd.idea.pomsky.lang.lexer.PomskyTokenType;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyTypes;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author Edoardo Luppi
 */
public class PomskySyntaxHighlighter extends SyntaxHighlighterBase {
  private static final Map<IElementType, TextAttributesKey[]> ATTRIBUTES = Map.ofEntries(
      // Primitives
      Map.entry(PomskyTypes.STRING, pack(PomskyHighlighterColors.STRING)),
      Map.entry(PomskyTypes.NUMBER, pack(PomskyHighlighterColors.NUMBER)),

      Map.entry(PomskyTokenType.COMMENT, pack(PomskyHighlighterColors.COMMENT)),
      Map.entry(PomskyTypes.KEYWORD, pack(PomskyHighlighterColors.KEYWORD)),
      Map.entry(PomskyTypes.IDENTIFIER, pack(PomskyHighlighterColors.IDENTIFIER)),
      Map.entry(PomskyTypes.COMMA, pack(PomskyHighlighterColors.COMMA)),
      Map.entry(PomskyTypes.SEMICOLON, pack(PomskyHighlighterColors.SEMICOLON)),
      Map.entry(PomskyTypes.BOUNDARY, pack(PomskyHighlighterColors.BOUNDARY)),
      Map.entry(PomskyTypes.QUANTIFIER, pack(PomskyHighlighterColors.QUANTIFIER)),
      Map.entry(PomskyTypes.NEGATION, pack(PomskyHighlighterColors.NEGATION)),
      Map.entry(PomskyTypes.RANGE_SEPARATOR, pack(PomskyHighlighterColors.KEYWORD)),

      // Lookaround assertions
      Map.entry(PomskyTypes.LOOKAHEAD, pack(PomskyHighlighterColors.LOOKAROUND)),
      Map.entry(PomskyTypes.LOOKAHEAD_NEGATED, pack(PomskyHighlighterColors.LOOKAROUND)),
      Map.entry(PomskyTypes.LOOKBEHIND, pack(PomskyHighlighterColors.LOOKAROUND)),
      Map.entry(PomskyTypes.LOOKBEHIND_NEGATED, pack(PomskyHighlighterColors.LOOKAROUND)),

      // Grouping
      Map.entry(PomskyTypes.GROUP, pack(PomskyHighlighterColors.GROUP)),
      Map.entry(PomskyTypes.REFERENCE, pack(PomskyHighlighterColors.REFERENCE))
  );

  @NotNull
  @Override
  public Lexer getHighlightingLexer() {
    return new PomskyLexer();
  }

  @NotNull
  @Override
  public TextAttributesKey @NotNull [] getTokenHighlights(final IElementType tokenType) {
    return ATTRIBUTES.getOrDefault(tokenType, TextAttributesKey.EMPTY_ARRAY);
  }
}
