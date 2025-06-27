package org.example.lang.interpreter.value;

/**
 * Representa um valor do tipo booleano.
 */
public record BoolValue(boolean value) implements Value {
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}