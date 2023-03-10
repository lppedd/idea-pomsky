package com.github.lppedd.idea.pomsky.process;

import com.intellij.util.PlatformUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public enum PomskyRegexpFlavor {
  JAVA("java", "Java"),
  JAVASCRIPT("javascript", "JavaScript"),
  RUST("rust", "Rust"),
  PYTHON("python", "Python"),
  DOTNET("dotnet", ".NET"),
  RUBY("ruby", "Ruby"),
  PCRE("pcre", "PCRE");

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

  @NotNull
  @Override
  public String toString() {
    return presentableName;
  }

  /**
   * Returns the regular expression flavor that should be used
   * on a specific IDE type (IntelliJ IDEA, WebStorm, PyCharm, etc.).
   * <p>
   * Useful to configure default values.
   */
  @NotNull
  @SuppressWarnings("UnstableApiUsage")
  public static PomskyRegexpFlavor getDefaultProductFlavor() {
    if (PlatformUtils.isPyCharm()) {
      return PomskyRegexpFlavor.PYTHON;
    }

    if (PlatformUtils.isRubyMine()) {
      return PomskyRegexpFlavor.RUBY;
    }

    if (PlatformUtils.isRider()) {
      return PomskyRegexpFlavor.DOTNET;
    }

    if (PlatformUtils.isWebStorm()) {
      return PomskyRegexpFlavor.JAVASCRIPT;
    }

    if (PlatformUtils.isIntelliJ()) {
      return PomskyRegexpFlavor.JAVA;
    }

    return PomskyRegexpFlavor.PCRE;
  }
}
