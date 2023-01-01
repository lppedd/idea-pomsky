package com.github.lppedd.idea.pomsky.lang;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyTypes;
import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Edoardo Luppi
 */
public class PomskyBraceMatcher implements PairedBraceMatcher {
  private static final BracePair[] PAIRS = {
      new BracePair(PomskyTypes.GROUP_BEGIN, PomskyTypes.GROUP_END, true),
      new BracePair(PomskyTypes.CLASS_BEGIN, PomskyTypes.CLASS_END, false),
      new BracePair(PomskyTypes.LBRACE, PomskyTypes.RBRACE, false),
  };

  @NotNull
  @Override
  public BracePair @NotNull [] getPairs() {
    return PAIRS;
  }

  @Override
  public boolean isPairedBracesAllowedBeforeType(
      @NotNull final IElementType openingBraceType,
      @Nullable final IElementType contextType) {
    return true;
  }

  @Override
  public int getCodeConstructStart(
      @NotNull final PsiFile file,
      final int openingBraceOffset) {
    return openingBraceOffset;
  }
}
