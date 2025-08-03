package org.example.lang.ast.exp;

import org.example.lang.ast.TypeNode;
import org.example.lang.ast.Visitor;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

public class FieldAccessExp implements LValue {
    private final Exp recordExp;
    private final String fieldName;
    private TypeNode type;
    public FieldAccessExp(Exp r, String f) { this.recordExp = r; this.fieldName = f; }
    public Exp recordExp() { return recordExp; }
    public String fieldName() { return fieldName; }
    @Override public void setType(TypeNode t) { this.type = t; }
    @Override public TypeNode getType() { return type; }
    @Override public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
