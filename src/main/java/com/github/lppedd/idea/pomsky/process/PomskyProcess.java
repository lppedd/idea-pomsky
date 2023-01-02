package com.github.lppedd.idea.pomsky.process;

import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public interface PomskyProcess {
  /**
   * Returns the Pomsky version for this process.
   */
  @NotNull
  String getVersion() throws PomskyProcessException;

  /**
   * Compiles the Pomsky code to obtain a regular expression of the default flavor.
   */
  @NotNull
  PomskyCompileResult compile(@NotNull final String code) throws PomskyProcessException;

  /**
   * Compiles the Pomsky code to obtain a regular expression of a specific flavor.
   */
  @NotNull
  PomskyCompileResult compile(
      @NotNull final String code,
      @NotNull final PomskyRegexpFlavor flavor) throws PomskyProcessException;
}
