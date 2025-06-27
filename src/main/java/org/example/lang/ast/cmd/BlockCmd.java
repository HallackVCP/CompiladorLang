package org.example.lang.ast.cmd;

import org.example.lang.ast.Visitor;

import java.util.List;

/**
 * Representa um bloco de comandos, delimitado por chaves.
 */
public record BlockCmd(List<Cmd> cmds) implements Cmd {
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
