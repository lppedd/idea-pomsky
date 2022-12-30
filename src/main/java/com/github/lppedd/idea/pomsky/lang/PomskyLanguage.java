package com.github.lppedd.idea.pomsky.lang;

import com.intellij.lang.Language;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyLanguage extends Language {
  public static final PomskyLanguage INSTANCE = new PomskyLanguage();

  private PomskyLanguage() {
    super("pomsky");
  }

  @NotNull
  @Override
  public String getDisplayName() {
    return "Pomsky";
  }
}
