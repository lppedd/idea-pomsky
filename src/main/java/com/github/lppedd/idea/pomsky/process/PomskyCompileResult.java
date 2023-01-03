package com.github.lppedd.idea.pomsky.process;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * @author Edoardo Luppi
 */
public class PomskyCompileResult {
  private final String regexp;
  private final String errorMessage;

  public PomskyCompileResult(
      @Nullable final String regexp,
      @Nullable final String errorMessage) {
    this.regexp = regexp;
    this.errorMessage = errorMessage;
  }

  @NotNull
  public String getRegexp() {
    if (hasError()) {
      throw new IllegalStateException("Check if errors are present before invoking this method");
    }

    return Objects.requireNonNull(regexp);
  }

  @NotNull
  public String getErrorMessage() {
    if (!hasError()) {
      throw new IllegalStateException("Check if errors are present before invoking this method");
    }

    return Objects.requireNonNull(errorMessage);
  }

  public boolean hasError() {
    return errorMessage != null;
  }
}
