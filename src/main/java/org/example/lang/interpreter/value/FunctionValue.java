package org.example.lang.interpreter.value;

import org.example.lang.ast.decl.FunDecl;

/**
 * Representa uma função como um valor de primeira classe.
 * Armazena a declaração da função (AST) para que possa ser executada quando chamada.
 */
public record FunctionValue(FunDecl decl) implements Value {
    @Override
    public String toString() {
        return "<function: " + decl.name() + ">";
    }
}
