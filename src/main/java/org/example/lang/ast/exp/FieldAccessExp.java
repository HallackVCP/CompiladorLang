package org.example.lang.ast.exp;

import org.example.lang.ast.Visitor;

public record FieldAccessExp(Exp recordExp, String fieldName) implements LValue {
    @Override public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
