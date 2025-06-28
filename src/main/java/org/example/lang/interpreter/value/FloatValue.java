package org.example.lang.interpreter.value;

public record FloatValue(float value) implements Value {
    @Override public String toString() { return String.valueOf(value); }
}