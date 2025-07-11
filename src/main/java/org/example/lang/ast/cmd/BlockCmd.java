package org.example.lang.ast.cmd;

import org.example.lang.ast.Visitor;

import java.util.List;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

/**
 * Representa um bloco de comandos, delimitado por chaves.
 */
public record BlockCmd(List<Cmd> cmds) implements Cmd {
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
