package com.github.lppedd.idea.pomsky.lang;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Describes variables and properties that are always present by default in the language.
 *
 * @author Edoardo Luppi
 */
@SuppressWarnings("unused")
public class PomskyBuiltins {
  private static final Map<String, Builtin> KEYWORDS = new HashMap<>(32);
  private static final Map<String, Builtin> CHARACTER_CLASSES = new HashMap<>(64);
  private static final Map<String, Builtin> VARIABLES = new HashMap<>(32);
  private static final Map<String, Builtin> CATEGORIES = new HashMap<>(128);
  private static final Map<String, Builtin> SCRIPTS = new HashMap<>(512);
  private static final Map<String, Builtin> BLOCKS = new HashMap<>(256);
  private static final Map<String, Builtin> OTHERS = new HashMap<>(128);

  public static class Keywords {
    public static final Builtin let = new Builtin("let", KEYWORDS);
    public static final Builtin lazy = new Builtin("lazy", KEYWORDS);
    public static final Builtin greedy = new Builtin("greedy", KEYWORDS);
    public static final Builtin range = new Builtin("range", KEYWORDS);
    public static final Builtin base = new Builtin("base", KEYWORDS);
    public static final Builtin atomic = new Builtin("atomic", KEYWORDS);
    public static final Builtin enable = new Builtin("enable", KEYWORDS);
    public static final Builtin disable = new Builtin("disable", KEYWORDS);
    public static final Builtin regex = new Builtin("regex", KEYWORDS);
    public static final Builtin test = new Builtin("test", KEYWORDS);
    public static final Builtin recursion = new Builtin("recursion", KEYWORDS);
    public static final Builtin iff = new Builtin("if", KEYWORDS);
    public static final Builtin elsee = new Builtin("else", KEYWORDS);
    public static final Builtin U = new Builtin("U", KEYWORDS);

    @NotNull
    public static Collection<Builtin> all() {
      return Collections.unmodifiableCollection(KEYWORDS.values());
    }

    public static boolean is(@NotNull final String name) {
      return KEYWORDS.containsKey(name);
    }
  }

  /**
   * See <a href="https://pomsky-lang.org/docs/reference/grammar/#characterset">CharacterSet grammar rule</a>.
   */
  public static class CharacterClasses {
    public static final Builtin n = new Builtin("n", CHARACTER_CLASSES);
    public static final Builtin r = new Builtin("r", CHARACTER_CLASSES);
    public static final Builtin t = new Builtin("t", CHARACTER_CLASSES);
    public static final Builtin a = new Builtin("a", CHARACTER_CLASSES);
    public static final Builtin e = new Builtin("e", CHARACTER_CLASSES);
    public static final Builtin f = new Builtin("f", CHARACTER_CLASSES);
    public static final Builtin word = new Builtin("word", List.of("w"), CHARACTER_CLASSES);
    public static final Builtin digit = new Builtin("word", List.of("d"), CHARACTER_CLASSES);
    public static final Builtin space = new Builtin("space", List.of("s"), CHARACTER_CLASSES);
    public static final Builtin horiz_space = new Builtin("horiz_space", List.of("h"), CHARACTER_CLASSES);
    public static final Builtin vert_space = new Builtin("vert_space", List.of("v"), CHARACTER_CLASSES);
    public static final Builtin ascii = new Builtin("ascii", CHARACTER_CLASSES);
    public static final Builtin ascii_alpha = new Builtin("ascii_alpha", CHARACTER_CLASSES);
    public static final Builtin ascii_alnum = new Builtin("ascii_alnum", CHARACTER_CLASSES);
    public static final Builtin ascii_blank = new Builtin("ascii_blank", CHARACTER_CLASSES);
    public static final Builtin ascii_cntrl = new Builtin("ascii_cntrl", CHARACTER_CLASSES);
    public static final Builtin ascii_digit = new Builtin("ascii_digit", CHARACTER_CLASSES);
    public static final Builtin ascii_graph = new Builtin("ascii_graph", CHARACTER_CLASSES);
    public static final Builtin ascii_lower = new Builtin("ascii_lower", CHARACTER_CLASSES);
    public static final Builtin ascii_print = new Builtin("ascii_print", CHARACTER_CLASSES);
    public static final Builtin ascii_punct = new Builtin("ascii_punct", CHARACTER_CLASSES);
    public static final Builtin ascii_space = new Builtin("ascii_space", CHARACTER_CLASSES);
    public static final Builtin ascii_upper = new Builtin("ascii_upper", CHARACTER_CLASSES);
    public static final Builtin ascii_word = new Builtin("ascii_word", CHARACTER_CLASSES);
    public static final Builtin ascii_xdigit = new Builtin("ascii_xdigit", CHARACTER_CLASSES);

    @NotNull
    public static Collection<Builtin> all() {
      return Collections.unmodifiableCollection(CHARACTER_CLASSES.values());
    }

    public static boolean is(@NotNull final String name) {
      return CHARACTER_CLASSES.containsKey(name);
    }
  }

  /**
   * See <a href="https://pomsky-lang.org/docs/reference/built-in-variables/">built-in variables</a>.
   */
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

