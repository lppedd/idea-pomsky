//
// IntelliJ IDEA JFLex lexer for the Pomsky language
// Author: Edoardo Luppi
//
package com.github.lppedd.idea.pomsky.lang.lexer;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyTypes;
import com.intellij.psi.PlainTextTokenTypes;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;

%%

%class PomskyFlexLexer
%implements PomskyEofFlexLexer
%function advance
%type IElementType
%unicode

%{

  private boolean isEof;

  @Override
  public boolean isEof() {
    return isEof;
  }

  @Override
  public void setEof(final boolean isEof) {
    this.isEof = isEof;
  }

%}

// Primitives
NewLine         = \n
Space           = [ \t]
Identifier      = [a-zA-Z_][a-zA-Z_0-9]*
Number          = [+-]?[0-9_.,]+
CodePoint       = U\+?[a-fA-F0-9]{1,6}

// Complex tokens
Comment         = #.*
Keyword         = let | enable | disable | lazy | greedy | atomic | range | regex | base | if | else

%xstate STRING_SINGLE
%xstate STRING_DOUBLE
%xstate GROUP_EXPRESSION

%%

<YYINITIAL> {
      {NewLine}+ | {Space}+ {
          return TokenType.WHITE_SPACE;
      }

      {Comment} {
          return PomskyTypes.COMMENT;
      }

      {Keyword} {
          return PomskyTypes.KEYWORD;
      }

      {CodePoint} {
          return PomskyTypes.CODE_POINT;
      }

      [\^$] | \!?% | Start | End {
          return PomskyTypes.BOUNDARY;
      }

      {Identifier} {
          return PomskyTypes.IDENTIFIER;
      }

      ' / [\s\S] {
          yybegin(STRING_SINGLE);
      }

      \" / [\s\S] {
          yybegin(STRING_DOUBLE);
      }

      [0-9]+ {
          return PomskyTypes.NUMBER;
      }

      ::({Identifier} | {Number})? {
          return PomskyTypes.GROUP_REFERENCE;
      }

      , {
          return PomskyTypes.COMMA;
      }

      ; {
          return PomskyTypes.SEMICOLON;
      }

      : {
          yybegin(GROUP_EXPRESSION);
          return PomskyTypes.COLON;
      }

      = {
          return PomskyTypes.EQ;
      }

      [*+?] {
          return PomskyTypes.QUANTIFIER;
      }

      \| {
          return PomskyTypes.UNION;
      }

      \[ {
          return PomskyTypes.CLASS_BEGIN;
      }

      ] {
          return PomskyTypes.CLASS_END;
      }

      \( {
          return PomskyTypes.GROUP_BEGIN;
      }

      \) {
          return PomskyTypes.GROUP_END;
      }

      \{ {
          return PomskyTypes.LBRACE;
      }

      \} {
          return PomskyTypes.RBRACE;
      }

      >> {
          return PomskyTypes.LOOKAHEAD;
      }

      \<< {
          return PomskyTypes.LOOKBEHIND;
      }

      \!>> {
          return PomskyTypes.LOOKAHEAD_NEGATED;
      }

      \!<< {
          return PomskyTypes.LOOKBEHIND_NEGATED;
      }

      \! {
          return PomskyTypes.NEGATION;
      }

      - {
          return PomskyTypes.RANGE_SEPARATOR;
      }

      \. {
          return PomskyTypes.DOT;
      }

      [^] {
          return PlainTextTokenTypes.PLAIN_TEXT;
      }
}

// A literal string in the form: 'example of string' or 'example of \'string\''
<STRING_SINGLE> {
      ' {
          yybegin(YYINITIAL);
          return PomskyTypes.STRING;
      }
}

// A literal string in the form: "example of string" or "example of \"string\""
<STRING_DOUBLE> {
      \\\\ {
          // Keep going
      }

      \\\" {
          // Keep going
      }

      \" {
          yybegin(YYINITIAL);
          return PomskyTypes.STRING;
      }
}

<STRING_SINGLE, STRING_DOUBLE> {
      [^] {
          // Keep going
      }

      <<EOF>> {
          if (isEof()) {
            return null;
          }

          setEof(true);
          return PomskyTypes.STRING;
      }
}

<GROUP_EXPRESSION> {
      {Identifier} | {Number} {
          return PomskyTypes.GROUP_NAME;
      }

      [^] {
          yypushback(yylength());
          yybegin(YYINITIAL);
      }
}
