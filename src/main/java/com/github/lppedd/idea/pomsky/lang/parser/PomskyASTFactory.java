package com.github.lppedd.idea.pomsky.lang.parser;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyIdentifierPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyTypes;
import com.intellij.lang.ASTFactory;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author Edoardo Luppi
 */
public class PomskyASTFactory extends ASTFactory {
  private static final Map<IElementType, BiFunction<IElementType, CharSequence, LeafElement>> LEAFS = Map.ofEntries(
      Map.entry(PomskyTypes.IDENTIFIER, PomskyIdentifierPsiElement::new)
  );

  @Nullable
  @Override
  public LeafElement createLeaf(
      @NotNull final IElementType type,
      @NotNull final CharSequence text) {
    final var producer = LEAFS.get(type);
    return producer != null
        ? producer.apply(type, text)
        : super.createLeaf(type, text);
  }
}
