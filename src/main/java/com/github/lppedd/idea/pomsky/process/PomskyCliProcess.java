package com.github.lppedd.idea.pomsky.process;

import com.github.lppedd.idea.pomsky.settings.PomskySettingsConfigurable;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.CapturingProcessHandler;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Edoardo Luppi
 */
public class PomskyCliProcess implements PomskyProcess {
  private static final int TIMEOUT_VERSION = 15000;
  private static final int TIMEOUT_COMPILE = 30000;
  private static final int OUTPUT_THREESOLD = 10000000;

  private final Path executablePath;

  public PomskyCliProcess(@NotNull final Path executablePath) {
    this.executablePath = executablePath.normalize();
  }

  @NotNull
  @Override
  public String getVersion(@NotNull final ProgressIndicator indicator) throws PomskyProcessException {
    checkExecutable();

    final var commandLine = new GeneralCommandLine()
        .withExePath(executablePath.toString())
        .withParameters("--version")
        .withCharset(StandardCharsets.UTF_8);

    try {
      final var processHandler = new CapturingProcessHandler(commandLine);
      final var processOutput = processHandler.runProcessWithProgressIndicator(indicator, TIMEOUT_VERSION, true);

      if (processOutput.isCancelled()) {
        throw new ProcessCanceledException();
      }

      if (processOutput.isTimeout()) {
        throw new PomskyProcessException("Version retrieval timed out");
      }

      final var stdout = processOutput.getStdout().trim();
      final var index = stdout.indexOf("pomsky");

      if (index < 0) {
        throw new PomskyProcessException("Not a Pomsky CLI executable");
      }

      return stdout.substring(index + 6).trim();
    } catch (final ExecutionException e) {
      throw new PomskyProcessException("Error during version retrieval", e);
    }
  }

  @NotNull
  @Override
  public PomskyCompileResult compile(
      @NotNull final String code,
      @NotNull final ProgressIndicator indicator) throws PomskyProcessException {
    return compileInternal(List.of("--no-new-line", code.trim()), indicator);
  }

  @NotNull
  @Override
  public PomskyCompileResult compile(
      @NotNull final String code,
      @NotNull final PomskyRegexpFlavor regexpFlavor,
      @NotNull final ProgressIndicator indicator) throws PomskyProcessException {
    return compileInternal(List.of("--no-new-line", "--flavor", regexpFlavor.getValue(), code.trim()), indicator);
  }

  @NotNull
  private PomskyCompileResult compileInternal(
      @NotNull final List<String> params,
      @NotNull final ProgressIndicator indicator) throws PomskyProcessException {
    checkExecutable();

    final var commandLine = new GeneralCommandLine()
        .withExePath(executablePath.toString())
        .withParameters(params)
        .withCharset(StandardCharsets.UTF_8);

    try {
      final var processHandler = new CapturingProcessHandler(commandLine);
      final var startTime = System.nanoTime();
      final var processOutput = processHandler.runProcessWithProgressIndicator(indicator, TIMEOUT_COMPILE, true);
      final var elapsedTimeMs = (System.nanoTime() - startTime) / 1000000 - 1;

      if (processOutput.isCancelled()) {
        throw new ProcessCanceledException();
      }

      if (processOutput.isTimeout()) {
        final var message = "Compilation timed out after " + TIMEOUT_COMPILE / 1000 + " seconds";
        throw new PomskyProcessException(message);
      }

      if (processOutput.getExitCode() != 0) {
        return new PomskyCompileResult(elapsedTimeMs, null, processOutput.getStderr().trim());
      }

      final var output = processOutput.getStdout().trim();
      return output.length() > OUTPUT_THREESOLD
          ? new PomskyCompileResult(elapsedTimeMs, null, "The compiled RegExp is too big to be displayed")
          : new PomskyCompileResult(elapsedTimeMs, output, null);
    } catch (final ExecutionException e) {
      throw new PomskyProcessException("Error during compilation", e);
    }
  }

  private void checkExecutable() throws PomskyProcessException {
    if (Files.notExists(executablePath)) {
      final var exception = new PomskyProcessException("The CLI executable does not exist");
      exception.addAction(new SettingsNotificationAction());
      throw exception;
    }
  }

  private static class SettingsNotificationAction extends NotificationAction {
    SettingsNotificationAction() {
      super("Settings");
    }

    @Override
    public void actionPerformed(
        @NotNull final AnActionEvent e,
        @NotNull final Notification notification) {
      final var project = e.getProject();
      ShowSettingsUtil.getInstance().showSettingsDialog(project, PomskySettingsConfigurable.class);
    }
  }
}
