package org.example.lang.ast.exp;

import org.example.lang.ast.TypeNode;
import org.example.lang.ast.Visitor;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

public class UnaryExp implements Exp {
    private final String op;
    private final Exp exp;
    private TypeNode type;
    public UnaryExp(String o, Exp e) { this.op = o; this.exp = e; }
    public String op() { return op; }
    public Exp exp() { return exp; }
    @Override public void setType(TypeNode t) { this.type = t; }
    @Override public TypeNode getType() { return type; }
    @Override public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
