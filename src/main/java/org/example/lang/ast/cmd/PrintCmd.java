package org.example.lang.ast.cmd;

import org.example.lang.ast.Visitor;
import org.example.lang.ast.exp.Exp;

/**
 * Representa o comando de escrita na saída padrão, 'print', seguido por uma expressão.
 */
public record PrintCmd(Exp exp) implements Cmd {
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
