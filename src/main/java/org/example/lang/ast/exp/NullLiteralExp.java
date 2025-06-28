package org.example.lang.ast.exp;

import org.example.lang.ast.Visitor;

public record NullLiteralExp() implements Exp {
    @Override public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
