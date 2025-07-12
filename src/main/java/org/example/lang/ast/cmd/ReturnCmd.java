package org.example.lang.ast.cmd;

import org.example.lang.ast.Visitor;
import org.example.lang.ast.exp.Exp;

import java.util.List;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

/**
 * Representa o comando 'return', que define os valores de retorno de uma função.
 * É seguido por uma lista de expressões separadas por vírgula.
 */
public record ReturnCmd(List<Exp> exps) implements Cmd{
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
