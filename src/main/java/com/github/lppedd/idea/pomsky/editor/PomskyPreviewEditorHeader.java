package com.github.lppedd.idea.pomsky.editor;

import com.intellij.codeInsight.hint.HintUtil;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.ui.HyperlinkLabel;
import com.intellij.ui.JBColor;
import com.intellij.ui.SideBorder;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBUI;

import java.awt.*;

/**
 * @author Edoardo Luppi
 */
class PomskyPreviewEditorHeader extends JBPanel<PomskyPreviewEditorHeader> {
  public PomskyPreviewEditorHeader() {
    super(new BorderLayout(JBUI.scale(10), 0));

    setBackground(EditorColorsManager.getInstance().getGlobalScheme().getColor(HintUtil.PROMOTION_PANE_KEY));
    setBorder(JBUI.Borders.merge(
        JBUI.Borders.empty(7),
        new SideBorder(JBColor.border(), SideBorder.BOTTOM, 2),
        true
    ));

    // noinspection DialogTitleCapitalization
    add(new JBLabel("Compiled RegExp"), BorderLayout.WEST);
    add(new HyperlinkLabel("Compile"), BorderLayout.EAST);
  }
}
