package org.example.lang.ast.type;

import org.example.lang.ast.TypeNode;
import org.example.lang.ast.Visitor;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

public record BaseTypeNode(String typeName) implements TypeNode {
    @Override public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
