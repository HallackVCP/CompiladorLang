package org.example.lang.ast.type;

import org.example.lang.ast.TypeNode;
import org.example.lang.ast.Visitor;

public record BaseTypeNode(String typeName) implements TypeNode {
    @Override public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
