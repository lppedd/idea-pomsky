package com.github.lppedd.idea.pomsky.support;

import com.github.lppedd.idea.pomsky.lang.PomskyFileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
class PomskyProblemFileHighlightFilter implements Condition<VirtualFile> {
  @Override
  public boolean value(@NotNull final VirtualFile virtualFile) {
    return FileTypeRegistry.getInstance().isFileOfType(virtualFile, PomskyFileType.INSTANCE);
  }
}
