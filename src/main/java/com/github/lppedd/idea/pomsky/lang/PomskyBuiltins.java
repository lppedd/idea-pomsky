package com.github.lppedd.idea.pomsky.lang;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Describes variables and properties that are always present by default in the language.
 *
 * @author Edoardo Luppi
 */
public class PomskyBuiltins {
  private static final Map<String, Builtin> VARIABLES = new HashMap<>(32);

  /**
   * See <a href="https://pomsky-lang.org/docs/reference/built-in-variables/">built-in variables</a>.
   */
  @SuppressWarnings("unused")
  public static class Variables {
    public static final Builtin Grapheme = new Builtin("Grapheme", List.of("G"), VARIABLES);
    public static final Builtin Codepoint = new Builtin("Codepoint", List.of("C"), VARIABLES);
    public static final Builtin Start = new Builtin("Start", VARIABLES);
    public static final Builtin End = new Builtin("End", VARIABLES);

    @NotNull
    public static Collection<Builtin> all() {
      return Collections.unmodifiableCollection(VARIABLES.values());
    }

    public static boolean is(@NotNull final String name) {
      return VARIABLES.containsKey(name);
    }
  }

  @SuppressWarnings("unused")
  public static class Builtin {
    private final String name;
    private final Collection<String> aliases;
    private final Collection<String> variants;

    private Builtin(
        @NotNull final String name,
        @NotNull final Map<String, Builtin> builtins) {
      this(name, List.of(), builtins);
    }

    private Builtin(
        @NotNull final String name,
        @NotNull final List<String> aliases,
        @NotNull final Map<String, Builtin> builtins) {
      this.name = name;
      this.aliases = List.copyOf(aliases);

      final var variants = new ArrayList<String>(aliases.size() + 1);
      variants.add(name);
      variants.addAll(aliases);
      this.variants = List.copyOf(variants);

      for (final var variant : this.variants) {
        builtins.put(variant, this);
      }
    }

    @NotNull
    public String getName() {
      return name;
    }

    @NotNull
    public Collection<String> getAliases() {
      return aliases;
    }

    @NotNull
    public Collection<String> getAllVariants() {
      return variants;
    }
  }
}
