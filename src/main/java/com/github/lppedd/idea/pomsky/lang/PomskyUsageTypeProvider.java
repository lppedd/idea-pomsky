package com.github.lppedd.idea.pomsky.lang;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyExpressionPsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.usages.impl.rules.UsageType;
import com.intellij.usages.impl.rules.UsageTypeProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Edoardo Luppi
 */
public class PomskyUsageTypeProvider implements UsageTypeProvider {
  private static final UsageType USAGE_EXPRESSIONS = new UsageType(() -> "Usage in expressions");

  @Nullable
  @Override
  public UsageType getUsageType(@NotNull final PsiElement element) {
    return element.getParent() instanceof PomskyExpressionPsiElement
        ? USAGE_EXPRESSIONS
        : null;
  }
}
