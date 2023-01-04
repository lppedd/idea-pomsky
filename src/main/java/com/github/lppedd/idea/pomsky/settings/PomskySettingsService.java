package com.github.lppedd.idea.pomsky.settings;

import com.github.lppedd.idea.pomsky.PomskyTopics;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.Attribute;
import com.intellij.util.xmlb.annotations.Transient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Edoardo Luppi
 */
@State(
    name = "com.github.lppedd.idea.pomsky.settings.PomskySettingsService",
    storages = @Storage(StoragePathMacros.NON_ROAMABLE_FILE)
)
public class PomskySettingsService implements PersistentStateComponent<PomskySettingsService> {
  @NotNull
  public static PomskySettingsService getInstance() {
    return ApplicationManager.getApplication().getService(PomskySettingsService.class);
  }

  /**
   * The absolute path to the Pomsky CLI executable.
   */
  @Attribute("cliExecutablePath")
  private String cliExecutablePath;

  /**
   * Temporary (per IntelliJ session) flag to show or not an editor
   * notification banner when the Pomsky CLI executable is not defined.
   * <p>
   * This is not stored.
   */
  private boolean showMissingCliExecutableBanner = true;

  @Nullable
  public String getCliExecutablePath() {
    return cliExecutablePath;
  }

  @Transient
  public boolean isShowMissingCliExecutableBanner() {
    return showMissingCliExecutableBanner;
  }

  public void setCliExecutablePath(@Nullable final String path) {
    cliExecutablePath = path;
    notifySettingsChanged();
  }

  public void setShowMissingCliExecutableBanner(final boolean doShow) {
    showMissingCliExecutableBanner = doShow;
    notifySettingsChanged();
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

  private void notifySettingsChanged() {
    final var messageBus = ApplicationManager.getApplication().getMessageBus();
    messageBus.syncPublisher(PomskyTopics.TOPIC_SETTINGS).settingsChanged(this);
  }
}
