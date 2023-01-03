package com.github.lppedd.idea.pomsky.process;

import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public enum PomskyRegexpFlavor {
  PCRE("pcre", "PCRE"),
  PYTHON("python", "Python"),
  JAVA("java", "Java"),
  JAVASCRIPT("javascript", "JavaScript"),
  DOTNET("dotnet", ".NET"),
  RUBY("ruby", "Ruby"),
  RUST("rust", "Rust");

  private final String value;
  private final String presentableName;

  PomskyRegexpFlavor(
      @NotNull final String value,
      @NotNull final String presentableName) {
    this.value = value;
    this.presentableName = presentableName;
  }

  @NotNull
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return presentableName;
  }
}
