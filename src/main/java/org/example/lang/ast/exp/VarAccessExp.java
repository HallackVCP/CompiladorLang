package org.example.lang.ast.exp;

import org.example.lang.ast.Visitor;

/**
 * Representa o acesso a uma variável através de seu identificador (ID).
 */
public record VarAccessExp(String name) implements Exp {
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
