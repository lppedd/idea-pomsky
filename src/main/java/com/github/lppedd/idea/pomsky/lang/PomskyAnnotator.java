package com.github.lppedd.idea.pomsky.lang;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyGroupReferencePsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyPsiElementVisitor;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyStringLiteralPsiElement;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyAnnotator implements Annotator {
  @Override
  public void annotate(
      @NotNull final PsiElement element,
      @NotNull final AnnotationHolder holder) {
    new PomskyAnnotatorProcess(element, holder);
  }

  private static class PomskyAnnotatorProcess extends PomskyPsiElementVisitor {
    final AnnotationHolder holder;

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

    @Override
    public void visitGroupReference(@NotNull final PomskyGroupReferencePsiElement element) {
      // Report group references without a name or number
      final var groupName = ElementManipulators.getValueText(element);

      if (groupName.isEmpty()) {
        holder.newAnnotation(HighlightSeverity.ERROR, "Expected number or name of a group")
            .range(element.getTextRange())
            .create();
      }
    }
  }
}
