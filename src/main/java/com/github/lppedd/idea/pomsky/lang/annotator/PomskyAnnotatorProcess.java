package com.github.lppedd.idea.pomsky.lang.annotator;

import com.github.lppedd.idea.pomsky.lang.psi.*;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Edoardo Luppi
 */
class PomskyAnnotatorProcess extends PomskyPsiElementVisitor {
  private final PsiElement element;
  private final AnnotationHolder holder;

  PomskyAnnotatorProcess(
      @NotNull final PsiElement element,
      @NotNull final AnnotationHolder holder) {
    this.element = element;
    this.holder = holder;
  }

  void execute() {
    element.accept(this);
  }

  @Override
  public void visitStringLiteral(@NotNull final PomskyStringLiteralPsiElement element) {
    // Report unclosed string literals
    final var text = element.getText();

    if (text.length() == 1 ||
        text.startsWith("'") && !text.endsWith("'") ||
        text.startsWith("\"") && !text.endsWith("\"")) {
      holder.newAnnotation(HighlightSeverity.ERROR, "String literal doesn't have a closing quote")
          .range(element.getTextRange())
          .create();
    }
  }

  @Override
  public void visitIdentifier(@NotNull final PomskyIdentifierPsiElement element) {
    if (element.getParent() instanceof PomskyVariableDeclarationPsiElement) {
      return;
    }

    // Report identifiers that refer to a non-existing variable
    final var reference = element.getReference();

    if (reference == null || reference.resolve() == null) {
      final var message = "Variable '%s' doesn't exist".formatted(element.getName());
      holder.newAnnotation(HighlightSeverity.ERROR, message)
          .range(element.getTextRange())
          .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
          .create();
    }
  }

  @Override
  public void visitGroupReference(@NotNull final PomskyGroupReferencePsiElement element) {
    // Report group references without a name or number
    if (element.getName().isEmpty()) {
      holder.newAnnotation(HighlightSeverity.ERROR, "Expected number or name of a group")
          .range(element.getTextRange())
          .create();
      return;
    }

    // Report group references that refer to a non-existing group
    final var reference = element.getReference();

    if (reference == null || reference.resolve() == null) {
      holder.newAnnotation(HighlightSeverity.ERROR, "Reference to unknown group")
          .range(element.getTextRange())
          .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
          .create();
    }
  }

  @Override
  public void visitVariableDeclaration(@NotNull final PomskyVariableDeclarationPsiElement element) {
    // Report variables declared after any other expression
    final var previousElement = PsiTreeUtil.skipSiblingsBackward(
        element,
        PsiComment.class,
        PsiWhiteSpace.class,
        PomskyVariableDeclarationPsiElement.class
    );

    if (previousElement instanceof PomskyExpressionPsiElement) {
      holder.newAnnotation(HighlightSeverity.ERROR, "Variable declaration must come before expressions")
          .range(element.getTextRange())
          .create();
    }

    // Report variables that have been defined already in the same scope (same name)
    final var previousDeclaration = getPreviousVariableDeclaration(element);

    if (previousDeclaration != null) {
      holder.newAnnotation(HighlightSeverity.ERROR, "A variable with the same name already exists in this scope")
          .withFix(new PomskyNavigateToPreviouslyDeclaredElementFix(previousDeclaration))
          .range(element.getIdentifier().getTextRange())
          .create();
    }
  }

  @Override
  public void visitNamedCapturingGroup(@NotNull final PomskyNamedCapturingGroupExpressionPsiElement element) {
    // Report named groups that have been defined already (same name)
    final var file = element.getContainingFile();
    final var processor = new PomskyFirstNamedCapturingGroupFinderProcessor(element);
    PsiTreeUtil.processElements(file, PomskyNamedCapturingGroupExpressionPsiElement.class, processor);
    final var previousDeclaration = processor.getFoundElement();

    if (previousDeclaration != null) {
      final var message = "Group name '%s' used multiple times".formatted(element.getName());
      holder.newAnnotation(HighlightSeverity.ERROR, message)
          .withFix(new PomskyNavigateToPreviouslyDeclaredElementFix(previousDeclaration))
          .range(element.getGroupName().getTextRange())
          .create();
    }
  }

  @Nullable
  private PomskyVariableDeclarationPsiElement getPreviousVariableDeclaration(
      @NotNull final PomskyVariableDeclarationPsiElement declaration) {
    PomskyVariableDeclarationPsiElement previousDeclaration = null;
    PsiElement previous = declaration;

    while ((previous = previous.getPrevSibling()) != null) {
      if (previous instanceof PomskyVariableDeclarationPsiElement other &&
          other.getName().equals(declaration.getName())) {
        previousDeclaration = other;
      }
    }

    return previousDeclaration;
  }
}
