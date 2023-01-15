package com.github.lppedd.idea.pomsky.lang.annotator;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Edoardo Luppi
 */
class PomskyNavigateToPreviouslyDeclaredElementFix implements IntentionAction {
  private final NavigatablePsiElement element;

  PomskyNavigateToPreviouslyDeclaredElementFix(@NotNull final NavigatablePsiElement element) {
    this.element = element;
  }

  @NotNull
  @Override
  public String getFamilyName() {
    return "Pomsky - Navigate to previous declaration";
  }

  @NotNull
  @Override
  public String getText() {
    return "Navigate to previously declared element";
  }

  @Override
  public boolean isAvailable(
      @NotNull final Project project,
      @Nullable final Editor editor,
      @Nullable final PsiFile file) {
    return element.isValid();
  }

  @Override
  public void invoke(
      @NotNull final Project project,
      @Nullable final Editor editor,
      @Nullable final PsiFile file) throws IncorrectOperationException {
    element.navigate(true);
  }

  @Override
  public boolean startInWriteAction() {
    return false;
  }
}
