package org.example.lang.interpreter.value;

/**
 * Representa um valor do tipo inteiro.
 */
public record IntValue(int value) implements Value {
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}