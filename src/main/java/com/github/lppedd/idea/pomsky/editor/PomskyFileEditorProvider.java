package com.github.lppedd.idea.pomsky.editor;

import com.github.lppedd.idea.pomsky.lang.PomskyFileType;
import com.intellij.openapi.fileEditor.AsyncFileEditorProvider;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class PomskyFileEditorProvider implements AsyncFileEditorProvider, DumbAware {
  @NotNull
  @Override
  public Builder createEditorAsync(@NotNull final Project project, @NotNull final VirtualFile file) {
    return new PomskyEditorBuilder(project, file);
  }

  @Override
  public boolean accept(@NotNull final Project project, @NotNull final VirtualFile file) {
    return FileTypeRegistry.getInstance().isFileOfType(file, PomskyFileType.INSTANCE);
  }

  @NotNull
  @Override
  public FileEditor createEditor(@NotNull final Project project, @NotNull final VirtualFile file) {
    return createEditorAsync(project, file).build();
  }

  @NonNls
  @NotNull
  @Override
  public String getEditorTypeId() {
    return "idea-pomsky-file-editor";
  }

  @NotNull
  @Override
  public FileEditorPolicy getPolicy() {
    return FileEditorPolicy.HIDE_DEFAULT_EDITOR;
  }
}
