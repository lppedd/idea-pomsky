package com.github.lppedd.idea.pomsky.lang;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyPsiElementVisitor;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyStringLiteralPsiElement;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyBasicAnnotator implements Annotator {
  @Override
  public void annotate(
      @NotNull final PsiElement element,
      @NotNull final AnnotationHolder holder) {
    new PomskyAnnotatorProcess(element, holder);
  }

  private static class PomskyAnnotatorProcess extends PomskyPsiElementVisitor {
    private final AnnotationHolder holder;

    PomskyAnnotatorProcess(
        @NotNull final PsiElement element,
        @NotNull final AnnotationHolder holder) {
      this.holder = holder;
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
  }
}
