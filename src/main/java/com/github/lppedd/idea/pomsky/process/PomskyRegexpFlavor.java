package com.github.lppedd.idea.pomsky.process;

import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public enum PomskyRegexpFlavor {
  PCRE("pcre"),
  PYTHON("python"),
  JAVA("java"),
  JAVASCRIPT("javascript"),
  DOTNET("dotnet"),
  RUBY("ruby"),
  RUST("rust");

  private final String value;

  PomskyRegexpFlavor(@NotNull final String value) {
    this.value = value;
  }

  @NotNull
  public String getValue() {
    return value;
  }
}