  /**
   * See <a href="https://pomsky-lang.org/docs/reference/unicode-properties/">unicode properties</a>.
   */
  @SuppressWarnings("SpellCheckingInspection")
  public static class Properties {
    public static class Categories {
      public static final Builtin Cased_Letter = new Builtin("Cased_Letter", List.of("LC"), CATEGORIES);
      public static final Builtin Close_Punctuation = new Builtin("Close_Punctuation", List.of("Pe"), CATEGORIES);
      public static final Builtin Connector_Punctuation = new Builtin("Connector_Punctuation", List.of("Pc"), CATEGORIES);
      public static final Builtin Control = new Builtin("Control", List.of("Cc", "cntrl"), CATEGORIES);
      public static final Builtin Currency_Symbol = new Builtin("Currency_Symbol", List.of("Sc"), CATEGORIES);
      public static final Builtin Dash_Punctuation = new Builtin("Dash_Punctuation", List.of("Pd"), CATEGORIES);
      public static final Builtin Decimal_Number = new Builtin("Decimal_Number", List.of("Nd", "digit", "d"), CATEGORIES);
      public static final Builtin Enclosing_Mark = new Builtin("Enclosing_Mark", List.of("Me"), CATEGORIES);
      public static final Builtin Final_Punctuation = new Builtin("Final_Punctuation", List.of("Pf"), CATEGORIES);
      public static final Builtin Format = new Builtin("Format", List.of("Cf"), CATEGORIES);
      public static final Builtin Initial_Punctuation = new Builtin("Initial_Punctuation", List.of("Pi"), CATEGORIES);
      public static final Builtin Letter = new Builtin("Letter", List.of("L"), CATEGORIES);
      public static final Builtin Letter_Number = new Builtin("Letter_Number", List.of("Nl"), CATEGORIES);
      public static final Builtin Line_Separator = new Builtin("Line_Separator", List.of("Zl"), CATEGORIES);
      public static final Builtin Lowercase_Letter = new Builtin("Lowercase_Letter", List.of("Ll"), CATEGORIES);
      public static final Builtin Mark = new Builtin("Mark", List.of("M", "Combining_Mark"), CATEGORIES);
      public static final Builtin Math_Symbol = new Builtin("Math_Symbol", List.of("Sm"), CATEGORIES);
      public static final Builtin Modifier_Letter = new Builtin("Modifier_Letter", List.of("Lm"), CATEGORIES);
      public static final Builtin Modifier_Symbol = new Builtin("Modifier_Symbol", List.of("Sk"), CATEGORIES);
      public static final Builtin Nonspacing_Mark = new Builtin("Nonspacing_Mark", List.of("Mn"), CATEGORIES);
      public static final Builtin Number = new Builtin("Number", List.of("N"), CATEGORIES);
      public static final Builtin Open_Punctuation = new Builtin("Open_Punctuation", List.of("Ps"), CATEGORIES);
      public static final Builtin Other = new Builtin("Other", List.of("C"), CATEGORIES);
      public static final Builtin Other_Letter = new Builtin("Other_Letter", List.of("Lo"), CATEGORIES);
      public static final Builtin Other_Number = new Builtin("Other_Number", List.of("No"), CATEGORIES);
      public static final Builtin Other_Punctuation = new Builtin("Other_Punctuation", List.of("Po"), CATEGORIES);
      public static final Builtin Other_Symbol = new Builtin("Other_Symbol", List.of("So"), CATEGORIES);
      public static final Builtin Paragraph_Separator = new Builtin("Paragraph_Separator", List.of("Zp"), CATEGORIES);
      public static final Builtin Private_Use = new Builtin("Private_Use", List.of("Co"), CATEGORIES);
      public static final Builtin Punctuation = new Builtin("Punctuation", List.of("P", "punct"), CATEGORIES);
      public static final Builtin Separator = new Builtin("Separator", List.of("Z", "space", "s"), CATEGORIES);
      public static final Builtin Space_Separator = new Builtin("Space_Separator", List.of("Zs"), CATEGORIES);
      public static final Builtin Spacing_Mark = new Builtin("Spacing_Mark", List.of("Mc"), CATEGORIES);
      public static final Builtin Surrogate = new Builtin("Surrogate", List.of("Cs"), CATEGORIES);
      public static final Builtin Symbol = new Builtin("Symbol", List.of("S"), CATEGORIES);
      public static final Builtin Titlecase_Letter = new Builtin("Titlecase_Letter", List.of("Lt"), CATEGORIES);
      public static final Builtin Unassigned = new Builtin("Unassigned", List.of("Cn"), CATEGORIES);
      public static final Builtin Uppercase_Letter = new Builtin("Uppercase_Letter", List.of("Lu"), CATEGORIES);

      @NotNull
      public static Collection<Builtin> all() {
        return Collections.unmodifiableCollection(CATEGORIES.values());
      }

      public static boolean is(@NotNull final String name) {
        return CATEGORIES.containsKey(name);
      }
    }

