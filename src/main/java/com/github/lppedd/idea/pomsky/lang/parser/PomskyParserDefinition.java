package com.github.lppedd.idea.pomsky.lang.parser;

import com.github.lppedd.idea.pomsky.lang.PomskyLanguage;
import com.github.lppedd.idea.pomsky.lang.lexer.PomskyLexer;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyPsiFile;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyTypes;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyParserDefinition implements ParserDefinition {
  private static final IFileElementType FILE = new IFileElementType(PomskyLanguage.INSTANCE);
  private static final TokenSet TOKENSET_COMMENT = TokenSet.create(PomskyTypes.COMMENT);

  @NotNull
  @Override
  public Lexer createLexer(final Project project) {
    return new PomskyLexer();
  }

  @NotNull
  @Override
  public PsiParser createParser(final Project project) {
    return new PomskyGeneratedParser();
  }

  @NotNull
  @Override
  public IFileElementType getFileNodeType() {
    return FILE;
  }

  @NotNull
  @Override
  public TokenSet getCommentTokens() {
    return TOKENSET_COMMENT;
  }

  @NotNull
  @Override
  public TokenSet getStringLiteralElements() {
    return TokenSet.EMPTY;
  }

  @NotNull
  @Override
  public PsiFile createFile(@NotNull final FileViewProvider viewProvider) {
    return new PomskyPsiFile(viewProvider);
  }

  @NotNull
  @Override
  public PsiElement createElement(final ASTNode node) {
    return new ASTWrapperPsiElement(node);
  }
}
