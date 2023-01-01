package com.github.lppedd.idea.pomsky.lang;

import com.intellij.codeInsight.generation.IndentedCommenter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Edoardo Luppi
 */
public class PomskyCommenter implements IndentedCommenter {
  @NotNull
  @Override
  public String getLineCommentPrefix() {
    return "# ";
  }

  @NotNull
  @Override
  public Boolean forceIndentedLineComment() {
    return Boolean.TRUE;
  }

  @Nullable
  @Override
  public String getBlockCommentPrefix() {
    return null;
  }

  @Nullable
  @Override
  public String getBlockCommentSuffix() {
    return null;
  }

  @Nullable
  @Override
  public String getCommentedBlockCommentPrefix() {
    return null;
  }

  @Nullable
  @Override
  public String getCommentedBlockCommentSuffix() {
    return null;
  }
}
