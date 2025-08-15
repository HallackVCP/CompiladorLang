package org.example.lang.ast.exp;

import org.example.lang.ast.TypeNode;
import org.example.lang.ast.Visitor;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

public class ArrayAccessExp implements LValue {
    private final Exp arrayExp;
    private final Exp indexExp;
    private TypeNode type;
    public ArrayAccessExp(Exp a, Exp i) { this.arrayExp = a; this.indexExp = i; }
    public Exp arrayExp() { return arrayExp; }
    public Exp indexExp() { return indexExp; }
    @Override public void setType(TypeNode t) { this.type = t; }
    @Override public TypeNode getType() { return type; }
    @Override public <T> T accept(Visitor<T> v) { return v.visit(this); }
}