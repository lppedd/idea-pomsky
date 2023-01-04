package com.github.lppedd.idea.pomsky.settings;

import com.github.lppedd.idea.pomsky.process.PomskyCliProcess;
import com.github.lppedd.idea.pomsky.process.PomskyProcessException;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.ui.ComponentValidator;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.UI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

/**
 * A component that allows selecting the Pomsky CLI executable.
 *
 * @author Edoardo Luppi
 */
class PomskyCliExecutablePanel extends JBPanel<PomskyCliExecutablePanel> {
  private final Disposable disposable;
  private Consumer<String> listener = version -> {};
  private TextFieldWithBrowseButton executablePathField;

  PomskyCliExecutablePanel(@NotNull final Disposable disposable) {
    super(new BorderLayout());
    this.disposable = disposable;
    add(getExecutablePathPanel(), BorderLayout.NORTH);
  }

  @Nullable
  String getCliExecutablePath() {
    final var text = executablePathField.getText().trim();
    return text.isEmpty() ? null : text;
  }

  void setCliExecutablePath(@Nullable final String path) {
    executablePathField.setText(path);
  }

  boolean isDataValid() {
    final var validator = ComponentValidator.getInstance(executablePathField).orElseThrow();
    return validator.getValidationInfo() == null;
  }

  void setListener(@NotNull final Consumer<String> listener) {
    this.listener = listener;
  }

  @NotNull
  private JPanel getExecutablePathPanel() {
    final var chooserDescriptor = new PomskyCliExecutableFileChooserDescriptor();
    executablePathField = new TextFieldWithBrowseButton();
    executablePathField.addBrowseFolderListener(new TextBrowseFolderListener(chooserDescriptor));

    final var executablePathPanel = UI.PanelFactory.panel(executablePathField)
        .withComment("The Pomsky CLI executable path. E.g., 'C:/tools/pomsky_windows_v0.8.exe'", false)
        .createPanel();

    installValidationOnFilePicker();
    return executablePathPanel;
  }

  private void installValidationOnFilePicker() {
    new ComponentValidator(disposable)
        .withValidator(this::validateCliExecutable)
        .withOutlineProvider(jComponent -> executablePathField.getTextField())
        .installOn(executablePathField);

    executablePathField.getTextField().getDocument().addDocumentListener(new DocumentAdapter() {
      @Override
      protected void textChanged(@NotNull final DocumentEvent e) {
        ComponentValidator
            .getInstance(executablePathField)
            .ifPresent(ComponentValidator::revalidate);
      }
    });
  }

  @Nullable
  private ValidationInfo validateCliExecutable() {
    // Remove any Pomsky version indicator
    listener.accept("N/A");

    final var text = executablePathField.getText().trim();

    if (text.isEmpty()) {
      return null;
    }

    final var executablePath = Path.of(text);

    if (!executablePath.isAbsolute()) {
      return new ValidationInfo("Invalid path", executablePathField);
    }

    if (!Files.isRegularFile(executablePath)) {
      return new ValidationInfo("Invalid Pomsky CLI executable", executablePathField);
    }

    try {
      final var process = new PomskyCliProcess(executablePath);
      final var version = ProgressManager.getInstance().run(new PomskyVersionTask(process));
      listener.accept(version);
    } catch (final PomskyProcessException e) {
      return new ValidationInfo(e.getMessage(), executablePathField);
    } catch (final ProcessCanceledException e) {
      // Nothing do here, the user canceled the version retrieval process
    }

    return null;
  }

  private static class PomskyVersionTask extends Task.WithResult<String, PomskyProcessException> {
    final PomskyCliProcess process;

    PomskyVersionTask(@NotNull final PomskyCliProcess process) {
      super(null, "Pomsky Settings", true);
      this.process = process;
    }

    @Override
    protected String compute(@NotNull final ProgressIndicator indicator) throws PomskyProcessException {
      indicator.setIndeterminate(true);
      indicator.setText("Retrieving Pomsky CLI executable version...");
      return process.getVersion(indicator);
    }
  }
}
