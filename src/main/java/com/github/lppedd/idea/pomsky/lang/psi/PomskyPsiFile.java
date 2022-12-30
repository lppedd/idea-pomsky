package com.github.lppedd.idea.pomsky.lang.psi;

import com.github.lppedd.idea.pomsky.lang.PomskyFileType;
import com.github.lppedd.idea.pomsky.lang.PomskyLanguage;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyPsiFile extends PsiFileBase {
  public PomskyPsiFile(@NotNull final FileViewProvider viewProvider) {
    super(viewProvider, PomskyLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return PomskyFileType.INSTANCE;
  }

  @Override
  public String toString() {
    return "Pomsky file";
  }
}
