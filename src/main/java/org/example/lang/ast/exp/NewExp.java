package org.example.lang.ast.exp;

import org.example.lang.ast.TypeNode;
import org.example.lang.ast.Visitor;

import java.util.Optional;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

public record NewExp(TypeNode type, Optional<Exp> size) implements Exp {
    @Override public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
