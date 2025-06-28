package org.example.lang.interpreter.value;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Representa um array em tempo de execução.
 * Contém uma lista de valores que são os elementos do array.
 */
public record ArrayValue(List<Value> elements) implements Value {
    @Override
    public String toString() {
        return elements.stream()
                .map(Value::toString)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
