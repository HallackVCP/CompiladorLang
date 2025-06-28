package org.example.lang.ast.type;

import org.example.lang.ast.TypeNode;
import org.example.lang.ast.Visitor;

public record ArrayTypeNode(TypeNode elementType) implements TypeNode {
    @Override public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
