package com.github.lppedd.idea.pomsky.settings;

import com.intellij.openapi.Disposable;
import com.intellij.ui.border.IdeaTitledBorder;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.GridBag;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * @author Edoardo Luppi
 */
class PomskySettingsComponent {
  private static final int SECTION_INDENT = 10;

  private final JBPanel<?> rootPanel;
  private final PomskyCliExecutablePanel cliExecutablePanel;
  private final JBCheckBox livePreviewCheckbox = new JBCheckBox("Live preview");

  PomskySettingsComponent(@NotNull final Disposable disposable) {
    // CLI settings
    final var cliGb = new GridBag()
        .setDefaultInsets(JBUI.insets(6))
        .setDefaultAnchor(0, GridBagConstraints.NORTHEAST)
        .setDefaultAnchor(1, GridBagConstraints.NORTHWEST)
        .setDefaultWeightX(0, 0.0)
        .setDefaultWeightX(1, 1.0);

    final var cliPanel = new JBPanel<>(new GridBagLayout());
    cliPanel.setBorder(new IdeaTitledBorder("CLI Settings", JBUI.scale(SECTION_INDENT), JBUI.emptyInsets()));

    final var versionValueLabel = new JBLabel("N/A");
    versionValueLabel.setForeground(JBUI.CurrentTheme.ContextHelp.FOREGROUND);

    cliExecutablePanel = new PomskyCliExecutablePanel(disposable);
    cliExecutablePanel.setListener(versionValueLabel::setText);

    cliPanel.add(new JBLabel("Executable path:"), cliGb.nextLine().next().insetTop(12));
    cliPanel.add(cliExecutablePanel, cliGb.next());

    final var versionLabel = new JBLabel("Version:");
    versionLabel.setForeground(JBUI.CurrentTheme.ContextHelp.FOREGROUND);

    cliPanel.add(versionLabel, cliGb.nextLine().next().insetTop(0));
    cliPanel.add(versionValueLabel, cliGb.next().insets(0, 8, 0, 0));

    // Editor settings
    final var editorGb = new GridBag()
        .setDefaultInsets(JBUI.insets(6))
        .setDefaultAnchor(GridBagConstraints.NORTHWEST)
        .setDefaultWeightX(1.0);

    final var editorPanel = new JBPanel<>(new GridBagLayout());
    editorPanel.setBorder(new IdeaTitledBorder("Editor Settings", JBUI.scale(SECTION_INDENT), JBUI.insetsTop(15)));
    editorPanel.add(livePreviewCheckbox, editorGb.nextLine().next().insetBottom(4));

    final var commentLabel = new JBLabel("Compile a file on every change");
    commentLabel.setForeground(JBUI.CurrentTheme.ContextHelp.FOREGROUND);
    editorPanel.add(commentLabel, editorGb.nextLine().next().insets(0, SECTION_INDENT + 20, 0, 0));

    // Root panel
    final var rootGb = new GridBag()
        .setDefaultFill(GridBagConstraints.HORIZONTAL)
        .setDefaultWeightX(1.0);

    rootPanel = new JBPanel<>(new GridBagLayout());
    rootPanel.add(cliPanel, rootGb.nextLine().next());
    rootPanel.add(editorPanel, rootGb.nextLine().next());

    // An empty component to fill all the remaining vertical space
    rootPanel.add(Box.createVerticalBox(), rootGb.nextLine().next().weighty(1.0));
  }

  @NotNull
  JPanel getPanel() {
    return rootPanel;
  }

  boolean isDataValid() {
    return cliExecutablePanel.isDataValid();
  }

  @Nullable
  String getCliExecutablePath() {
    return cliExecutablePanel.getCliExecutablePath();
  }

  boolean isLivePreview() {
    return livePreviewCheckbox.isSelected();
  }

  void setCliExecutablePath(@Nullable final String path) {
    cliExecutablePanel.setCliExecutablePath(path);
  }

  void setLivePreview(final boolean isLivePreview) {
    livePreviewCheckbox.setSelected(isLivePreview);
  }
}
