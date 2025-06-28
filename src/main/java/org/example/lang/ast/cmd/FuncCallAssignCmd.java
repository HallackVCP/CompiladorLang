package org.example.lang.ast.cmd;

import org.example.lang.ast.Visitor;
import org.example.lang.ast.exp.FunCallExp;
import org.example.lang.ast.exp.LValue;

import java.util.List;

public record FuncCallAssignCmd(FunCallExp call, List<LValue> lvalues) implements Cmd {
    @Override public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
