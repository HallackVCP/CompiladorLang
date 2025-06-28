package org.example.lang.interpreter.value;

public record CharValue(char value) implements Value {
    @Override public String toString() { return String.valueOf(value); }
}
