package com.github.lppedd.idea.pomsky.settings;

import com.intellij.openapi.Disposable;
import com.intellij.ui.border.IdeaTitledBorder;
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
  private final JBPanel<?> rootPanel;
  private final PomskyCliExecutablePanel cliExecutablePanel;

  PomskySettingsComponent(@NotNull final Disposable disposable) {
    // General settings
    final var generalSettingsPanel = new JBPanel<>(new GridBagLayout());
    final var border = new IdeaTitledBorder("General Settings", JBUI.scale(15), JBUI.emptyInsets());
    generalSettingsPanel.setBorder(border);

    final var gb = new GridBag()
        .setDefaultInsets(JBUI.insets(6))
        .setDefaultAnchor(0, GridBagConstraints.NORTHEAST)
        .setDefaultAnchor(1, GridBagConstraints.NORTHWEST)
        .setDefaultWeightX(0, 0.0)
        .setDefaultWeightX(1, 1.0);

    final var versionLabel = new JBLabel("Pomsky version:");
    versionLabel.setForeground(JBUI.CurrentTheme.ContextHelp.FOREGROUND);

    generalSettingsPanel.add(versionLabel, gb.nextLine().next());

    final var versionValueLabel = new JBLabel("N/A");
    versionValueLabel.setForeground(JBUI.CurrentTheme.ContextHelp.FOREGROUND);

    generalSettingsPanel.add(versionValueLabel, gb.next().insetLeft(8));
    generalSettingsPanel.add(new JBLabel("Executable path:"), gb.nextLine().next().insetTop(12));

    cliExecutablePanel = new PomskyCliExecutablePanel(disposable);
    cliExecutablePanel.setListener(versionValueLabel::setText);

    generalSettingsPanel.add(cliExecutablePanel, gb.next());

    // Root panel
    rootPanel = new JBPanel<>(new BorderLayout());
    rootPanel.add(generalSettingsPanel, BorderLayout.NORTH);
  }

  @NotNull
  JPanel getPanel() {
    return rootPanel;
  }

  @Nullable
  String getCliExecutablePath() {
    return cliExecutablePanel.getCliExecutablePath();
  }

  void setCliExecutablePath(@Nullable final String path) {
    cliExecutablePanel.setCliExecutablePath(path);
  }

  boolean isDataValid() {
    return cliExecutablePanel.isDataValid();
  }
}
