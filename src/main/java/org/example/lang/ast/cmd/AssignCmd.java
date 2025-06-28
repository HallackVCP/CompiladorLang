package org.example.lang.ast.cmd;

import org.example.lang.ast.Visitor;
import org.example.lang.ast.exp.Exp;
import org.example.lang.ast.exp.LValue;

public record AssignCmd(LValue lvalue, Exp exp) implements Cmd {
    @Override public <T> T accept(Visitor<T> v) { return v.visit(this); }
}