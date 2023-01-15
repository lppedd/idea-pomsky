package com.github.lppedd.idea.pomsky;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * Plugin-wide reusable RegExp patterns and utility methods.
 *
 * @author Edoardo Luppi
 */
public class PomskyPatterns {
  public static final Pattern NUMBER = Pattern.compile("[0-9]+");

  public static boolean matchesNumber(@NotNull final String value) {
    return matches(NUMBER, value);
  }

  @SuppressWarnings("SameParameterValue")
  private static boolean matches(
      @NotNull final Pattern pattern,
      @NotNull final String value) {
    return pattern.matcher(value).matches();
  }
}
