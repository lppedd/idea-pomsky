package com.github.lppedd.idea.pomsky.settings;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
class PomskyCliExecutableFileChooserDescriptor extends FileChooserDescriptor {
  PomskyCliExecutableFileChooserDescriptor() {
    super(true, false, false, false, false, false);
  }

  @NotNull
  @Override
  public String getTitle() {
    return "Select Pomsky CLI Executable";
  }
}
