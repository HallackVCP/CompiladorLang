package org.example.lang.ast.cmd;

import org.example.lang.ast.Visitor;
import org.example.lang.ast.exp.LValue;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

public record ReadCmd(LValue lvalue) implements Cmd {
    @Override public <T> T accept(Visitor<T> v) { return v.visit(this); }
}