package org.example.lang.ast.exp;

import org.example.lang.ast.Visitor;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

/**
 * Representa uma expressão com um operador binário, como aritmético, lógico ou de comparação.
 */
public record BinOpExp(Exp left, String op, Exp right) implements Exp {
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}