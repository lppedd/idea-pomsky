package com.github.lppedd.idea.pomsky.lang.psi;

import com.intellij.psi.NavigatablePsiElement;

/**
 * Represents a group, which can be:
 * <ul>
 *   <li>non-capturing</li>
 *   <li>non-capturing atomic</li>
 *   <li>capturing named</li>
 *   <li>capturing numbered</li>
 * </ul>
 * See inheritors.
 *
 * @author Edoardo Luppi
 */
public interface PomskyGroupExpressionPsiElement extends NavigatablePsiElement {}
