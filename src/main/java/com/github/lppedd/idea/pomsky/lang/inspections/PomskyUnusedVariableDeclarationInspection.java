package com.github.lppedd.idea.pomsky.lang.inspections;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyPsiElementVisitor;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyVariableDeclarationPsiElement;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.search.searches.ReferencesSearch;
import org.jetbrains.annotations.NotNull;

/**
 * Reports variables that are not used and offers a fix to remove them.
 *
 * @author Edoardo Luppi
 * @see PomskyRemoveUnusedVariableDeclarationFix
 */
class PomskyUnusedVariableDeclarationInspection extends LocalInspectionTool {
  @NotNull
  @Override
  public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, final boolean isOnTheFly) {
    return new PomskyUnusedVariableVisitor(holder);
  }

  private static class PomskyUnusedVariableVisitor extends PomskyPsiElementVisitor {
    final ProblemsHolder holder;

    PomskyUnusedVariableVisitor(@NotNull final ProblemsHolder holder) {
      this.holder = holder;
    }

    @Override
    public void visitVariableDeclaration(@NotNull final PomskyVariableDeclarationPsiElement element) {
      final var reference = ReferencesSearch.search(element, element.getUseScope()).findFirst();

      if (reference != null && reference.resolve() != null) {
        return;
      }

      // TODO: manually calculate the text range to avoid messing up
      //  in case the PSI structure changes
      final var rangeInElement = element.getIdentifier().getTextRangeInParent();
      final var variableName = element.getName();

      // The default severity level and text attribute key are defined in plugin.xml.
      // We do not specify them here as we want the user to be able to customize them
      holder.registerProblem(
          element,
          rangeInElement,
          "Variable '%s' is never used".formatted(variableName),
          new PomskyRemoveUnusedVariableDeclarationFix(variableName)
      );
    }
  }
}
