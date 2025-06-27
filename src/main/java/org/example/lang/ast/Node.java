package org.example.lang.ast;

// Visitor Pattern
public interface Node {
    <T> T accept(Visitor<T> visitor);
}
