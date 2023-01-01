package com.github.lppedd.idea.pomsky.lang.parser;

import com.github.lppedd.idea.pomsky.lang.PomskyLanguage;
import com.github.lppedd.idea.pomsky.lang.lexer.PomskyLexer;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyPsiFile;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyTypes;
import com.github.lppedd.idea.pomsky.lang.psi.impl.PomskyCharacterSetExpressionPsiElementImpl;
import com.github.lppedd.idea.pomsky.lang.psi.impl.PomskyExpressionPsiElementImpl;
import com.github.lppedd.idea.pomsky.lang.psi.impl.PomskyNamedCapturingGroupExpressionPsiElementImpl;
import com.github.lppedd.idea.pomsky.lang.psi.impl.PomskyVariableDeclarationPsiElementImpl;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;

/**
 * @author Edoardo Luppi
 */
public class PomskyParserDefinition implements ParserDefinition {
  private static final IFileElementType FILE = new IFileElementType(PomskyLanguage.INSTANCE);
  private static final TokenSet TOKENSET_COMMENT = TokenSet.create(PomskyTypes.COMMENT);
  private static final Map<IElementType, Function<ASTNode, PsiElement>> ELEMENTS = Map.ofEntries(
      Map.entry(PomskyTypes.VARIABLE_DECLARATION, PomskyVariableDeclarationPsiElementImpl::new),
      Map.entry(PomskyTypes.EXPRESSION, PomskyExpressionPsiElementImpl::new),
      Map.entry(PomskyTypes.CHARACTER_SET_EXPRESSION, PomskyCharacterSetExpressionPsiElementImpl::new),
      Map.entry(PomskyTypes.GROUP_EXPRESSION_CAPTURING_NAMED, PomskyNamedCapturingGroupExpressionPsiElementImpl::new)
  );

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
  public PsiElement createElement(@NotNull final ASTNode node) {
    final var elementType = node.getElementType();
    final var producer = ELEMENTS.getOrDefault(elementType, ASTWrapperPsiElement::new);
    return producer.apply(node);
  }
}
