package com.github.lppedd.idea.pomsky.editor;

import com.github.lppedd.idea.pomsky.PomskyTopics;
import com.github.lppedd.idea.pomsky.process.PomskyCompileListener;
import com.github.lppedd.idea.pomsky.process.PomskyCompileResult;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Adds an "elapsed time" indicator at the bottom of Pomsky editors.
 * <p>
 * The time is notified by the compilation service.
 *
 * @author Edoardo Luppi
 */
class PomskyFileEditorManagerListener implements FileEditorManagerListener {
  @Override
  public void fileOpened(
      @NotNull final FileEditorManager manager,
      @NotNull final VirtualFile file) {
    final var editor = manager.getSelectedEditor(file);

    if (!(editor instanceof PomskyEditorWithPreview)) {
      return;
    }

    final var panel = new JBPanel<>(new BorderLayout());
    panel.setOpaque(false);
    panel.setBorder(JBUI.Borders.empty(2, 4, 4, 4));

    final var elapsedTimeLabel = new JBLabel("Elapsed time: N/A");
    panel.add(elapsedTimeLabel, BorderLayout.EAST);

    // Connect to the compilation topic to be notified of elapsed times
    final var project = manager.getProject();
    final var messageBus = project.getMessageBus();
    messageBus.connect(editor).subscribe(PomskyTopics.TOPIC_COMPILE, new PomskyCompileListener() {
      @Override
      public void compileFinished(
          @NotNull final VirtualFile compiledFile,
          @NotNull final PomskyCompileResult result) {
        if (compiledFile.equals(file)) {
          elapsedTimeLabel.setText("Elapsed time: %d ms".formatted(result.getElapsedTimeMs()));
        }
      }
    });

    manager.addBottomComponent(editor, panel);
    Disposer.register(editor, () -> manager.removeBottomComponent(editor, panel));
  }
}
