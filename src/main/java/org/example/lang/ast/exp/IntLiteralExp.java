package org.example.lang.ast.exp;

import org.example.lang.ast.Visitor;

/**
 * Representa um literal inteiro na AST.
 */
public record IntLiteralExp(int value) implements Exp {
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}