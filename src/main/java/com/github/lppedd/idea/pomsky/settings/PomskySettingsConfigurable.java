package com.github.lppedd.idea.pomsky.settings;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.util.Disposer;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Objects;

/**
 * @author Edoardo Luppi
 */
public class PomskySettingsConfigurable implements SearchableConfigurable {
  private final Disposable disposable = Disposer.newDisposable("PomskySettingsConfigurable");
  private final PomskySettingsService settings = PomskySettingsService.getInstance();
  private PomskySettingsComponent component;

  @NonNls
  @NotNull
  @Override
  public String getId() {
    return "com.github.lppedd.idea.pomsky.settings";
  }

  @NotNull
  @Override
  public String getDisplayName() {
    return "Pomsky";
  }

  @NotNull
  @Override
  public JComponent createComponent() {
    component = new PomskySettingsComponent(disposable);
    component.setCliExecutablePath(settings.getCliExecutablePath());
    component.setLivePreview(settings.isLivePreview());
    return component.getPanel();
  }

  @Override
  public boolean isModified() {
    return component.isDataValid() && (
        !Objects.equals(component.getCliExecutablePath(), settings.getCliExecutablePath()) ||
        component.isLivePreview() != settings.isLivePreview()
    );
  }

  @Override
  public void apply() {
    settings.setCliExecutablePath(component.getCliExecutablePath());
    settings.setLivePreview(component.isLivePreview());
  }

  @Override
  public void reset() {
    component.setCliExecutablePath(settings.getCliExecutablePath());
    component.setLivePreview(settings.isLivePreview());
  }

  @Override
  public void disposeUIResources() {
    Disposer.dispose(disposable);
  }
}
