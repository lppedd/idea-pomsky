package com.github.lppedd.idea.pomsky.lang.lexer;

import com.intellij.lexer.FlexAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyLexer extends FlexAdapter {
  public PomskyLexer() {
    super(new PomskyFlexLexer(null));
  }

  @Override
  public PomskyEofFlexLexer getFlex() {
    return (PomskyEofFlexLexer) super.getFlex();
  }

  @Override
  public void start(@NotNull CharSequence buffer, int startOffset, int endOffset, int initialState) {
    getFlex().setEof(false);
    super.start(buffer, startOffset, endOffset, initialState);
  }
}
