package com.github.lppedd.idea.pomsky.process;

import com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public interface PomskyProcess {
  /**
   * Returns the Pomsky version for this process.
   *
   * @throws PomskyProcessException in case of fatal error during process execution
   */
  @NotNull
  String getVersion(@NotNull final ProgressIndicator indicator) throws PomskyProcessException;

  /**
   * Compiles the Pomsky code to obtain a regular expression of the default flavor.
   *
   * @throws PomskyProcessException in case of fatal error during process execution
   */
  @NotNull
  PomskyCompileResult compile(
      @NotNull final String code,
      @NotNull final ProgressIndicator indicator) throws PomskyProcessException;

  /**
   * Compiles the Pomsky code to obtain a regular expression of a specific flavor.
   *
   * @throws PomskyProcessException in case of fatal error during process execution
   */
  @NotNull
  PomskyCompileResult compile(
      @NotNull final String code,
      @NotNull final PomskyRegexpFlavor regexpFlavor,
      @NotNull final ProgressIndicator indicator) throws PomskyProcessException;
}
