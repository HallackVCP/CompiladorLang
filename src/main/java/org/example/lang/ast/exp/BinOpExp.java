package org.example.lang.ast.exp;

import org.example.lang.ast.Visitor;

/**
 * Representa uma expressão com um operador binário, como aritmético, lógico ou de comparação.
 */
public record BinOpExp(Exp left, String op, Exp right) implements Exp {
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}