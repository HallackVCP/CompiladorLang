package org.example.lang.ast.exp;

import org.example.lang.ast.TypeNode;
import org.example.lang.ast.Visitor;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

public class NullLiteralExp implements Exp {
    private TypeNode type;
    @Override public void setType(TypeNode t) { this.type = t; }
    @Override public TypeNode getType() { return type; }
    @Override public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
