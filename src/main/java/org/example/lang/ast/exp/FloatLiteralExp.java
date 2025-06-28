package org.example.lang.ast.exp;


import org.example.lang.ast.Visitor;

public record FloatLiteralExp(float value) implements Exp {
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