    public static class Scripts {
      public static final Builtin Adlam = new Builtin("Adlam", List.of("Adlm"), SCRIPTS);
      public static final Builtin Ahom = new Builtin("Ahom", SCRIPTS);
      public static final Builtin Anatolian_Hieroglyphs = new Builtin("Anatolian_Hieroglyphs", List.of("Hluw"), SCRIPTS);
      public static final Builtin Arabic = new Builtin("Arabic", List.of("Arab"), SCRIPTS);
      public static final Builtin Armenian = new Builtin("Armenian", List.of("Armn"), SCRIPTS);
      public static final Builtin Avestan = new Builtin("Avestan", List.of("Avst"), SCRIPTS);
      public static final Builtin Balinese = new Builtin("Balinese", List.of("Bali"), SCRIPTS);
      public static final Builtin Bamum = new Builtin("Bamum", List.of("Bamu"), SCRIPTS);
      public static final Builtin Bassa_Vah = new Builtin("Bassa_Vah", List.of("Bass"), SCRIPTS);
      public static final Builtin Batak = new Builtin("Batak", List.of("Batk"), SCRIPTS);
      public static final Builtin Bengali = new Builtin("Bengali", List.of("Beng"), SCRIPTS);
      public static final Builtin Bhaiksuki = new Builtin("Bhaiksuki", List.of("Bhks"), SCRIPTS);
      public static final Builtin Bopomofo = new Builtin("Bopomofo", List.of("Bopo"), SCRIPTS);
      public static final Builtin Brahmi = new Builtin("Brahmi", List.of("Brah"), SCRIPTS);
      public static final Builtin Braille = new Builtin("Braille", List.of("Brai"), SCRIPTS);
      public static final Builtin Buginese = new Builtin("Buginese", List.of("Bugi"), SCRIPTS);
      public static final Builtin Buhid = new Builtin("Buhid", List.of("Buhd"), SCRIPTS);
      public static final Builtin Canadian_Aboriginal = new Builtin("Canadian_Aboriginal", List.of("Cans"), SCRIPTS);
      public static final Builtin Carian = new Builtin("Carian", List.of("Cari"), SCRIPTS);
      public static final Builtin Caucasian_Albanian = new Builtin("Caucasian_Albanian", List.of("Aghb"), SCRIPTS);
      public static final Builtin Chakma = new Builtin("Chakma", List.of("Cakm"), SCRIPTS);
      public static final Builtin Cham = new Builtin("Cham", SCRIPTS);
      public static final Builtin Chorasmian = new Builtin("Chorasmian", List.of("Chrs"), SCRIPTS);
      public static final Builtin Cherokee = new Builtin("Cherokee", List.of("Cher"), SCRIPTS);
      public static final Builtin Common = new Builtin("Common", List.of("Zyyy"), SCRIPTS);
      public static final Builtin Coptic = new Builtin("Coptic", List.of("Copt"), SCRIPTS);
      public static final Builtin Cuneiform = new Builtin("Cuneiform", List.of("Xsux"), SCRIPTS);
      public static final Builtin Cypriot = new Builtin("Cypriot", List.of("Cprt"), SCRIPTS);
      public static final Builtin Cypro_Minoan = new Builtin("Cypro_Minoan", List.of("Cpmn"), SCRIPTS);
      public static final Builtin Cyrillic = new Builtin("Cyrillic", List.of("Cyrl"), SCRIPTS);
      public static final Builtin Deseret = new Builtin("Deseret", List.of("Dsrt"), SCRIPTS);
      public static final Builtin Devanagari = new Builtin("Devanagari", List.of("Deva"), SCRIPTS);
      public static final Builtin Dives_Akuru = new Builtin("Dives_Akuru", List.of("Diak"), SCRIPTS);
      public static final Builtin Dogra = new Builtin("Dogra", List.of("Dogr"), SCRIPTS);
      public static final Builtin Duployan = new Builtin("Duployan", List.of("Dupl"), SCRIPTS);
      public static final Builtin Egyptian_Hieroglyphs = new Builtin("Egyptian_Hieroglyphs", List.of("Egyp"), SCRIPTS);
      public static final Builtin Elbasan = new Builtin("Elbasan", List.of("Elba"), SCRIPTS);
      public static final Builtin Elymaic = new Builtin("Elymaic", List.of("Elym"), SCRIPTS);
      public static final Builtin Ethiopic = new Builtin("Ethiopic", List.of("Ethi"), SCRIPTS);
      public static final Builtin Georgian = new Builtin("Georgian", List.of("Geor"), SCRIPTS);
      public static final Builtin Glagolitic = new Builtin("Glagolitic", List.of("Glag"), SCRIPTS);
      public static final Builtin Gothic = new Builtin("Gothic", List.of("Goth"), SCRIPTS);
      public static final Builtin Grantha = new Builtin("Grantha", List.of("Gran"), SCRIPTS);
      public static final Builtin Greek = new Builtin("Greek", List.of("Grek"), SCRIPTS);
      public static final Builtin Gujarati = new Builtin("Gujarati", List.of("Gujr"), SCRIPTS);
      public static final Builtin Gunjala_Gondi = new Builtin("Gunjala_Gondi", List.of("Gong"), SCRIPTS);
      public static final Builtin Gurmukhi = new Builtin("Gurmukhi", List.of("Guru"), SCRIPTS);
      public static final Builtin Han = new Builtin("Han", List.of("Hani"), SCRIPTS);
      public static final Builtin Hangul = new Builtin("Hangul", List.of("Hang"), SCRIPTS);
      public static final Builtin Hanifi_Rohingya = new Builtin("Hanifi_Rohingya", List.of("Rohg"), SCRIPTS);
      public static final Builtin Hanunoo = new Builtin("Hanunoo", List.of("Hano"), SCRIPTS);
      public static final Builtin Hatran = new Builtin("Hatran", List.of("Hatr"), SCRIPTS);
      public static final Builtin Hebrew = new Builtin("Hebrew", List.of("Hebr"), SCRIPTS);
      public static final Builtin Hiragana = new Builtin("Hiragana", List.of("Hira"), SCRIPTS);
      public static final Builtin Imperial_Aramaic = new Builtin("Imperial_Aramaic", List.of("Armi"), SCRIPTS);
      public static final Builtin Inherited = new Builtin("Inherited", List.of("Zinh"), SCRIPTS);
      public static final Builtin Inscriptional_Pahlavi = new Builtin("Inscriptional_Pahlavi", List.of("Phli"), SCRIPTS);
      public static final Builtin Inscriptional_Parthian = new Builtin("Inscriptional_Parthian", List.of("Prti"), SCRIPTS);
      public static final Builtin Javanese = new Builtin("Javanese", List.of("Java"), SCRIPTS);
      public static final Builtin Kaithi = new Builtin("Kaithi", List.of("Kthi"), SCRIPTS);
      public static final Builtin Kannada = new Builtin("Kannada", List.of("Knda"), SCRIPTS);
      public static final Builtin Katakana = new Builtin("Katakana", List.of("Kana"), SCRIPTS);
      public static final Builtin Kayah_Li = new Builtin("Kayah_Li", List.of("Kali"), SCRIPTS);
      public static final Builtin Kharoshthi = new Builtin("Kharoshthi", List.of("Khar"), SCRIPTS);
      public static final Builtin Khitan_Small_Script = new Builtin("Khitan_Small_Script", List.of("Kits"), SCRIPTS);
      public static final Builtin Khmer = new Builtin("Khmer", List.of("Khmr"), SCRIPTS);
      public static final Builtin Khojki = new Builtin("Khojki", List.of("Khoj"), SCRIPTS);
      public static final Builtin Khudawadi = new Builtin("Khudawadi", List.of("Sind"), SCRIPTS);
      public static final Builtin Lao = new Builtin("Lao", List.of("Laoo"), SCRIPTS);
      public static final Builtin Latin = new Builtin("Latin", List.of("Latn"), SCRIPTS);
      public static final Builtin Lepcha = new Builtin("Lepcha", List.of("Lepc"), SCRIPTS);
      public static final Builtin Limbu = new Builtin("Limbu", List.of("Limb"), SCRIPTS);
      public static final Builtin Linear_A = new Builtin("Linear_A", List.of("Lina"), SCRIPTS);
      public static final Builtin Linear_B = new Builtin("Linear_B", List.of("Linb"), SCRIPTS);
      public static final Builtin Lisu = new Builtin("Lisu", SCRIPTS);
      public static final Builtin Lycian = new Builtin("Lycian", List.of("Lyci"), SCRIPTS);
      public static final Builtin Lydian = new Builtin("Lydian", List.of("Lydi"), SCRIPTS);
      public static final Builtin Mahajani = new Builtin("Mahajani", List.of("Mahj"), SCRIPTS);
      public static final Builtin Makasar = new Builtin("Makasar", List.of("Maka"), SCRIPTS);
      public static final Builtin Malayalam = new Builtin("Malayalam", List.of("Mlym"), SCRIPTS);
      public static final Builtin Mandaic = new Builtin("Mandaic", List.of("Mand"), SCRIPTS);
      public static final Builtin Manichaean = new Builtin("Manichaean", List.of("Mani"), SCRIPTS);
      public static final Builtin Marchen = new Builtin("Marchen", List.of("Marc"), SCRIPTS);
      public static final Builtin Medefaidrin = new Builtin("Medefaidrin", List.of("Medf"), SCRIPTS);
      public static final Builtin Masaram_Gondi = new Builtin("Masaram_Gondi", List.of("Gonm"), SCRIPTS);
      public static final Builtin Meetei_Mayek = new Builtin("Meetei_Mayek", List.of("Mtei"), SCRIPTS);
      public static final Builtin Mende_Kikakui = new Builtin("Mende_Kikakui", List.of("Mend"), SCRIPTS);
      public static final Builtin Meroitic_Cursive = new Builtin("Meroitic_Cursive", List.of("Merc"), SCRIPTS);
      public static final Builtin Meroitic_Hieroglyphs = new Builtin("Meroitic_Hieroglyphs", List.of("Mero"), SCRIPTS);
      public static final Builtin Miao = new Builtin("Miao", List.of("Plrd"), SCRIPTS);
      public static final Builtin Modi = new Builtin("Modi", SCRIPTS);
      public static final Builtin Mongolian = new Builtin("Mongolian", List.of("Mong"), SCRIPTS);
      public static final Builtin Mro = new Builtin("Mro", List.of("Mroo"), SCRIPTS);
      public static final Builtin Multani = new Builtin("Multani", List.of("Mult"), SCRIPTS);
      public static final Builtin Myanmar = new Builtin("Myanmar", List.of("Mymr"), SCRIPTS);
      public static final Builtin Nabataean = new Builtin("Nabataean", List.of("Nbat"), SCRIPTS);
      public static final Builtin Nandinagari = new Builtin("Nandinagari", List.of("Nand"), SCRIPTS);
      public static final Builtin New_Tai_Lue = new Builtin("New_Tai_Lue", List.of("Talu"), SCRIPTS);
      public static final Builtin Newa = new Builtin("Newa", SCRIPTS);
      public static final Builtin Nko = new Builtin("Nko", List.of("Nkoo"), SCRIPTS);
      public static final Builtin Nushu = new Builtin("Nushu", List.of("Nshu"), SCRIPTS);
      public static final Builtin Nyiakeng_Puachue_Hmong = new Builtin("Nyiakeng_Puachue_Hmong", List.of("Hmnp"), SCRIPTS);
      public static final Builtin Ogham = new Builtin("Ogham", List.of("Ogam"), SCRIPTS);
      public static final Builtin Ol_Chiki = new Builtin("Ol_Chiki", List.of("Olck"), SCRIPTS);
      public static final Builtin Old_Hungarian = new Builtin("Old_Hungarian", List.of("Hung"), SCRIPTS);
      public static final Builtin Old_Italic = new Builtin("Old_Italic", List.of("Ital"), SCRIPTS);
      public static final Builtin Old_North_Arabian = new Builtin("Old_North_Arabian", List.of("Narb"), SCRIPTS);
      public static final Builtin Old_Permic = new Builtin("Old_Permic", List.of("Perm"), SCRIPTS);
      public static final Builtin Old_Persian = new Builtin("Old_Persian", List.of("Xpeo"), SCRIPTS);
      public static final Builtin Old_Sogdian = new Builtin("Old_Sogdian", List.of("Sogo"), SCRIPTS);
      public static final Builtin Old_South_Arabian = new Builtin("Old_South_Arabian", List.of("Sarb"), SCRIPTS);
      public static final Builtin Old_Turkic = new Builtin("Old_Turkic", List.of("Orkh"), SCRIPTS);
      public static final Builtin Old_Uyghur = new Builtin("Old_Uyghur", List.of("Ougr"), SCRIPTS);
      public static final Builtin Oriya = new Builtin("Oriya", List.of("Orya"), SCRIPTS);
      public static final Builtin Osage = new Builtin("Osage", List.of("Osge"), SCRIPTS);
      public static final Builtin Osmanya = new Builtin("Osmanya", List.of("Osma"), SCRIPTS);
      public static final Builtin Pahawh_Hmong = new Builtin("Pahawh_Hmong", List.of("Hmng"), SCRIPTS);
      public static final Builtin Palmyrene = new Builtin("Palmyrene", List.of("Palm"), SCRIPTS);
      public static final Builtin Pau_Cin_Hau = new Builtin("Pau_Cin_Hau", List.of("Pauc"), SCRIPTS);
      public static final Builtin Phags_Pa = new Builtin("Phags_Pa", List.of("Phag"), SCRIPTS);
      public static final Builtin Phoenician = new Builtin("Phoenician", List.of("Phnx"), SCRIPTS);
      public static final Builtin Psalter_Pahlavi = new Builtin("Psalter_Pahlavi", List.of("Phlp"), SCRIPTS);
      public static final Builtin Rejang = new Builtin("Rejang", List.of("Rjng"), SCRIPTS);
      public static final Builtin Runic = new Builtin("Runic", List.of("Runr"), SCRIPTS);
      public static final Builtin Samaritan = new Builtin("Samaritan", List.of("Samr"), SCRIPTS);
      public static final Builtin Saurashtra = new Builtin("Saurashtra", List.of("Saur"), SCRIPTS);
      public static final Builtin Sharada = new Builtin("Sharada", List.of("Shrd"), SCRIPTS);
      public static final Builtin Shavian = new Builtin("Shavian", List.of("Shaw"), SCRIPTS);
      public static final Builtin Siddham = new Builtin("Siddham", List.of("Sidd"), SCRIPTS);
      public static final Builtin SignWriting = new Builtin("SignWriting", List.of("Sgnw"), SCRIPTS);
      public static final Builtin Sinhala = new Builtin("Sinhala", List.of("Sinh"), SCRIPTS);
      public static final Builtin Sogdian = new Builtin("Sogdian", List.of("Sogd"), SCRIPTS);
      public static final Builtin Sora_Sompeng = new Builtin("Sora_Sompeng", List.of("Sora"), SCRIPTS);
      public static final Builtin Soyombo = new Builtin("Soyombo", List.of("Soyo"), SCRIPTS);
      public static final Builtin Sundanese = new Builtin("Sundanese", List.of("Sund"), SCRIPTS);
      public static final Builtin Syloti_Nagri = new Builtin("Syloti_Nagri", List.of("Sylo"), SCRIPTS);
      public static final Builtin Syriac = new Builtin("Syriac", List.of("Syrc"), SCRIPTS);
      public static final Builtin Tagalog = new Builtin("Tagalog", List.of("Tglg"), SCRIPTS);
      public static final Builtin Tagbanwa = new Builtin("Tagbanwa", List.of("Tagb"), SCRIPTS);
      public static final Builtin Tai_Le = new Builtin("Tai_Le", List.of("Tale"), SCRIPTS);
      public static final Builtin Tai_Tham = new Builtin("Tai_Tham", List.of("Lana"), SCRIPTS);
      public static final Builtin Tai_Viet = new Builtin("Tai_Viet", List.of("Tavt"), SCRIPTS);
      public static final Builtin Takri = new Builtin("Takri", List.of("Takr"), SCRIPTS);
      public static final Builtin Tamil = new Builtin("Tamil", List.of("Taml"), SCRIPTS);
      public static final Builtin Tangsa = new Builtin("Tangsa", List.of("Tnsa"), SCRIPTS);
      public static final Builtin Tangut = new Builtin("Tangut", List.of("Tang"), SCRIPTS);
      public static final Builtin Telugu = new Builtin("Telugu", List.of("Telu"), SCRIPTS);
      public static final Builtin Thaana = new Builtin("Thaana", List.of("Thaa"), SCRIPTS);
      public static final Builtin Thai = new Builtin("Thai", SCRIPTS);
      public static final Builtin Tibetan = new Builtin("Tibetan", List.of("Tibt"), SCRIPTS);
      public static final Builtin Tifinagh = new Builtin("Tifinagh", List.of("Tfng"), SCRIPTS);
      public static final Builtin Tirhuta = new Builtin("Tirhuta", List.of("Tirh"), SCRIPTS);
      public static final Builtin Toto = new Builtin("Toto", SCRIPTS);
      public static final Builtin Ugaritic = new Builtin("Ugaritic", List.of("Ugar"), SCRIPTS);
      public static final Builtin Vai = new Builtin("Vai", List.of("Vaii"), SCRIPTS);
      public static final Builtin Vithkuqi = new Builtin("Vithkuqi", List.of("Vith"), SCRIPTS);
      public static final Builtin Wancho = new Builtin("Wancho", List.of("Wcho"), SCRIPTS);
      public static final Builtin Warang_Citi = new Builtin("Warang_Citi", List.of("Wara"), SCRIPTS);
      public static final Builtin Yezidi = new Builtin("Yezidi", List.of("Yezi"), SCRIPTS);
      public static final Builtin Yi = new Builtin("Yi", List.of("Yiii"), SCRIPTS);
      public static final Builtin Zanabazar_Square = new Builtin("Zanabazar_Square", List.of("Zanb"), SCRIPTS);

