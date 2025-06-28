package org.example.lang.interpreter.value;

import java.util.Map;
import java.util.stream.Collectors;

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

