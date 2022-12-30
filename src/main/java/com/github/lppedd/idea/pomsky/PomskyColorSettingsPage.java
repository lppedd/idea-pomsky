package com.github.lppedd.idea.pomsky;

import com.github.lppedd.idea.pomsky.lang.PomskyHighlighterColors;
import com.github.lppedd.idea.pomsky.lang.PomskySyntaxHighlighter;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

/**
 * @author Edoardo Luppi
 */
public class PomskyColorSettingsPage implements ColorSettingsPage {
  private static final AttributesDescriptor[] ATTRIBUTES_DESCRIPTORS = new AttributesDescriptor[]{
      new AttributesDescriptor("Comment", PomskyHighlighterColors.COMMENT),
      new AttributesDescriptor("String", PomskyHighlighterColors.STRING),
      new AttributesDescriptor("Number", PomskyHighlighterColors.NUMBER),
      new AttributesDescriptor("Keyword", PomskyHighlighterColors.KEYWORD),
      new AttributesDescriptor("Group//Group name", PomskyHighlighterColors.GROUP),
      new AttributesDescriptor("Group//Group reference", PomskyHighlighterColors.REFERENCE),
      new AttributesDescriptor("Braces and Operators//Comma", PomskyHighlighterColors.COMMA),
      new AttributesDescriptor("Braces and Operators//Semicolon", PomskyHighlighterColors.SEMICOLON),
      new AttributesDescriptor("Braces and Operators//Negation", PomskyHighlighterColors.NEGATION),
      new AttributesDescriptor("Braces and Operators//Boundary", PomskyHighlighterColors.BOUNDARY),
      new AttributesDescriptor("Braces and Operators//Quantifier", PomskyHighlighterColors.QUANTIFIER),
      new AttributesDescriptor("Braces and Operators//Lookaround", PomskyHighlighterColors.LOOKAROUND),
  };

  @NotNull
  @Override
  public Icon getIcon() {
    return PomskyIcons.LOGO;
  }

  @NotNull
  @Override
  public SyntaxHighlighter getHighlighter() {
    return new PomskySyntaxHighlighter();
  }

  @NonNls
  @NotNull
  @Override
  public String getDemoText() {
    return "# This regex describes a cron expression\n" +
        "let sep = ^ | ' ';\n" +
        "let every = '*' | [digit]+ (',' [digit]+)*;\n\n" +
        "Start\n" +
        "(sep 'M=' :months(every))?\n" +
        "(sep 'D=' :days(every))?\n" +
        "(sep 'h=' :hours(every))?\n" +
        "(sep 'm=' :minutes(every))?\n" +
        "(sep 's=' :seconds(every))?\n" +
        "End";
  }

  @Nullable
  @Override
  public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
    return null;
  }

  @NotNull
  @Override
  public AttributesDescriptor @NotNull [] getAttributeDescriptors() {
    return ATTRIBUTES_DESCRIPTORS;
  }

  @NotNull
  @Override
  public ColorDescriptor @NotNull [] getColorDescriptors() {
    return new ColorDescriptor[0];
  }

  @NotNull
  @Override
  public String getDisplayName() {
    return "Pomsky";
  }
}
