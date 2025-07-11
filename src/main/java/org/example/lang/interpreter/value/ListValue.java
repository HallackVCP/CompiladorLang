package org.example.lang.interpreter.value;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

// Usado para encapsular múltiplos valores de retorno de uma função.
public record ListValue(List<Value> values) implements Value {
    @Override
    public String toString() {
        return values.stream().map(Value::toString).collect(Collectors.joining(", ", "[", "]"));
    }
}
