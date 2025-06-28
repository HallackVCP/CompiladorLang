package org.example.lang.ast.cmd;

import org.example.lang.ast.Visitor;
import org.example.lang.ast.exp.LValue;

public record ReadCmd(LValue lvalue) implements Cmd {
    @Override public <T> T accept(Visitor<T> v) { return v.visit(this); }
}