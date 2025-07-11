package org.example.lang.ast.cmd;

import org.example.lang.ast.Visitor;
import org.example.lang.ast.exp.Exp;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

/**
 * Representa um comando de seleção 'if', com uma ramificação 'else' opcional.
 */
public record IfCmd(Exp condition, Cmd thenBranch, Cmd elseBranch) implements Cmd {
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}