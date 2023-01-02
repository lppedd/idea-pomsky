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
  private final PomskySettingsService service = PomskySettingsService.getInstance();
  private final PomskySettingsComponent component = new PomskySettingsComponent(disposable);

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
    component.setCliExecutablePath(service.getCliExecutablePath());
    return component.getPanel();
  }

  @Override
  public boolean isModified() {
    return component.isDataValid() &&
           !Objects.equals(component.getCliExecutablePath(), service.getCliExecutablePath());
  }

  @Override
  public void apply() {
    service.setCliExecutablePath(component.getCliExecutablePath());
  }

  @Override
  public void reset() {
    component.setCliExecutablePath(service.getCliExecutablePath());
  }

  @Override
  public void disposeUIResources() {
    Disposer.dispose(disposable);
  }
}
