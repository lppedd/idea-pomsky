package com.github.lppedd.idea.pomsky.lang;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

/**
 * @author Edoardo Luppi
 */
@Service(Service.Level.APP)
public final class PomskyNameHelper {
  private static final Pattern PATTERN_GROUP_NAME = Pattern.compile("[a-zA-Z][0-9a-zA-Z]*");
  private static final Pattern PATTERN_IDENTIFIER = Pattern.compile(
      "[\\p{Alpha}_][\\p{Alpha}\\p{N}_]*",
      Pattern.UNICODE_CASE | Pattern.UNICODE_CHARACTER_CLASS
  );

  @NotNull
  public static PomskyNameHelper getInstance() {
    return ApplicationManager.getApplication().getService(PomskyNameHelper.class);
  }

  /**
   * Returns an error message describing the problem with the identifier,
   * or {@code null} if the identifier is valid.
   */
  @Nullable
  public String validateIdentifier(@NotNull final String identifier) {
    if (PomskyBuiltins.Keywords.is(identifier)) {
      return "An identifier cannot be a keyword";
    }

    if (!PATTERN_IDENTIFIER.matcher(identifier).matches()) {
      return "An identifier must consist of a letter or underscore (_), " +
             "followed by any number of letters, digits and underscores";
    }

    return null;
  }

  /**
   * Returns an error message describing the problem with the group name,
   * or {@code null} if the group name is valid.
   */
  @Nullable
  public String validateGroupName(@NotNull final String groupName) {
    if (PomskyBuiltins.Keywords.is(groupName)) {
      return "A group name cannot be a keyword";
    }

    if (groupName.length() > 32) {
      return "The group name is too long. It must be at most 32 characters";
    }

    if (!PATTERN_GROUP_NAME.matcher(groupName).matches()) {
      return "A group name must be ASCII-only, must not start with a number or contain underscores";
    }

    return null;
  }
}
