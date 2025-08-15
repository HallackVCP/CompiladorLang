package org.example.lang.ast.exp;

import org.example.lang.ast.TypeNode;
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
public class BinOpExp implements Exp {
    private final Exp left;
    private final String op;
    private final Exp right;
    private TypeNode type;
    public BinOpExp(Exp l, String o, Exp r) { this.left = l; this.op = o; this.right = r; }
    public Exp left() { return left; }
    public String op() { return op; }
    public Exp right() { return right; }
    @Override public void setType(TypeNode t) { this.type = t; }
    @Override public TypeNode getType() { return type; }
    @Override public <T> T accept(Visitor<T> v) { return v.visit(this); }
}