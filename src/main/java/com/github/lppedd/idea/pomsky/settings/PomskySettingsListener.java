package com.github.lppedd.idea.pomsky.settings;

import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public interface PomskySettingsListener {
  /**
   * Called when the application-level settings have been changed.
   */
  default void settingsChanged(@NotNull final PomskySettingsService settings) {}

  /**
   * Called when the project-level settings have been changed.
   */
  default void projectSettingsChanged(@NotNull final PomskyProjectSettingsService settings) {}
}
