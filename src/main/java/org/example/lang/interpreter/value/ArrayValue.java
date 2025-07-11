package org.example.lang.interpreter.value;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

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
