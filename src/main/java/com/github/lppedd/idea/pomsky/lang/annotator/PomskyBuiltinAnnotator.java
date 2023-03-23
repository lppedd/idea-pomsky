package com.github.lppedd.idea.pomsky.lang.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.project.DumbAware;
import com.intellij.psi.PsiElement;
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
}
