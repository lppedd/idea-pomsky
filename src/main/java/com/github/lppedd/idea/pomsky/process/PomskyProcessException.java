package com.github.lppedd.idea.pomsky.process;

import com.github.lppedd.idea.pomsky.support.PomskyActionsException;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyProcessException extends PomskyActionsException {
  public PomskyProcessException(@NotNull final String message) {
    super(message);
  }

  public PomskyProcessException(@NotNull final String message, @NotNull final Throwable cause) {
    super(message, cause);
  }
}
