package com.github.lppedd.idea.pomsky.lang.completion;

import com.github.lppedd.idea.pomsky.PomskyIcons;
import com.github.lppedd.idea.pomsky.lang.psi.*;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.Key;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import static com.intellij.codeInsight.TailType.insertChar;
import static com.intellij.psi.util.PsiTreeUtil.findChildrenOfType;

/**
 * @author Edoardo Luppi
 */
public class PomskyCompletionContributor extends CompletionContributor implements DumbAware {
  public static final Key<Boolean> KEY_KEYWORD = Key.create("pomskyCompletionKeyword");

  PomskyCompletionContributor() {
    extend(CompletionType.BASIC, variablePattern(), new VariableCompletionProvider());
    extend(CompletionType.BASIC, namedGroupPattern(), new NamedGroupCompletionProvider());
  }

  @NotNull
  private PsiElementPattern.Capture<PomskyIdentifierPsiElement> variablePattern() {
    return PlatformPatterns
        .psiElement(PomskyIdentifierPsiElement.class)
        .inside(PomskyExpressionPsiElement.class);
  }

  @NotNull
  private PsiElementPattern.Capture<PomskyGroupReferencePsiElement> namedGroupPattern() {
    return PlatformPatterns
        .psiElement(PomskyGroupReferencePsiElement.class)
        .inside(PomskyExpressionPsiElement.class);
  }

  @NotNull
  @SuppressWarnings("SameParameterValue")
  private static LookupElement createKeywordElement(@NotNull final String keyword) {
    final var element = LookupElementBuilder.create(keyword)
        .withTypeText("Keyword")
        .withInsertHandler((ctx, item) -> insertChar(ctx.getEditor(), ctx.getTailOffset(), ' '))
        .bold();

    element.putUserData(KEY_KEYWORD, Boolean.TRUE);
    return element;
  }

  private static class VariableCompletionProvider extends CompletionProvider<CompletionParameters> {
    @Override
    protected void addCompletions(
        @NotNull final CompletionParameters parameters,
        @NotNull final ProcessingContext context,
        @NotNull final CompletionResultSet result) {
      final var psiFile = parameters.getOriginalFile();
      final var variableDeclarations = findChildrenOfType(psiFile, PomskyVariableDeclarationPsiElement.class);

      for (final var variableDeclaration : variableDeclarations) {
        result.addElement(
            LookupElementBuilder.create(variableDeclaration)
                .withIcon(PomskyIcons.LOGO)
                .withTypeText("Variable")
        );
      }

      result.addElement(createKeywordElement("let"));
    }
  }

  private static class NamedGroupCompletionProvider extends CompletionProvider<CompletionParameters> {
    @Override
    protected void addCompletions(
        @NotNull final CompletionParameters parameters,
        @NotNull final ProcessingContext context,
        @NotNull final CompletionResultSet result) {
      final var psiFile = parameters.getOriginalFile();
      final var namedGroups = findChildrenOfType(psiFile, PomskyNamedCapturingGroupExpressionPsiElement.class);

      for (final var namedGroup : namedGroups) {
        result.addElement(
            LookupElementBuilder.create(namedGroup)
                .withIcon(PomskyIcons.LOGO)
                .withTypeText("Named group")
        );
      }
    }
  }
}
