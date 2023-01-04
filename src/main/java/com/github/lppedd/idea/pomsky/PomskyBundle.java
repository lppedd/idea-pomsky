package com.github.lppedd.idea.pomsky;

import com.intellij.DynamicBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

/**
 * @author Edoardo Luppi
 */
public class PomskyBundle extends DynamicBundle {
  private static final String BUNDLE = "messages.PomskyBundle";
  private static final PomskyBundle INSTANCE = new PomskyBundle();

  private PomskyBundle() {
    super(PomskyBundle.class, BUNDLE);
  }

  @Nls
  @NotNull
  public static String message(
      @NotNull @PropertyKey(resourceBundle = BUNDLE) final String key,
      @NotNull final Object @NotNull ... params) {
    return INSTANCE.getMessage(key, params);
  }
}
