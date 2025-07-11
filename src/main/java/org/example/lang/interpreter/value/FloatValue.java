package org.example.lang.interpreter.value;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

public record FloatValue(float value) implements Value {
    @Override public String toString() { return String.valueOf(value); }
}