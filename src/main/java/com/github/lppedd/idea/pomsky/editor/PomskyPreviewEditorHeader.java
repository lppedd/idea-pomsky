package com.github.lppedd.idea.pomsky.editor;

import com.github.lppedd.idea.pomsky.process.PomskyRegexpFlavor;
import com.intellij.codeInsight.hint.HintUtil;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.*;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.GridBag;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * @author Edoardo Luppi
 */
class PomskyPreviewEditorHeader extends JBPanel<PomskyPreviewEditorHeader> {
  private final JBLabel infoLabel;
  private final ComboBox<PomskyRegexpFlavor> regexpFlavorComboBox;
  private final HyperlinkLabel compileHyperlink;
  private final JBLabel loadingIcon;

  PomskyPreviewEditorHeader() {
    super(new GridBagLayout());

    // TODO: pick better colors
    setBackground(EditorColorsManager.getInstance().getGlobalScheme().getColor(HintUtil.PROMOTION_PANE_KEY));
    setBorder(JBUI.Borders.merge(
        JBUI.Borders.empty(0, 7),
        new SideBorder(JBColor.PanelBackground, SideBorder.BOTTOM, JBUI.scale(2)),
        true
    ));

    // noinspection DialogTitleCapitalization
    infoLabel = new JBLabel("Compiled RegExp");
    regexpFlavorComboBox = new ComboBox<>(new EnumComboBoxModel<>(PomskyRegexpFlavor.class), JBUI.scale(110));
    compileHyperlink = new HyperlinkLabel("Compile");

    loadingIcon = new JBLabel(new AnimatedIcon.Default());
    loadingIcon.setToolTipText("Compiling...");
    loadingIcon.setVisible(false);

    setWideLayout();
    addComponentListener(new LayoutChangeComponentListener());
  }

  @NotNull
  public ComboBox<PomskyRegexpFlavor> getRegexpFlavorComboBox() {
    return regexpFlavorComboBox;
  }

  @NotNull
  public HyperlinkLabel getCompileHyperlink() {
    return compileHyperlink;
  }

  public void setLoading(final boolean isLoading) {
    loadingIcon.setVisible(isLoading);
  }

  private void setWideLayout() {
    final var gb = new GridBag().setDefaultInsets(JBUI.insets(3));
    add(infoLabel, gb.nextLine().next());
    add(regexpFlavorComboBox, gb.next().insetLeft(JBUI.scale(4)));
    add(Box.createHorizontalStrut(1), gb.next().weightx(1.0));
    add(loadingIcon, gb.next());
    add(compileHyperlink, gb.next().insetLeft(1));
  }

  private void setTightLayout() {
    final var gb = new GridBag().setDefaultInsets(JBUI.insets(2));
    add(infoLabel, gb.nextLine().next().insetTop(5));
    add(regexpFlavorComboBox, gb.nextLine().next());
    add(compileHyperlink, gb.nextLine().next().insetBottom(5));
  }

  private void removeComponents(final int n) {
    for (var i = 0; i < n; i++) {
      remove(0);
    }
  }

  private class LayoutChangeComponentListener extends ComponentAdapter {
    static final int LAYOUT_WIDE = 0;
    static final int LAYOUT_TIGHT = 1;

    int layoutType = -1;
    int lastLayoutType = LAYOUT_WIDE;

    @Override
    public void componentResized(@NotNull final ComponentEvent e) {
      final var size = e.getComponent().getSize();
      layoutType = size.width > JBUI.scale(270)
          ? LAYOUT_WIDE
          : LAYOUT_TIGHT;

      if (lastLayoutType != layoutType) {
        switch (lastLayoutType = layoutType) {
          case LAYOUT_TIGHT -> {
            removeComponents(5);
            setTightLayout();
          }
          case LAYOUT_WIDE -> {
            removeComponents(3);
            setWideLayout();
          }
        }

        revalidate();
        validate();
      }
    }
  }
}
