package com.github.lppedd.idea.pomsky.process;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.CapturingProcessHandler;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

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
  public String getVersion() throws PomskyProcessException {
    final var commandLine = new GeneralCommandLine()
        .withExePath(executablePath.toString())
        .withParameters("--version")
        .withCharset(StandardCharsets.UTF_8);

    try {
      final var processHandler = new CapturingProcessHandler(commandLine);
      final var processOutput = processHandler.runProcess(2000, true);
      final var stdout = processOutput.getStdout().trim();
      final var index = stdout.indexOf("pomsky");

      if (index < 0) {
        throw new PomskyProcessException("Not a Pomsky CLI executable");
      }

      return stdout.substring(index + 6).trim();
    } catch (final ExecutionException e) {
      throw new PomskyProcessException("Error while retrieving the Pomsky version", e);
    }
  }

  @NotNull
  @Override
  public PomskyCompileResult compile(@NotNull final String code) throws PomskyProcessException {
    return compileInternal(List.of("--no-new-line", code.trim()));
  }

  @NotNull
  @Override
  public PomskyCompileResult compile(
      @NotNull final String code,
      @NotNull final PomskyRegexpFlavor flavor) throws PomskyProcessException {
    return compileInternal(List.of("--no-new-line", "--flavor", flavor.getValue(), code.trim()));
  }

  @NotNull
  private PomskyCompileResult compileInternal(
      @NotNull final List<String> params) throws PomskyProcessException {
    final var commandLine = new GeneralCommandLine()
        .withExePath(executablePath.toString())
        .withParameters(params)
        .withCharset(StandardCharsets.UTF_8);

    try {
      final var processHandler = new CapturingProcessHandler(commandLine);
      final var processOutput = processHandler.runProcess(4000, true);

      if (processOutput.getExitCode() != 0) {
        return new PomskyCompileResult(null, processOutput.getStderr().trim());
      }

      return new PomskyCompileResult(processOutput.getStdout().trim(), null);
    } catch (final ExecutionException e) {
      throw new PomskyProcessException("Error while compiling the Pomsky code", e);
    }
  }
}
