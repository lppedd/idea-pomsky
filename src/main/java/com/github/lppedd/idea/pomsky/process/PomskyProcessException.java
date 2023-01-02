package com.github.lppedd.idea.pomsky.process;

import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyProcessException extends Exception {
  public PomskyProcessException(@NotNull final String message) {
    super(message);
  }

  public PomskyProcessException(@NotNull final String message, @NotNull final Throwable cause) {
    super(message, cause);
  }
}
