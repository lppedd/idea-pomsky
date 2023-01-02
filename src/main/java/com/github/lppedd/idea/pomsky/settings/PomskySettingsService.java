package com.github.lppedd.idea.pomsky.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.Attribute;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Edoardo Luppi
 */
@State(
    name = "com.github.lppedd.idea.pomsky.settings.PomskySettingsService",
    storages = @Storage("pomsky.xml")
)
public class PomskySettingsService implements PersistentStateComponent<PomskySettingsService> {
  @Attribute("cliExecutablePath")
  private String cliExecutablePath;

  @Nullable
  public String getCliExecutablePath() {
    return cliExecutablePath;
  }

  public void setCliExecutablePath(@Nullable final String path) {
    cliExecutablePath = path;
  }

  @NotNull
  @Override
  public PomskySettingsService getState() {
    return this;
  }

  @Override
  public void loadState(@NotNull final PomskySettingsService state) {
    XmlSerializerUtil.copyBean(state, this);
  }

  @NotNull
  public static PomskySettingsService getInstance() {
    return ApplicationManager.getApplication().getService(PomskySettingsService.class);
  }
}
