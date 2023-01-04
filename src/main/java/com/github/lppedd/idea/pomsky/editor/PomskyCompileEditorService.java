package com.github.lppedd.idea.pomsky.editor;

import com.github.lppedd.idea.pomsky.PomskyTopics;
import com.github.lppedd.idea.pomsky.process.PomskyProcessException;
import com.github.lppedd.idea.pomsky.process.PomskyService;
import com.github.lppedd.idea.pomsky.settings.PomskyProjectSettingsService;
import com.github.lppedd.idea.pomsky.settings.PomskySettingsService;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Set;
import java.util.WeakHashMap;

import static java.util.Collections.newSetFromMap;
import static java.util.Collections.synchronizedMap;

/**
 * @author Edoardo Luppi
 */
class PomskyCompileEditorService {
  private static final Logger logger = Logger.getInstance(PomskyCompileEditorService.class);

  private final Project project;
  private final MessageBus messageBus;
  private final Set<VirtualFile> compilingFileSet = newSetFromMap(synchronizedMap(new WeakHashMap<>(32)));

  PomskyCompileEditorService(@NotNull final Project project) {
    this.project = project;
    messageBus = project.getMessageBus();
  }

  @NotNull
  public static PomskyCompileEditorService getInstance(@NotNull final Project project) {
    return project.getService(PomskyCompileEditorService.class);
  }

  /**
   * Checks if a file can be compiled at the moment of invocation.
   */
  public synchronized boolean canCompile(@NotNull final VirtualFile file) {
    return !compilingFileSet.contains(file);
  }

  /**
   * Submits a file for compilation, if possible.
   * <p>
   * This method returns immediately, while the compilation process runs in background.
   */
  public synchronized void compileAndUpdateEditorAsync(@NotNull final VirtualFile file) {
    if (!canCompile(file)) {
      return;
    }

    final var settings = PomskySettingsService.getInstance();
    final var executablePath = settings.getCliExecutablePath();

    if (executablePath == null) {
      return;
    }

    compilingFileSet.add(file);
    messageBus.syncPublisher(PomskyTopics.TOPIC_COMPILE).compileRequested(file);
    ProgressManager.getInstance().run(new PomskyCompileTask(file));
  }

  private class PomskyCompileTask extends Task.Backgroundable {
    final VirtualFile file;

    PomskyCompileTask(@NotNull final VirtualFile file) {
      super(project, "Compiling Pomsky file", true);
      this.file = file;
    }

    @Override
    public void run(@NotNull final ProgressIndicator indicator) {
      indicator.setIndeterminate(true);
      indicator.setText("Compiling " + getPresentableFileName() + "...");

      final var projectSettings = PomskyProjectSettingsService.getInstance(project);

      try {
        final var code = ReadAction.compute(this::getVirtualFileText);
        final var flavor = projectSettings.getRegexpFlavor();
        final var result = PomskyService.getInstance().compile(code, flavor, indicator);

        ApplicationManager.getApplication().invokeLater(() -> {
          final var listener = messageBus.syncPublisher(PomskyTopics.TOPIC_COMPILE);
          listener.compileFinished(file, result);
        });
      } catch (final IOException | PomskyProcessException e) {
        if (!(e instanceof PomskyProcessException) || e.getCause() != null) {
          logger.error("Error while compiling '%s'".formatted(file.getName()), e);
        }

        messageBus.syncPublisher(PomskyTopics.TOPIC_COMPILE).compileFailed(file, e);
      }
    }

    @Override
    public void onCancel() {
      messageBus.syncPublisher(PomskyTopics.TOPIC_COMPILE).compileCanceled(file);
    }

    @Override
    public void onThrowable(@NotNull final Throwable e) {
      logger.error("Error while compiling '%s'".formatted(file.getName()), e);
      messageBus.syncPublisher(PomskyTopics.TOPIC_COMPILE).compileFailed(file, e);
    }

    @Override
    public void onFinished() {
      compilingFileSet.remove(file);
    }

    @NotNull
    private String getPresentableFileName() {
      // In case we are compiling an injected fragment, we do not show
      // the file extension, as it is redundant
      final var name = file.getName();
      return name.toLowerCase().contains("pomsky fragment")
          ? name.substring(0, name.lastIndexOf(".pomsky"))
          : name;
    }

    @NotNull
    private String getVirtualFileText() throws IOException {
      final var document = FileDocumentManager.getInstance().getDocument(file);
      return document != null
          ? document.getText()
          // The file might be too large, or something might have gone wrong.
          // Let's load it from the VirtualFile itself
          : VfsUtil.loadText(file);
    }
  }
}
