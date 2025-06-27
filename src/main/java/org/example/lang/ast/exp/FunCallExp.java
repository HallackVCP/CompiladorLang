package org.example.lang.ast.exp;

import org.example.lang.ast.Visitor;

import java.util.List;

/**
 * Representa uma chamada de função como uma expressão.
 * Usa-se um índice para determinar qual dos valores de retorno da função será usado.
 */
public record FunCallExp(String name, List<Exp> args, Exp returnIndex) implements Exp {
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
