package com.github.lppedd.idea.pomsky;

import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Edoardo Luppi
 */
public class PomskyIcons {
  public static final Icon LOGO = getIcon("/icons/logo.png");

  @NotNull
  @SuppressWarnings("SameParameterValue")
  private static Icon getIcon(@NotNull final String iconPath) {
    return IconLoader.getIcon(iconPath, PomskyIcons.class);
  }
}
