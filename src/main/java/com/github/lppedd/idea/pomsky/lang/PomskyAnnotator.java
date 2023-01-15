package com.github.lppedd.idea.pomsky.lang;

import com.github.lppedd.idea.pomsky.lang.psi.*;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.project.DumbAware;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyAnnotator implements Annotator, DumbAware {
  @Override
  public void annotate(
      @NotNull final PsiElement element,
      @NotNull final AnnotationHolder holder) {
    new PomskyAnnotatorProcess(element, holder).execute();
  }

  private static class PomskyAnnotatorProcess extends PomskyPsiElementVisitor {
    final PsiElement element;
    final AnnotationHolder holder;

    PomskyAnnotatorProcess(
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
      // Report unclosed string literals
      final var text = element.getText();

      if (text.length() == 1 ||
          text.startsWith("'") && !text.endsWith("'") ||
          text.startsWith("\"") && !text.endsWith("\"")) {
        holder.newAnnotation(HighlightSeverity.ERROR, "String literal doesn't have a closing quote")
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

      // Report group references that refer to non-existing groups
      for (final var reference : element.getReferences()) {
        if (reference.resolve() == null) {
          holder.newAnnotation(HighlightSeverity.ERROR, "Reference to unknown group")
              .range(element.getTextRange())
              .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
              .create();
        }
      }
    }

    @Override
    public void visitVariableDeclaration(@NotNull final PomskyVariableDeclarationPsiElement element) {
      // Report variables declared after any other expression
      final var previousElement = PsiTreeUtil.skipWhitespacesBackward(element);

      if (previousElement instanceof PomskyExpressionPsiElement) {
        holder.newAnnotation(HighlightSeverity.ERROR, "Variable declarations must come before expressions")
            .range(element.getTextRange())
            .create();
      }
    }
  }
}
