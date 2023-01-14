package com.github.lppedd.idea.pomsky.support;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyExpressionPsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.usages.impl.rules.UsageType;
import com.intellij.usages.impl.rules.UsageTypeProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Edoardo Luppi
 */
class PomskyUsageTypeProvider implements UsageTypeProvider {
  private static final UsageType USAGE_EXPRESSIONS = new UsageType(() -> "Usages in expressions");

  @Nullable
  @Override
  public UsageType getUsageType(@NotNull final PsiElement element) {
    return element.getParent() instanceof PomskyExpressionPsiElement
        ? USAGE_EXPRESSIONS
        : null;
  }
}
