package com.github.lppedd.idea.pomsky.lang.lexer;

import com.intellij.lexer.FlexLexer;

/**
 * @author Edoardo Luppi
 */
public interface PomskyEofFlexLexer extends FlexLexer {
  boolean isEof();

  void setEof(final boolean isEof);
}
