package com.github.lppedd.idea.pomsky.process;

import org.jetbrains.annotations.Nullable;

/**
 * @author Edoardo Luppi
 */
public class PomskyCompileResult {
  private final String regexp;
  private final String errorMessage;

  PomskyCompileResult(
      @Nullable final String regexp,
      @Nullable final String errorMessage) {
    this.regexp = regexp;
    this.errorMessage = errorMessage;
  }

  @Nullable
  public String getRegexp() {
    return regexp;
  }

  @Nullable
  public String getErrorMessage() {
    return errorMessage;
  }

  public boolean hasError() {
    return errorMessage != null;
  }
}
