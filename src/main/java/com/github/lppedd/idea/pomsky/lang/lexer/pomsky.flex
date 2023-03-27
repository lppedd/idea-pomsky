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
Whitespace      = \s+
Number          = [0-9_]+
NonPrintable    = [nrtaef]
CodePoint       = U{Whitespace}*\+{Whitespace}*[a-fA-F0-9]{1,6}
Identifier      = [\p{Alpha}_][\p{Alpha}\p{N}_]*
GroupName       = [\p{Alpha}\p{N}_-]* // This is a relaxed variant. The correct regexp is [a-zA-Z][a-zA-Z0-9]*

// Complex tokens
Comment         = #.*
Keyword         = let
                | enable
                | disable
                | unicode
                | lazy
                | greedy
                | atomic
                | range
                | regex
                | base
                | if
                | else
                | test
                | U

%xstate STRING_SINGLE
%xstate STRING_DOUBLE
%xstate GROUP_EXPRESSION

%%

<YYINITIAL> {
      {Whitespace} {
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

      {NonPrintable} {
          return PomskyTypes.NON_PRINTABLE;
      }

      {Identifier} {
          return PomskyTypes.IDENTIFIER;
      }

      ' {
          yybegin(STRING_SINGLE);
      }

      \" {
          yybegin(STRING_DOUBLE);
      }

      [0-9]+ {
          return PomskyTypes.NUMBER;
      }

      ::({GroupName} | {Number})? {
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
      {GroupName} {
          return PomskyTypes.GROUP_NAME;
      }

      [^] {
          yypushback(yylength());
          yybegin(YYINITIAL);
      }
}
