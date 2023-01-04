package com.github.lppedd.idea.pomsky.process;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.CapturingProcessHandler;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Edoardo Luppi
 */
public class PomskyCliProcess implements PomskyProcess {
  private final Path executablePath;

  public PomskyCliProcess(@NotNull final Path executablePath) {
    this.executablePath = executablePath.normalize();
  }

  @NotNull
  @Override
  public String getVersion(@NotNull final ProgressIndicator indicator) throws PomskyProcessException {
    final var commandLine = new GeneralCommandLine()
        .withExePath(executablePath.toString())
        .withParameters("--version")
        .withCharset(StandardCharsets.UTF_8);

    try {
      final var processHandler = new CapturingProcessHandler(commandLine);
      final var processOutput = processHandler.runProcessWithProgressIndicator(indicator, 15000, true);

      if (processOutput.isCancelled()) {
        throw new ProcessCanceledException();
      }

      if (processOutput.isTimeout()) {
        throw new PomskyProcessException("Retrieving Pomsky version timed out");
      }

      final var stdout = processOutput.getStdout().trim();
      final var index = stdout.indexOf("pomsky");

      if (index < 0) {
        throw new PomskyProcessException("Not a Pomsky CLI executable");
      }

      return stdout.substring(index + 6).trim();
    } catch (final ExecutionException e) {
      throw new PomskyProcessException("Error while retrieving Pomsky version", e);
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
    final var commandLine = new GeneralCommandLine()
        .withExePath(executablePath.toString())
        .withParameters(params)
        .withCharset(StandardCharsets.UTF_8);

    try {
      final var processHandler = new CapturingProcessHandler(commandLine);
      final var startTime = System.nanoTime();
      final var processOutput = processHandler.runProcessWithProgressIndicator(indicator, 30000, true);
      final var elapsedTimeMs = TimeUnit.of(ChronoUnit.NANOS).toMillis(System.nanoTime() - startTime) - 1;

      if (processOutput.isCancelled()) {
        throw new ProcessCanceledException();
      }

      if (processOutput.isTimeout()) {
        throw new PomskyProcessException("Compiling Pomsky code timed out");
      }

      if (processOutput.getExitCode() != 0) {
        return new PomskyCompileResult(elapsedTimeMs, null, processOutput.getStderr().trim());
      }

      return new PomskyCompileResult(elapsedTimeMs, processOutput.getStdout().trim(), null);
    } catch (final ExecutionException e) {
      throw new PomskyProcessException("Error while compiling Pomsky code", e);
    }
  }
}
