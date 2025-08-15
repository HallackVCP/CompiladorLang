package org.example.lang.ast.exp;

import org.example.lang.ast.TypeNode;
import org.example.lang.ast.Visitor;

import java.util.List;

/**
 * Representa uma chamada de função como uma expressão.
 * Usa-se um índice para determinar qual dos valores de retorno da função será usado.
 */
import java.util.Optional;
/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

public class FunCallExp implements Exp {
    private final String name;
    private final List<Exp> args;
    private final Optional<Exp> returnIndex;
    private TypeNode type;
    public FunCallExp(String n, List<Exp> a, Optional<Exp> r) { this.name = n; this.args = a; this.returnIndex = r; }
    public String name() { return name; }
    public List<Exp> args() { return args; }
    public Optional<Exp> returnIndex() { return returnIndex; }
    @Override public void setType(TypeNode t) { this.type = t; }
    @Override public TypeNode getType() { return type; }
    @Override public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
