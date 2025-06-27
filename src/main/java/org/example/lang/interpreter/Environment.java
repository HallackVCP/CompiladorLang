package org.example.lang.interpreter;

import org.example.lang.interpreter.value.Value;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Environment {
    private final Stack<Map<String, Value>> scopes = new Stack<>();

    public Environment() {
        // Escopo global
        scopes.push(new HashMap<>());
    }

    public void pushScope() { scopes.push(new HashMap<>()); }
    public void popScope() { scopes.pop(); }

    public void define(String name, Value value) {
        scopes.peek().put(name, value);
    }

    public Value get(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(name)) {
                return scopes.get(i).get(name);
            }
        }
        throw new RuntimeException("Erro: variável ou função '" + name + "' não definida.");
    }
}
