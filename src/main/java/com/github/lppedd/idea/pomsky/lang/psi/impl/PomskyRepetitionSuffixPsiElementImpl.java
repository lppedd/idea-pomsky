package com.github.lppedd.idea.pomsky.lang.psi.impl;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyRepetitionSuffixPsiElement;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyRepetitionSuffixPsiElementImpl extends ASTWrapperPsiElement implements PomskyRepetitionSuffixPsiElement {
  public PomskyRepetitionSuffixPsiElementImpl(@NotNull final ASTNode node) {
    super(node);
  }
}
