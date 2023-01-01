package com.github.lppedd.idea.pomsky.lang;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyTypes;
import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyQuoteHandler extends SimpleTokenSetQuoteHandler {
  public PomskyQuoteHandler() {
    super(PomskyTypes.STRING);
  }

  @Override
  public boolean hasNonClosedLiteral(
      @NotNull final Editor editor,
      @NotNull final HighlighterIterator iterator,
      final int offset) {
    return true;
  }
}