      @NotNull
      public static Collection<Builtin> all() {
        return Collections.unmodifiableCollection(SCRIPTS.values());
      }

      public static boolean is(@NotNull final String name) {
        return SCRIPTS.containsKey(name);
      }
    }

    public static class Blocks {
      public static final Builtin InBasic_Latin = new Builtin("InBasic_Latin", BLOCKS);
      public static final Builtin InLatin_1_Supplement = new Builtin("InLatin_1_Supplement", BLOCKS);
      public static final Builtin InLatin_Extended_A = new Builtin("InLatin_Extended_A", BLOCKS);
      public static final Builtin InLatin_Extended_B = new Builtin("InLatin_Extended_B", BLOCKS);
      public static final Builtin InIPA_Extensions = new Builtin("InIPA_Extensions", BLOCKS);
      public static final Builtin InSpacing_Modifier_Letters = new Builtin("InSpacing_Modifier_Letters", BLOCKS);
      public static final Builtin InCombining_Diacritical_Marks = new Builtin("InCombining_Diacritical_Marks", BLOCKS);
      public static final Builtin InGreek_and_Coptic = new Builtin("InGreek_and_Coptic", BLOCKS);
      public static final Builtin InCyrillic = new Builtin("InCyrillic", BLOCKS);
      public static final Builtin InCyrillic_Supplementary = new Builtin("InCyrillic_Supplementary", BLOCKS);
      public static final Builtin InArmenian = new Builtin("InArmenian", BLOCKS);
      public static final Builtin InHebrew = new Builtin("InHebrew", BLOCKS);
      public static final Builtin InArabic = new Builtin("InArabic", BLOCKS);
      public static final Builtin InSyriac = new Builtin("InSyriac", BLOCKS);
      public static final Builtin InThaana = new Builtin("InThaana", BLOCKS);
      public static final Builtin InDevanagari = new Builtin("InDevanagari", BLOCKS);
      public static final Builtin InBengali = new Builtin("InBengali", BLOCKS);
      public static final Builtin InGurmukhi = new Builtin("InGurmukhi", BLOCKS);
      public static final Builtin InGujarati = new Builtin("InGujarati", BLOCKS);
      public static final Builtin InOriya = new Builtin("InOriya", BLOCKS);
      public static final Builtin InTamil = new Builtin("InTamil", BLOCKS);
      public static final Builtin InTelugu = new Builtin("InTelugu", BLOCKS);
      public static final Builtin InKannada = new Builtin("InKannada", BLOCKS);
      public static final Builtin InMalayalam = new Builtin("InMalayalam", BLOCKS);
      public static final Builtin InSinhala = new Builtin("InSinhala", BLOCKS);
      public static final Builtin InThai = new Builtin("InThai", BLOCKS);
      public static final Builtin InLao = new Builtin("InLao", BLOCKS);
      public static final Builtin InTibetan = new Builtin("InTibetan", BLOCKS);
      public static final Builtin InMyanmar = new Builtin("InMyanmar", BLOCKS);
      public static final Builtin InGeorgian = new Builtin("InGeorgian", BLOCKS);
      public static final Builtin InHangul_Jamo = new Builtin("InHangul_Jamo", BLOCKS);
      public static final Builtin InEthiopic = new Builtin("InEthiopic", BLOCKS);
      public static final Builtin InCherokee = new Builtin("InCherokee", BLOCKS);
      public static final Builtin InUnified_Canadian_Aboriginal_Syllabics = new Builtin("InUnified_Canadian_Aboriginal_Syllabics", BLOCKS);
      public static final Builtin InOgham = new Builtin("InOgham", BLOCKS);
      public static final Builtin InRunic = new Builtin("InRunic", BLOCKS);
      public static final Builtin InTagalog = new Builtin("InTagalog", BLOCKS);
      public static final Builtin InHanunoo = new Builtin("InHanunoo", BLOCKS);
      public static final Builtin InBuhid = new Builtin("InBuhid", BLOCKS);
      public static final Builtin InTagbanwa = new Builtin("InTagbanwa", BLOCKS);
      public static final Builtin InKhmer = new Builtin("InKhmer", BLOCKS);
      public static final Builtin InMongolian = new Builtin("InMongolian", BLOCKS);
      public static final Builtin InLimbu = new Builtin("InLimbu", BLOCKS);
      public static final Builtin InTai_Le = new Builtin("InTai_Le", BLOCKS);
      public static final Builtin InKhmer_Symbols = new Builtin("InKhmer_Symbols", BLOCKS);
      public static final Builtin InPhonetic_Extensions = new Builtin("InPhonetic_Extensions", BLOCKS);
      public static final Builtin InLatin_Extended_Additional = new Builtin("InLatin_Extended_Additional", BLOCKS);
      public static final Builtin InGreek_Extended = new Builtin("InGreek_Extended", BLOCKS);
      public static final Builtin InGeneral_Punctuation = new Builtin("InGeneral_Punctuation", BLOCKS);
      public static final Builtin InSuperscripts_and_Subscripts = new Builtin("InSuperscripts_and_Subscripts", BLOCKS);
      public static final Builtin InCurrency_Symbols = new Builtin("InCurrency_Symbols", BLOCKS);
      public static final Builtin InCombining_Diacritical_Marks_for_Symbols = new Builtin("InCombining_Diacritical_Marks_for_Symbols", BLOCKS);
      public static final Builtin InLetterlike_Symbols = new Builtin("InLetterlike_Symbols", BLOCKS);
      public static final Builtin InNumber_Forms = new Builtin("InNumber_Forms", BLOCKS);
      public static final Builtin InArrows = new Builtin("InArrows", BLOCKS);
      public static final Builtin InMathematical_Operators = new Builtin("InMathematical_Operators", BLOCKS);
      public static final Builtin InMiscellaneous_Technical = new Builtin("InMiscellaneous_Technical", BLOCKS);
      public static final Builtin InControl_Pictures = new Builtin("InControl_Pictures", BLOCKS);
      public static final Builtin InOptical_Character_Recognition = new Builtin("InOptical_Character_Recognition", BLOCKS);
      public static final Builtin InEnclosed_Alphanumerics = new Builtin("InEnclosed_Alphanumerics", BLOCKS);
      public static final Builtin InBox_Drawing = new Builtin("InBox_Drawing", BLOCKS);
      public static final Builtin InBlock_Elements = new Builtin("InBlock_Elements", BLOCKS);
      public static final Builtin InGeometric_Shapes = new Builtin("InGeometric_Shapes", BLOCKS);
      public static final Builtin InMiscellaneous_Symbols = new Builtin("InMiscellaneous_Symbols", BLOCKS);
      public static final Builtin InDingbats = new Builtin("InDingbats", BLOCKS);
      public static final Builtin InMiscellaneous_Mathematical_Symbols_A = new Builtin("InMiscellaneous_Mathematical_Symbols_A", BLOCKS);
      public static final Builtin InSupplemental_Arrows_A = new Builtin("InSupplemental_Arrows_A", BLOCKS);
      public static final Builtin InBraille_Patterns = new Builtin("InBraille_Patterns", BLOCKS);
      public static final Builtin InSupplemental_Arrows_B = new Builtin("InSupplemental_Arrows_B", BLOCKS);
      public static final Builtin InMiscellaneous_Mathematical_Symbols_B = new Builtin("InMiscellaneous_Mathematical_Symbols_B", BLOCKS);
      public static final Builtin InSupplemental_Mathematical_Operators = new Builtin("InSupplemental_Mathematical_Operators", BLOCKS);
      public static final Builtin InMiscellaneous_Symbols_and_Arrows = new Builtin("InMiscellaneous_Symbols_and_Arrows", BLOCKS);
      public static final Builtin InCJK_Radicals_Supplement = new Builtin("InCJK_Radicals_Supplement", BLOCKS);
      public static final Builtin InKangxi_Radicals = new Builtin("InKangxi_Radicals", BLOCKS);
      public static final Builtin InIdeographic_Description_Characters = new Builtin("InIdeographic_Description_Characters", BLOCKS);
      public static final Builtin InCJK_Symbols_and_Punctuation = new Builtin("InCJK_Symbols_and_Punctuation", BLOCKS);
      public static final Builtin InHiragana = new Builtin("InHiragana", BLOCKS);
      public static final Builtin InKatakana = new Builtin("InKatakana", BLOCKS);
      public static final Builtin InBopomofo = new Builtin("InBopomofo", BLOCKS);
      public static final Builtin InHangul_Compatibility_Jamo = new Builtin("InHangul_Compatibility_Jamo", BLOCKS);
      public static final Builtin InKanbun = new Builtin("InKanbun", BLOCKS);
      public static final Builtin InBopomofo_Extended = new Builtin("InBopomofo_Extended", BLOCKS);
      public static final Builtin InKatakana_Phonetic_Extensions = new Builtin("InKatakana_Phonetic_Extensions", BLOCKS);
      public static final Builtin InEnclosed_CJK_Letters_and_Months = new Builtin("InEnclosed_CJK_Letters_and_Months", BLOCKS);
      public static final Builtin InCJK_Compatibility = new Builtin("InCJK_Compatibility", BLOCKS);
      public static final Builtin InCJK_Unified_Ideographs_Extension_A = new Builtin("InCJK_Unified_Ideographs_Extension_A", BLOCKS);
      public static final Builtin InYijing_Hexagram_Symbols = new Builtin("InYijing_Hexagram_Symbols", BLOCKS);
      public static final Builtin InCJK_Unified_Ideographs = new Builtin("InCJK_Unified_Ideographs", BLOCKS);
      public static final Builtin InYi_Syllables = new Builtin("InYi_Syllables", BLOCKS);
      public static final Builtin InYi_Radicals = new Builtin("InYi_Radicals", BLOCKS);
      public static final Builtin InHangul_Syllables = new Builtin("InHangul_Syllables", BLOCKS);
      public static final Builtin InHigh_Surrogates = new Builtin("InHigh_Surrogates", BLOCKS);
      public static final Builtin InHigh_Private_Use_Surrogates = new Builtin("InHigh_Private_Use_Surrogates", BLOCKS);
      public static final Builtin InLow_Surrogates = new Builtin("InLow_Surrogates", BLOCKS);
      public static final Builtin InPrivate_Use_Area = new Builtin("InPrivate_Use_Area", BLOCKS);
      public static final Builtin InCJK_Compatibility_Ideographs = new Builtin("InCJK_Compatibility_Ideographs", BLOCKS);
      public static final Builtin InAlphabetic_Presentation_Forms = new Builtin("InAlphabetic_Presentation_Forms", BLOCKS);
      public static final Builtin InArabic_Presentation_Forms_A = new Builtin("InArabic_Presentation_Forms_A", BLOCKS);
      public static final Builtin InVariation_Selectors = new Builtin("InVariation_Selectors", BLOCKS);
      public static final Builtin InCombining_Half_Marks = new Builtin("InCombining_Half_Marks", BLOCKS);
      public static final Builtin InCJK_Compatibility_Forms = new Builtin("InCJK_Compatibility_Forms", BLOCKS);
      public static final Builtin InSmall_Form_Variants = new Builtin("InSmall_Form_Variants", BLOCKS);
      public static final Builtin InArabic_Presentation_Forms_B = new Builtin("InArabic_Presentation_Forms_B", BLOCKS);
      public static final Builtin InHalfwidth_and_Fullwidth_Forms = new Builtin("InHalfwidth_and_Fullwidth_Forms", BLOCKS);
      public static final Builtin InSpecials = new Builtin("InSpecials", BLOCKS);

