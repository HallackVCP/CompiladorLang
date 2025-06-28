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

//    public void define(String name, Value value) {
//        // Verifica se o nome já está visível em qualquer escopo.
//        if (isDefined(name)) {
//            throw new RuntimeException("Erro: O nome '" + name + "' já está definido neste escopo ou em um escopo externo.");
//        }
//        scopes.peek().put(name, value);
//    }
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
        throw new RuntimeException("Erro: Tentativa de atribuir a uma variável não definida '" + name + "'.");
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
