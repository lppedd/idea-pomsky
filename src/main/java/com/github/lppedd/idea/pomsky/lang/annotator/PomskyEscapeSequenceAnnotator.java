package com.github.lppedd.idea.pomsky.lang.annotator;

import com.github.lppedd.idea.pomsky.lang.PomskyHighlighterColors;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyPsiElementVisitor;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyStringLiteralPsiElement;
import com.intellij.codeInsight.daemon.impl.HighlightInfoType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * @author Edoardo Luppi
 */
public class PomskyEscapeSequenceAnnotator implements Annotator, DumbAware {
  private static final Pattern PATTERN_ESCAPE_SEQUENCE = Pattern.compile("\\\\\\\\|\\\\\"");

  @Override
  public void annotate(
      @NotNull final PsiElement element,
      @NotNull final AnnotationHolder holder) {
    new PomskyEscapeSequenceAnnotatorProcess(element, holder).execute();
  }

  private static class PomskyEscapeSequenceAnnotatorProcess extends PomskyPsiElementVisitor {
    final PsiElement element;
    final AnnotationHolder holder;

    PomskyEscapeSequenceAnnotatorProcess(
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
      // Colorize escape sequences
      final var matcher = PATTERN_ESCAPE_SEQUENCE.matcher(element.getText());
      final var startOffset = element.getTextRange().getStartOffset();

      while (matcher.find()) {
        holder.newSilentAnnotation(HighlightInfoType.SYMBOL_TYPE_SEVERITY)
            .textAttributes(PomskyHighlighterColors.STRING_ESCAPE)
            .range(TextRange.create(startOffset + matcher.start(), startOffset + matcher.end()))
            .create();
      }
    }
  }
}
