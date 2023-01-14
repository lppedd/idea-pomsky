package com.github.lppedd.idea.pomsky.lang.completion;

import com.github.lppedd.idea.pomsky.lang.PomskyLanguage;
import com.intellij.codeInsight.AutoPopupController;
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
class PomskyTypedHandler extends TypedHandlerDelegate {
  @NotNull
  @Override
  public Result checkAutoPopup(
      final char charTyped,
      @NotNull final Project project,
      @NotNull final Editor editor,
      @NotNull final PsiFile file) {
    if (file.getLanguage() == PomskyLanguage.INSTANCE && isCharValid(charTyped)) {
      AutoPopupController.getInstance(project).scheduleAutoPopup(editor);
      return Result.STOP;
    }

    return super.checkAutoPopup(charTyped, project, editor, file);
  }

  private boolean isCharValid(final char c) {
    return Character.isLetterOrDigit(c) || c == ':' || c == '_';
  }
}
