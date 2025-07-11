package org.example.lang.interpreter.value;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

/**
 * Representa uma instância de um tipo de dado (registro) em tempo de execução.
 * Contém o nome do tipo e um mapa com os campos e seus respectivos valores.
 */
public record RecordValue(String typeName, Map<String, Value> fields) implements Value {
    @Override
    public String toString() {
        String fieldsStr = fields.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue().toString())
                .collect(Collectors.joining(", "));
        return typeName + " { " + fieldsStr + " }";
    }
}

