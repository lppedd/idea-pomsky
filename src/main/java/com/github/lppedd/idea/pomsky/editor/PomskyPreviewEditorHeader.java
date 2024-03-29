package com.github.lppedd.idea.pomsky.editor;

import com.github.lppedd.idea.pomsky.Workaround;
import com.github.lppedd.idea.pomsky.process.PomskyRegexpFlavor;
import com.intellij.codeInsight.hint.HintUtil;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.*;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.EmptyIcon;
import com.intellij.util.ui.GridBag;
import com.intellij.util.ui.JBInsets;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author Edoardo Luppi
 */
class PomskyPreviewEditorHeader extends JBPanel<PomskyPreviewEditorHeader> {
  private final Collection<Consumer<PomskyRegexpFlavor>> compileListeners = new ArrayList<>(16);
  private final Collection<Consumer<PomskyRegexpFlavor>> regexpFlavorListeners = new ArrayList<>(16);

  private final JBLabel infoLabel;
  private final ComboBox<PomskyRegexpFlavor> regexpFlavorComboBox;
  private final JBLabel busyIconLabel;
  private final HyperlinkLabel compileHyperlink;

  PomskyPreviewEditorHeader() {
    super(new GridBagLayout());

    // TODO: pick better colors
    final var colorsManager = EditorColorsManager.getInstance();
    setBackground(colorsManager.getGlobalScheme().getColor(HintUtil.PROMOTION_PANE_KEY));
    setBorder(JBUI.Borders.merge(
        JBUI.Borders.empty(0, 7),
        new SideBorder(JBColor.PanelBackground, SideBorder.BOTTOM, JBUI.scale(2)),
        true
    ));

    // noinspection DialogTitleCapitalization
    infoLabel = new JBLabel("Compiled RegExp");
    regexpFlavorComboBox = new ComboBox<>(new EnumComboBoxModel<>(PomskyRegexpFlavor.class), JBUI.scale(105));
    regexpFlavorComboBox.addItemListener(e -> {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        notifyRegexpFlavorListeners((PomskyRegexpFlavor) e.getItem());
      }
    });

    busyIconLabel = new JBLabel(EmptyIcon.create(getBusyIcon()));
    compileHyperlink = new HyperlinkLabel("Compile");
    compileHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
      @Override
      protected void hyperlinkActivated(final @NotNull HyperlinkEvent e) {
        notifyCompileListeners(regexpFlavorComboBox.getItem());
      }
    });

    setWideLayout();
    addComponentListener(new LayoutChangeComponentListener());
  }

  @NotNull
  ComboBox<PomskyRegexpFlavor> getRegexpFlavorComboBox() {
    return regexpFlavorComboBox;
  }

  @NotNull
  HyperlinkLabel getCompileHyperlink() {
    return compileHyperlink;
  }

  @NotNull
  PomskyRegexpFlavor getRegexpFlavor() {
    return regexpFlavorComboBox.getItem();
  }

  void setRegexpFlavor(@NotNull final PomskyRegexpFlavor flavor) {
    regexpFlavorComboBox.setItem(flavor);
  }

  void setCompileEnabled(final boolean isEnabled, @Nullable final String tooltip) {
    compileHyperlink.setEnabled(isEnabled);
    compileHyperlink.setToolTipText(tooltip);
  }

  void setCompileLoading(final boolean isLoading) {
    if (isLoading) {
      busyIconLabel.setIcon(getBusyIcon());
      busyIconLabel.setToolTipText("Compiling...");
    } else {
      busyIconLabel.setIcon(EmptyIcon.create(getBusyIcon()));
      busyIconLabel.setToolTipText(null);
    }
  }

  void addCompileListener(@NotNull final Consumer<PomskyRegexpFlavor> listener) {
    compileListeners.add(listener);
  }

  void addRegexpFlavorListener(@NotNull final Consumer<PomskyRegexpFlavor> listener) {
    regexpFlavorListeners.add(listener);
  }

  @Override
  public void updateUI() {
    super.updateUI();

    final var colorsManager = EditorColorsManager.getInstance();
    setBackground(colorsManager.getGlobalScheme().getColor(HintUtil.PROMOTION_PANE_KEY));

    if (busyIconLabel != null) {
      updateUIDimensions();
    }
  }

  private void notifyCompileListeners(@NotNull final PomskyRegexpFlavor flavor) {
    for (final var listener : compileListeners) {
      listener.accept(flavor);
    }
  }

  private void notifyRegexpFlavorListeners(@NotNull final PomskyRegexpFlavor flavor) {
    for (final var listener : regexpFlavorListeners) {
      listener.accept(flavor);
    }
  }

  @NotNull
  @Workaround
  private Icon getBusyIcon() {
    try {
      // We need a fresh copy of the frames for the current scaling
      final var getDefaultFrames = AnimatedIcon.Default.class.getDeclaredMethod("getDefaultFrames");
      getDefaultFrames.trySetAccessible();
      return new AnimatedIcon((AnimatedIcon.Frame[]) getDefaultFrames.invoke(null));
    } catch (final Exception e) {
      // In this case the size of the icon won't be affected by scale changes
      return AnimatedIcon.Default.INSTANCE;
    }
  }

  private void updateUIDimensions() {
    regexpFlavorComboBox.setMinimumAndPreferredWidth(JBUI.scale(105));

    final var layout = (GridBagLayout) getLayout();
    updateConstraints(layout, infoLabel);
    updateConstraints(layout, regexpFlavorComboBox);
    updateConstraints(layout, busyIconLabel);
    updateConstraints(layout, compileHyperlink);
  }

  private void updateConstraints(
      @NotNull final GridBagLayout layout,
      @NotNull final Component component) {
    final var constraints = layout.getConstraints(component);
    final var insets = (JBInsets) constraints.insets;
    final var unscaled = insets.getUnscaled();
    constraints.insets = JBUI.insets(unscaled);
    layout.setConstraints(component, constraints);
  }

  private void setWideLayout() {
    final var gb = new GridBag().setDefaultInsets(JBUI.insets(3));
    add(infoLabel, gb.nextLine().next().insets(3, 1, 3, 3));
    add(regexpFlavorComboBox, gb.next());
    add(Box.createHorizontalStrut(0), gb.next().weightx(1.0));
    add(busyIconLabel, gb.next().insets(3, 0, 3, 3));
    add(compileHyperlink, gb.next().insets(3, 1, 3, 3));
  }

  private void setTightLayout() {
    final var gb = new GridBag().setDefaultInsets(JBUI.insets(2));
    add(infoLabel, gb.nextLine().next().insets(5, 2, 2, 2));
    add(regexpFlavorComboBox, gb.nextLine().next());
    add(compileHyperlink, gb.nextLine().next().insets(2, 2, 5, 2));
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
