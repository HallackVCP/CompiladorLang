package org.example.lang.ast.cmd;

import org.example.lang.ast.Visitor;
import org.example.lang.ast.exp.Exp;

import java.util.Optional;

/**
 * Representa o comando de iteração 'iterate'.
 * Pode ter uma variável de controle opcional.
 */
public record IterateCmd(Optional<String> var, Exp collection, Cmd body) implements Cmd {
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}