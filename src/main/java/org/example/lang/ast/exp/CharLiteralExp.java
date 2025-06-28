package org.example.lang.ast.exp;

import org.example.lang.ast.Visitor;

public record CharLiteralExp(char value) implements Exp {
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
