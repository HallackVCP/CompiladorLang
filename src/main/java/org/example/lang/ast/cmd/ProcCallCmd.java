package org.example.lang.ast.cmd;

import org.example.lang.ast.Visitor;
import org.example.lang.ast.exp.Exp;

import java.util.List;

public record ProcCallCmd(String name, List<Exp> args) implements Cmd {
    @Override public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
