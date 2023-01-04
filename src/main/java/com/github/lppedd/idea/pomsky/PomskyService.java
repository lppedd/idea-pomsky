package com.github.lppedd.idea.pomsky;

import com.github.lppedd.idea.pomsky.process.*;
import com.github.lppedd.idea.pomsky.settings.PomskySettingsService;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

/**
 * @author Edoardo Luppi
 */
public class PomskyService implements PomskyProcess {
  @NotNull
  public static PomskyService getInstance() {
    return ApplicationManager.getApplication().getService(PomskyService.class);
  }

  @NotNull
  @Override
  public String getVersion(@NotNull final ProgressIndicator indicator) throws PomskyProcessException {
    final var process = new PomskyCliProcess(getExecutablePath());
    return process.getVersion(indicator);
  }

  @NotNull
  @Override
  public PomskyCompileResult compile(
      @NotNull final String code,
      @NotNull final ProgressIndicator indicator) throws PomskyProcessException {
    final var process = new PomskyCliProcess(getExecutablePath());
    return process.compile(code, indicator);
  }

  @NotNull
  @Override
  public PomskyCompileResult compile(
      @NotNull final String code,
      @NotNull final PomskyRegexpFlavor regexpFlavor,
      @NotNull final ProgressIndicator indicator) throws PomskyProcessException {
    final var process = new PomskyCliProcess(getExecutablePath());
    return process.compile(code, regexpFlavor, indicator);
  }

  @NotNull
  private Path getExecutablePath() throws PomskyProcessException {
    final var settings = PomskySettingsService.getInstance();
    final var cliExecutablePath = settings.getCliExecutablePath();

    if (cliExecutablePath == null) {
      throw new PomskyProcessException("The Pomsky CLI executable has not been set");
    }

    return Path.of(cliExecutablePath);
  }
}
