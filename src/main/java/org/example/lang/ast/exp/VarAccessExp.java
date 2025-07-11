package org.example.lang.ast.exp;

import org.example.lang.ast.Visitor;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

/**
 * Representa o acesso a uma variável através de seu identificador (ID).
 */
public record VarAccessExp(String name) implements LValue {
    @Override public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
