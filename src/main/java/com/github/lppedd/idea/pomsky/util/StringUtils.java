package com.github.lppedd.idea.pomsky.util;

import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class StringUtils {
  public static int countOccurrencesBackwards(@NotNull final String str, final char ch, final int startIndex) {
    final var length = str.length();

    if (startIndex > length - 1) {
      throw new IndexOutOfBoundsException("Start index is greater than string's last index");
    }

    if (length == 0) {
      return 0;
    }

    var i = startIndex;

    while (i > -1 && str.charAt(i) == ch) {
      i--;
    }

    return startIndex - i;
  }
}
