package com.github.lppedd.idea.pomsky.lang.annotator;

import com.github.lppedd.idea.pomsky.lang.PomskyBuiltins;
import com.github.lppedd.idea.pomsky.lang.PomskyNameHelper;
import com.github.lppedd.idea.pomsky.lang.psi.*;
import com.github.lppedd.idea.pomsky.util.StringUtils;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Edoardo Luppi
 * @see PomskyBuiltinAnnotator
 * @see PomskyEscapeSequenceAnnotator
 */
public class PomskyRootAnnotator implements Annotator, DumbAware {
  @Override
  public void annotate(
      @NotNull final PsiElement element,
      @NotNull final AnnotationHolder holder) {
    new PomskyRootAnnotatorProcess(element, holder).execute();
  }

  private static class PomskyRootAnnotatorProcess extends PomskyPsiElementVisitor {
    final PsiElement element;
    final AnnotationHolder holder;

    PomskyRootAnnotatorProcess(
        @NotNull final PsiElement element,
        @NotNull final AnnotationHolder holder) {
      this.element = element;
      this.holder = holder;
    }

    void execute() {
      element.accept(this);
    }

    @Override
    public void visitStringLiteral(@NotNull final PomskyStringLiteralPsiElement element) {
      final var text = element.getText();
      final var textLength = text.length();

      // Report unclosed string literals
      if (textLength == 1 || isSingleQuotesUnclosed(text) || isDoubleQuotesUnclosed(text)) {
        holder.newAnnotation(HighlightSeverity.ERROR, "String literal doesn't have a closing quote")
            .range(element.getTextRange())
            .create();
      }

      // Report unescaped double quotes and backslashes
      if (text.startsWith("\"")) {
        var i = text.indexOf('\\');

        while (i > -1) {
          if (i >= textLength - 1 || (text.charAt(i + 1) != '\\' && text.charAt(i + 1) != '"')) {
            final var elementStartOffset = element.getTextRange().getStartOffset();
            final var startOffset = elementStartOffset + i;
            final var endOffset = elementStartOffset + Math.min(i + 2, text.length());
            holder.newAnnotation(HighlightSeverity.ERROR, "Unsupported escape sequence in string literal")
                .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                .range(TextRange.create(startOffset, endOffset))
                .create();
          }

          i = text.indexOf('\\', i + 2);
        }
      }
    }

    @Override
    public void visitIdentifier(@NotNull final PomskyIdentifierPsiElement element) {
      if (element.getParent() instanceof PomskyVariableDeclarationPsiElement) {
        return;
      }

      final var isInCharacterSet = PsiTreeUtil.getParentOfType(element, PomskyCharacterSetExpressionPsiElement.class) != null;

      if (isInCharacterSet) {
        if (PomskyBuiltins.CharacterClasses.is(element.getName()) ||
            PomskyBuiltins.Properties.is(element.getName())) {
          return;
        }

        final var message = "Unknown character class '%s'".formatted(element.getName());
        holder.newAnnotation(HighlightSeverity.ERROR, message)
            .range(element.getTextRange())
            .create();
        return;
      }

      // Report identifiers that refer to a non-existing variable
      final var reference = element.getReference();

      if (reference == null || reference.resolve() == null) {
        // Check if the element refer to a built-in variable or property
        if (PomskyBuiltins.Variables.is(element.getName())) {
          return;
        }

        final var message = "Variable '%s' doesn't exist".formatted(element.getName());
        holder.newAnnotation(HighlightSeverity.ERROR, message)
            .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
            .range(element.getTextRange())
            .create();
      }
    }

    @Override
    public void visitGroupReference(@NotNull final PomskyGroupReferencePsiElement element) {
      // Report group references without a name or number
      if (element.getName().isEmpty()) {
        holder.newAnnotation(HighlightSeverity.ERROR, "Expected number or name of a group")
            .range(element.getTextRange())
            .create();
        return;
      }

      // Report group references that refer to a non-existing group
      final var reference = element.getReference();

      if (reference == null || reference.resolve() == null) {
        holder.newAnnotation(HighlightSeverity.ERROR, "Reference to unknown group")
            .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
            .range(element.getTextRange())
            .create();
      }
    }

    @Override
    public void visitVariableDeclaration(@NotNull final PomskyVariableDeclarationPsiElement element) {
      // Report variables declared after any other expression
      final var previousElement = PsiTreeUtil.skipSiblingsBackward(
          element,
          PsiComment.class,
          PsiWhiteSpace.class,
          PomskyVariableDeclarationPsiElement.class
      );

      if (previousElement instanceof PomskyExpressionPsiElement) {
        holder.newAnnotation(HighlightSeverity.ERROR, "Variable declaration must come before expressions")
            .range(element.getTextRange())
            .create();
      }

      // Report variables that have been defined already in the same scope (same name)
      final var previousDeclaration = getPreviousVariableDeclaration(element);

      if (previousDeclaration != null) {
        holder.newAnnotation(HighlightSeverity.ERROR, "A variable with the same name already exists in this scope")
            .withFix(new PomskyNavigateToPreviouslyDeclaredElementAction(previousDeclaration))
            .range(element.getIdentifier().getTextRange())
            .create();
      }

      // Report invalid variable names
      final var message = PomskyNameHelper.getInstance().validateIdentifier(element.getName());

      if (message != null) {
        holder.newAnnotation(HighlightSeverity.ERROR, message)
            .range(element.getIdentifier().getTextRange())
            .create();
      }
    }

    @Override
    public void visitNamedCapturingGroup(@NotNull final PomskyNamedCapturingGroupExpressionPsiElement element) {
      // Report named groups that have been defined already (same name)
      final var file = element.getContainingFile();
      final var processor = new PomskyFirstNamedCapturingGroupFinderProcessor(element);
      PsiTreeUtil.processElements(file, PomskyNamedCapturingGroupExpressionPsiElement.class, processor);
      final var previousDeclaration = processor.getFoundElement();

      if (previousDeclaration != null) {
        final var message = "Group name '%s' used multiple times".formatted(element.getName());
        holder.newAnnotation(HighlightSeverity.ERROR, message)
            .withFix(new PomskyNavigateToPreviouslyDeclaredElementAction(previousDeclaration))
            .range(element.getGroupName().getTextRange())
            .create();
      }

      // Report invalid group names
      final var message = PomskyNameHelper.getInstance().validateGroupName(element.getName());

      if (message != null) {
        holder.newAnnotation(HighlightSeverity.ERROR, message)
            .range(element.getGroupName().getTextRange())
            .create();
      }
    }

    @Nullable
    PomskyVariableDeclarationPsiElement getPreviousVariableDeclaration(
        @NotNull final PomskyVariableDeclarationPsiElement declaration) {
      PomskyVariableDeclarationPsiElement previousDeclaration = null;
      PsiElement previous = declaration;

      while ((previous = previous.getPrevSibling()) != null) {
        if (previous instanceof final PomskyVariableDeclarationPsiElement other &&
            other.getName().equals(declaration.getName())) {
          previousDeclaration = other;
        }
      }

      return previousDeclaration;
    }

    boolean isSingleQuotesUnclosed(@NotNull final String str) {
      return str.startsWith("'") && !str.endsWith("'");
    }

    boolean isDoubleQuotesUnclosed(@NotNull final String str) {
      return str.startsWith("\"") &&
             !(str.endsWith("\"") && StringUtils.countOccurrencesBackwards(str, '\\', str.length() - 2) % 2 == 0);
    }
  }
}
