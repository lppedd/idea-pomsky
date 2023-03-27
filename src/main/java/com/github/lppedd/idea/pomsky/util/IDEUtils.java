package com.github.lppedd.idea.pomsky.util;

import org.jetbrains.annotations.NotNull;

/**
 * @author Edoardo Luppi
 */
public class IDEUtils {
  private static final String PLATFORM_PREFIX_KEY = "idea.platform.prefix";
  private static final String IDEA_PREFIX = "idea";
  private static final String IDEA_CE_PREFIX = "Idea";
  private static final String IDEA_EDU_PREFIX = "IdeaEdu";
  private static final String ANDROID_STUDIO_PREFIX = "AndroidStudio";
  private static final String PYCHARM_PREFIX = "Python";
  private static final String PYCHARM_CE_PREFIX = "PyCharmCore";
  private static final String DATASPELL_PREFIX = "DataSpell";
  private static final String PYCHARM_EDU_PREFIX = "PyCharmEdu";
  private static final String RUBY_PREFIX = "Ruby";
  private static final String WEB_PREFIX = "WebStorm";
  private static final String RIDER_PREFIX = "Rider";

  public static boolean isIntelliJ() {
    return is(IDEA_PREFIX) || is(IDEA_CE_PREFIX) || is(IDEA_EDU_PREFIX) || is(ANDROID_STUDIO_PREFIX);
  }

  public static boolean isWebStorm() {
    return is(WEB_PREFIX);
  }

  public static boolean isPyCharm() {
    return is(PYCHARM_PREFIX) || is(PYCHARM_CE_PREFIX) || is(PYCHARM_EDU_PREFIX) || is(DATASPELL_PREFIX);
  }

  public static boolean isRubyMine() {
    return is(RUBY_PREFIX);
  }

  public static boolean isRider() {
    return is(RIDER_PREFIX);
  }

  @NotNull
  private static String getPlatformPrefix() {
    return System.getProperty(PLATFORM_PREFIX_KEY, IDEA_PREFIX);
  }

  private static boolean is(@NotNull final String idePrefix) {
    return idePrefix.equals(getPlatformPrefix());
  }
}
