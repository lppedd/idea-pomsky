package com.github.lppedd.idea.pomsky.lang.annotator;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyIdentifierPsiElement;
import com.intellij.codeInsight.highlighting.HighlightUsagesHandlerBase;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Consumer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Edoardo Luppi
 */
class PomskyBuiltinsHighlightUsagesHandler extends HighlightUsagesHandlerBase<PomskyIdentifierPsiElement> {
  private final PomskyIdentifierPsiElement identifier;

  PomskyBuiltinsHighlightUsagesHandler(
      @NotNull final Editor editor,
      @NotNull final PsiFile file,
      @NotNull final PomskyIdentifierPsiElement identifier) {
    super(editor, file);
    this.identifier = identifier;
  }

  @NotNull
  @Override
  public List<PomskyIdentifierPsiElement> getTargets() {
    return List.of(identifier);
  }

  @Override
  protected void selectTargets(
      @NotNull final List<? extends PomskyIdentifierPsiElement> targets,
      @NotNull final Consumer<? super List<? extends PomskyIdentifierPsiElement>> selectionConsumer) {
    selectionConsumer.consume(targets);
  }

  @Override
  public void computeUsages(@NotNull final List<? extends PomskyIdentifierPsiElement> targets) {
    for (final var identifier : targets) {
      final var file = identifier.getContainingFile();
      final var name = identifier.getName();
      PsiTreeUtil.processElements(file, PomskyIdentifierPsiElement.class, element -> {
        if (element.getName().equals(name)) {
          addOccurrence(element);
        }

        return true;
      });
    }
  }
}
