package com.github.lppedd.idea.pomsky.support;

import com.intellij.openapi.actionSystem.AnAction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Edoardo Luppi
 */
public class PomskyActionsException extends Exception {
  private final Collection<AnAction> actions = new ArrayList<>(4);

  public PomskyActionsException(@NotNull final String message) {
    super(message);
  }

  public PomskyActionsException(@NotNull final String message, @NotNull final Throwable cause) {
    super(message, cause);
  }

  /**
   * Returns the attached actions.
   */
  @NotNull
  public Collection<AnAction> getActions() {
    return actions;
  }

  /**
   * Adds an action that <em>might</em> be used by whoever catches the exception.
   * <p>
   * Actions usage is not guaranteed.
   */
  public void addAction(@NotNull final AnAction action) {
    actions.add(action);
  }
}
