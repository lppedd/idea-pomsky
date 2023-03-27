package com.github.lppedd.idea.pomsky.process;

import com.github.lppedd.idea.pomsky.util.IDEUtils;
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
  public static PomskyRegexpFlavor getDefaultProductFlavor() {
    if (IDEUtils.isPyCharm()) {
      return PomskyRegexpFlavor.PYTHON;
    }

    if (IDEUtils.isRubyMine()) {
      return PomskyRegexpFlavor.RUBY;
    }

    if (IDEUtils.isRider()) {
      return PomskyRegexpFlavor.DOTNET;
    }

    if (IDEUtils.isWebStorm()) {
      return PomskyRegexpFlavor.JAVASCRIPT;
    }

    if (IDEUtils.isIntelliJ()) {
      return PomskyRegexpFlavor.JAVA;
    }

    return PomskyRegexpFlavor.PCRE;
  }
}