      @NotNull
      public static Collection<Builtin> all() {
        return Collections.unmodifiableCollection(BLOCKS.values());
      }

      public static boolean is(@NotNull final String name) {
        return BLOCKS.containsKey(name);
      }
    }

    public static class Others {
      public static final Builtin White_Space = new Builtin("White_Space", OTHERS);
      public static final Builtin Alphabetic = new Builtin("Alphabetic", List.of("Alpha"), OTHERS);
      public static final Builtin Noncharacter_Code_Point = new Builtin("Noncharacter_Code_Point", OTHERS);
      public static final Builtin Default_Ignorable_Code_Point = new Builtin("Default_Ignorable_Code_Point", OTHERS);
      public static final Builtin Logical_Order_Exception = new Builtin("Logical_Order_Exception", OTHERS);
      public static final Builtin Deprecated = new Builtin("Deprecated", OTHERS);
      public static final Builtin Variation_Selector = new Builtin("Variation_Selector", OTHERS);
      public static final Builtin Uppercase = new Builtin("Uppercase", List.of("upper"), OTHERS);
      public static final Builtin Lowercase = new Builtin("Lowercase", List.of("lower"), OTHERS);
      public static final Builtin Soft_Dotted = new Builtin("Soft_Dotted", OTHERS);
      public static final Builtin Case_Ignorable = new Builtin("Case_Ignorable", OTHERS);
      public static final Builtin Changes_When_Lowercased = new Builtin("Changes_When_Lowercased", OTHERS);
      public static final Builtin Changes_When_Uppercased = new Builtin("Changes_When_Uppercased", OTHERS);
      public static final Builtin Changes_When_Titlecased = new Builtin("Changes_When_Titlecased", OTHERS);
      public static final Builtin Changes_When_Casefolded = new Builtin("Changes_When_Casefolded", OTHERS);
      public static final Builtin Changes_When_Casemapped = new Builtin("Changes_When_Casemapped", OTHERS);
      public static final Builtin Emoji = new Builtin("Emoji", OTHERS);
      public static final Builtin Emoji_Presentation = new Builtin("Emoji_Presentation", OTHERS);
      public static final Builtin Emoji_Modifier = new Builtin("Emoji_Modifier", OTHERS);
      public static final Builtin Emoji_Modifier_Base = new Builtin("Emoji_Modifier_Base", OTHERS);
      public static final Builtin Emoji_Component = new Builtin("Emoji_Component", OTHERS);
      public static final Builtin Extended_Pictographic = new Builtin("Extended_Pictographic", OTHERS);
      public static final Builtin Hex_Digit = new Builtin("Hex_Digit", OTHERS);
      public static final Builtin ASCII_Hex_Digit = new Builtin("ASCII_Hex_Digit", OTHERS);
      public static final Builtin Join_Control = new Builtin("Join_Control", OTHERS);
      public static final Builtin Joining_Group = new Builtin("Joining_Group", OTHERS);
      public static final Builtin Bidi_Control = new Builtin("Bidi_Control", OTHERS);
      public static final Builtin Bidi_Mirrored = new Builtin("Bidi_Mirrored", OTHERS);
      public static final Builtin Bidi_Mirroring_Glyph = new Builtin("Bidi_Mirroring_Glyph", OTHERS);
      public static final Builtin ID_Continue = new Builtin("ID_Continue", OTHERS);
      public static final Builtin ID_Start = new Builtin("ID_Start", OTHERS);
      public static final Builtin XID_Continue = new Builtin("XID_Continue", OTHERS);
      public static final Builtin XID_Start = new Builtin("XID_Start", OTHERS);
      public static final Builtin Pattern_Syntax = new Builtin("Pattern_Syntax", OTHERS);
      public static final Builtin Pattern_White_Space = new Builtin("Pattern_White_Space", OTHERS);
      public static final Builtin Ideographic = new Builtin("Ideographic", OTHERS);
      public static final Builtin Unified_Ideograph = new Builtin("Unified_Ideograph", OTHERS);
      public static final Builtin Radical = new Builtin("Radical", OTHERS);
      public static final Builtin IDS_Binary_Operator = new Builtin("IDS_Binary_Operator", OTHERS);
      public static final Builtin IDS_Trinary_Operator = new Builtin("IDS_Trinary_Operator", OTHERS);
      public static final Builtin Math = new Builtin("Math", OTHERS);
      public static final Builtin Quotation_Mark = new Builtin("Quotation_Mark", OTHERS);
      public static final Builtin Dash = new Builtin("Dash", OTHERS);
      public static final Builtin Sentence_Terminal = new Builtin("Sentence_Terminal", OTHERS);
      public static final Builtin Terminal_Punctuation = new Builtin("Terminal_Punctuation", OTHERS);
      public static final Builtin Diacritic = new Builtin("Diacritic", OTHERS);
      public static final Builtin Extender = new Builtin("Extender", OTHERS);
      public static final Builtin Grapheme_Base = new Builtin("Grapheme_Base", OTHERS);
      public static final Builtin Grapheme_Extend = new Builtin("Grapheme_Extend", OTHERS);
      public static final Builtin Regional_Indicator = new Builtin("Regional_Indicator", OTHERS);

      @NotNull
      public static Collection<Builtin> all() {
        return Collections.unmodifiableCollection(OTHERS.values());
      }

      public static boolean is(@NotNull final String name) {
        return OTHERS.containsKey(name);
      }
    }

    @NotNull
    public static Collection<Builtin> all() {
      // TODO: return a view to minimize memory consumption
      final var properties = new ArrayList<Builtin>(1024);
      properties.addAll(PomskyBuiltins.Properties.Categories.all());
      properties.addAll(PomskyBuiltins.Properties.Scripts.all());
      properties.addAll(PomskyBuiltins.Properties.Blocks.all());
      properties.addAll(PomskyBuiltins.Properties.Others.all());
      return Collections.unmodifiableCollection(properties);
    }

    public static boolean is(@NotNull final String name) {
      return PomskyBuiltins.Properties.Categories.is(name) ||
             PomskyBuiltins.Properties.Scripts.is(name) ||
             PomskyBuiltins.Properties.Blocks.is(name) ||
             PomskyBuiltins.Properties.Others.is(name);
    }
  }

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
      this.variants.forEach(v -> builtins.put(v, this));
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
