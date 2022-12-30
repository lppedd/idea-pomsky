package com.github.lppedd.idea.pomsky.lang;

import com.github.lppedd.idea.pomsky.PomskyIcons;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Edoardo Luppi
 */
public class PomskyFileType extends LanguageFileType {
  public static final PomskyFileType INSTANCE = new PomskyFileType();

  PomskyFileType() {
    super(PomskyLanguage.INSTANCE);
  }

  @NonNls
  @NotNull
  @Override
  public String getName() {
    return "Pomsky";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "Pomsky file";
  }

  @Override
  @NotNull
  public String getDefaultExtension() {
    return "pomsky";
  }

  @Override
  public Icon getIcon() {
    return PomskyIcons.LOGO;
  }
}
