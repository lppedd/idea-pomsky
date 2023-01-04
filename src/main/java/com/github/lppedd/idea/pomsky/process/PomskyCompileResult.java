package com.github.lppedd.idea.pomsky.process;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Represents a Pomsky compilation process result.
 *
 * @author Edoardo Luppi
 */
public class PomskyCompileResult {
  private final long elapsedTimeMs;
  private final String regexp;
  private final String errorMessage;

  public PomskyCompileResult(
      final long elapsedTimeMs,
      @Nullable final String regexp,
      @Nullable final String errorMessage) {
    this.regexp = regexp;
    this.errorMessage = errorMessage;
    this.elapsedTimeMs = elapsedTimeMs;
  }

  /**
   * Returns whether an error is present.
   */
  public boolean hasError() {
    return errorMessage != null;
  }

  /**
   * Returns the time, in milliseconds, required for the compilation process.
   * <p>
   * It is always a positive value.
   */
  public long getElapsedTimeMs() {
    return elapsedTimeMs;
  }

  /**
   * If no error is present, returns the compiled regular expression.
   *
   * @throws IllegalStateException if an error is present
   */
  @NotNull
  public String getCompiledRegexp() {
    if (hasError()) {
      throw new IllegalStateException("Check if errors are present before invoking this method");
    }

    return Objects.requireNonNull(regexp);
  }

  /**
   * If an error is present, returns the error message.
   *
   * @throws IllegalStateException if no error is present
   */
  @NotNull
  public String getErrorMessage() {
    if (!hasError()) {
      throw new IllegalStateException("Check if errors are present before invoking this method");
    }

    return Objects.requireNonNull(errorMessage);
  }
}
