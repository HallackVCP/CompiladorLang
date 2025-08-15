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

public class NewExp implements Exp {
    private final TypeNode typeNode;
    private final Optional<Exp> size;
    private TypeNode type;
    public NewExp(TypeNode tn, Optional<Exp> s) { this.typeNode = tn; this.size = s; }
    public TypeNode typeNode() { return typeNode; }
    public Optional<Exp> size() { return size; }
    @Override public void setType(TypeNode t) { this.type = t; }
    @Override public TypeNode getType() { return type; }
    @Override public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
