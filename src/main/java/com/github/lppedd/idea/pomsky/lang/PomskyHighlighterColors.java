package com.github.lppedd.idea.pomsky.lang;

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

/**
 * @author Edoardo Luppi
 */
public class PomskyHighlighterColors {
  public static final TextAttributesKey STRING = createTextAttributesKey("POMSKY_STRING", DefaultLanguageHighlighterColors.STRING);
  public static final TextAttributesKey NUMBER = createTextAttributesKey("POMSKY_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
  public static final TextAttributesKey COMMENT = createTextAttributesKey("POMSKY_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
  public static final TextAttributesKey KEYWORD = createTextAttributesKey("POMSKY_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
  public static final TextAttributesKey IDENTIFIER = createTextAttributesKey("POMSKY_IDENTIFIER", DefaultLanguageHighlighterColors.LOCAL_VARIABLE);
  public static final TextAttributesKey IDENTIFIER_BUILTIN = createTextAttributesKey("POMSKY_IDENTIFIER_BUILTIN", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE);
  public static final TextAttributesKey COMMA = createTextAttributesKey("POMSKY_COMMA", DefaultLanguageHighlighterColors.COMMA);
  public static final TextAttributesKey SEMICOLON = createTextAttributesKey("POMSKY_SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON);
  public static final TextAttributesKey BOUNDARY = createTextAttributesKey("POMSKY_BOUNDARY");
  public static final TextAttributesKey QUANTIFIER = createTextAttributesKey("POMSKY_QUANTIFIER");
  public static final TextAttributesKey NEGATION = createTextAttributesKey("POMSKY_NEGATION");
  public static final TextAttributesKey LOOKAROUND = createTextAttributesKey("POMSKY_LOOKAROUND");
  public static final TextAttributesKey GROUP_NAME = createTextAttributesKey("POMSKY_GROUP_NAME");
  public static final TextAttributesKey GROUP_REFERENCE = createTextAttributesKey("POMSKY_GROUP_REFERENCE");
  public static final TextAttributesKey STRING_ESCAPE = createTextAttributesKey("POMSKY_STRING_ESCAPE", DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE);
}
