package com.github.lppedd.idea.pomsky.settings;

import com.github.lppedd.idea.pomsky.process.PomskyRegexpFlavor;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.Attribute;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
@State(
    name = "com.github.lppedd.idea.pomsky.settings.PomskyProjectSettingsService",
    storages = @Storage(StoragePathMacros.WORKSPACE_FILE)
)
public class PomskyProjectSettingsService implements PersistentStateComponent<PomskyProjectSettingsService> {
  @Attribute("regexpFlavor")
  private PomskyRegexpFlavor regexpFlavor = PomskyRegexpFlavor.PCRE;

  @NotNull
  public PomskyRegexpFlavor getRegexpFlavor() {
    return regexpFlavor;
  }

  public void setRegexpFlavor(@NotNull final PomskyRegexpFlavor regexpFlavor) {
    this.regexpFlavor = regexpFlavor;
  }

  @NotNull
  @Override
  public PomskyProjectSettingsService getState() {
    return this;
  }

  @Override
  public void loadState(@NotNull final PomskyProjectSettingsService state) {
    XmlSerializerUtil.copyBean(state, this);
  }

  @NotNull
  public static PomskyProjectSettingsService getInstance(@NotNull final Project project) {
    return project.getService(PomskyProjectSettingsService.class);
  }
}