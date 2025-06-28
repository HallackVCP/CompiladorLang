package org.example.lang.interpreter.value;

public record NullValue() implements Value {
    @Override public String toString() { return "null"; }
}
