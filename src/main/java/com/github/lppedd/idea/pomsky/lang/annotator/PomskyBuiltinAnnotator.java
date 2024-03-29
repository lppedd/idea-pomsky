package com.github.lppedd.idea.pomsky.lang.annotator;

import com.github.lppedd.idea.pomsky.lang.PomskyBuiltins;
import com.github.lppedd.idea.pomsky.lang.PomskyHighlighterColors;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyCharacterSetExpressionPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyIdentifierPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyPsiElementVisitor;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyVariableDeclarationPsiElement;
import com.intellij.codeInsight.daemon.impl.HighlightInfoType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.project.DumbAware;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyBuiltinAnnotator implements Annotator, DumbAware {
  @Override
  public void annotate(
      @NotNull final PsiElement element,
      @NotNull final AnnotationHolder holder) {
    new PomskyBuiltinAnnotatorProcess(element, holder).execute();
  }

  private static class PomskyBuiltinAnnotatorProcess extends PomskyPsiElementVisitor {
    final PsiElement element;
    final AnnotationHolder holder;

    PomskyBuiltinAnnotatorProcess(
        @NotNull final PsiElement element,
        @NotNull final AnnotationHolder holder) {
      this.element = element;
      this.holder = holder;
    }

    void execute() {
      element.accept(this);
    }

    @Override
    public void visitIdentifier(@NotNull final PomskyIdentifierPsiElement element) {
      if (element.getParent() instanceof PomskyVariableDeclarationPsiElement) {
        return;
      }

      if (isBuiltin(element)) {
        holder.newSilentAnnotation(HighlightInfoType.SYMBOL_TYPE_SEVERITY)
            .textAttributes(PomskyHighlighterColors.IDENTIFIER_BUILTIN)
            .range(element.getTextRange())
            .create();
      }
    }

    boolean isBuiltin(@NotNull final PomskyIdentifierPsiElement element) {
      final var name = element.getName();

      if (PsiTreeUtil.getParentOfType(element, PomskyCharacterSetExpressionPsiElement.class) != null) {
        return PomskyBuiltins.Properties.is(name) ||
               PomskyBuiltins.CharacterClasses.is(name);
      } else {
        final var reference = element.getReference();
        return (reference == null || reference.resolve() == null) && PomskyBuiltins.Variables.is(name);
      }
    }
  }
}
