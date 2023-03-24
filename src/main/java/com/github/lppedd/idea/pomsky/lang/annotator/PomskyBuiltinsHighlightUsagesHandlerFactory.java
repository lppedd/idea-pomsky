package com.github.lppedd.idea.pomsky.lang.annotator;

import com.github.lppedd.idea.pomsky.lang.PomskyBuiltins;
import com.github.lppedd.idea.pomsky.lang.PomskyLanguage;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyIdentifierPsiElement;
import com.intellij.codeInsight.highlighting.HighlightUsagesHandlerFactoryBase;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Edoardo Luppi
 */
class PomskyBuiltinsHighlightUsagesHandlerFactory extends HighlightUsagesHandlerFactoryBase {
  @Nullable
  @Override
  public PomskyBuiltinsHighlightUsagesHandler createHighlightUsagesHandler(
      @NotNull final Editor editor,
      @NotNull final PsiFile file,
      @NotNull final PsiElement target) {
    return file.getLanguage().isKindOf(PomskyLanguage.INSTANCE) && isBuiltinElement(target)
        ? new PomskyBuiltinsHighlightUsagesHandler(editor, file, (PomskyIdentifierPsiElement) target)
        : null;
  }

  private boolean isBuiltinElement(@NotNull final PsiElement element) {
    if (!(element instanceof final PomskyIdentifierPsiElement identifier)) {
      return false;
    }

    final var reference = identifier.getReference();
    final var hasNoReferences = reference == null || reference.resolve() == null;
    final var name = identifier.getName();
    return hasNoReferences && PomskyBuiltins.Variables.is(name);
  }
}
