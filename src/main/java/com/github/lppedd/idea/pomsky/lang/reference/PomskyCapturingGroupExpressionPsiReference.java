package com.github.lppedd.idea.pomsky.lang.reference;

import com.github.lppedd.idea.pomsky.lang.psi.PomskyCapturingGroupExpressionPsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyGroupReferencePsiElement;
import com.github.lppedd.idea.pomsky.lang.psi.PomskyNamedCapturingGroupExpressionPsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Given a numbered/named capturing group reference ({@code ::2}, {@code ::groupName}),
 * resolves to that group declaration.
 *
 * @author Edoardo Luppi
 */
public class PomskyCapturingGroupExpressionPsiReference extends PsiReferenceBase<PomskyGroupReferencePsiElement> {
  public PomskyCapturingGroupExpressionPsiReference(@NotNull final PomskyGroupReferencePsiElement element) {
    super(element);
  }

  @Nullable
  @Override
  public PsiElement resolve() {
    final var groupReference = getElement();
    final var file = groupReference.getContainingFile();
    final var groupName = getValue();
    return groupReference.isNumbered()
        ? findNumberedGroupDeclaration(groupName, file)
        : findNamedGroupDeclaration(groupName, file);
  }

  @Nullable
  private PsiElement findNumberedGroupDeclaration(
      @NotNull final String name,
      @NotNull final PsiFile file) {
    final var groupIndex = Integer.parseInt(name) - 1;

    if (groupIndex < 0) {
      return null;
    }

    final var groups = PsiTreeUtil.findChildrenOfType(file, PomskyCapturingGroupExpressionPsiElement.class);
    final var groupsList = new ArrayList<>(groups);
    return groupsList.size() > groupIndex
        ? groupsList.get(groupIndex)
        : null;
  }

  @Nullable
  private PsiElement findNamedGroupDeclaration(
      @NotNull final String groupName,
      @NotNull final PsiFile file) {
    // TODO: optimize by traversing from the bottom of the file
    final var groups = PsiTreeUtil.findChildrenOfType(file, PomskyNamedCapturingGroupExpressionPsiElement.class);
    PsiElement result = null;

    for (final var group : groups) {
      if (group.getName().equals(groupName)) {
        result = group;
      }
    }

    return result;
  }
}
