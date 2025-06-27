package org.example.lang.ast.cmd;

import org.example.lang.ast.Visitor;
import org.example.lang.ast.exp.Exp;

/**
 * Representa um comando de seleção 'if', com uma ramificação 'else' opcional.
 */
public record IfCmd(Exp condition, Cmd thenBranch, Cmd elseBranch) implements Cmd {
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}