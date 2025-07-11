package org.example.lang.interpreter;

import org.example.lang.interpreter.value.Value;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

public class Environment {
    private final Stack<Map<String, Value>> scopes = new Stack<>();

    public Environment() {
        // Escopo global
        scopes.push(new HashMap<>());
    }


    public void define(String name, Value value) {
        // A verificação agora é feita apenas no escopo atual (o mapa no topo da pilha).
        if (scopes.peek().containsKey(name)) {
            throw new RuntimeException("Erro: O nome '" + name + "' já foi definido neste escopo.");
        }
        scopes.peek().put(name, value);
    }
    public void assign(String name, Value value) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(name)) {
                scopes.get(i).put(name, value);
                return;
            }
        }
        scopes.peek().put(name, value);
    }

    public boolean isDefined(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(name)) {
                return true;
            }
        }
        return false;
    }

    public void pushScope() { scopes.push(new HashMap<>()); }
    public void popScope() { scopes.pop(); }


    public Value get(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(name)) {
                return scopes.get(i).get(name);
            }
        }
        throw new RuntimeException("Erro: variável ou função '" + name + "' não definida.");
    }
}
