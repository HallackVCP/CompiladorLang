package org.example.lang.interpreter.value;

/**
 * Representa a ausência de um valor, similar ao 'void'.
 * É o resultado de comandos ou funções que não retornam nada.
 */
public record VoidValue() implements Value {
    @Override
    public String toString() {
        return "void";
    }
}
