package com.github.lppedd.idea.pomsky.process;

import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public interface PomskyCompileListener {
  /**
   * Called when compilation has been requested for a file.
   */
  default void compileRequested(
      @NotNull final VirtualFile compiledFile) {}

  /**
   * Called when compilation for a file has finished, successfully or not.
   * <p>
   * To test if the compilation has been successful, use {@link PomskyCompileResult#hasError()}.
   */
  default void compileFinished(
      @NotNull final VirtualFile compiledFile,
      @NotNull final PomskyCompileResult result) {}

  /**
   * Called when compilation for a file has been canceled, e.g., by the user.
   */
  default void compileCanceled(
      @NotNull final VirtualFile compiledFile) {}

  /**
   * Called when compilation for a file could not be completed because of a fatal error.
   */
  default void compileErrored(
      @NotNull final VirtualFile compiledFile,
      @NotNull final Throwable error) {}
}
