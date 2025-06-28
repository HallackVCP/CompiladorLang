package org.example.lang.ast.cmd;

import org.example.lang.ast.Visitor;
import org.example.lang.ast.exp.Exp;

import java.util.List;

/**
 * Representa o comando 'return', que define os valores de retorno de uma função.
 * É seguido por uma lista de expressões separadas por vírgula.
 * (Esta implementação foi simplificada para um único valor de retorno para corresponder ao parser do exemplo.)
 */
public record ReturnCmd(List<Exp> exps) implements Cmd{
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
