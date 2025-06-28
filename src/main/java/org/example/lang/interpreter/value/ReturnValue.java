package org.example.lang.interpreter.value;

import java.util.List;

/**
 * Uma classe wrapper especial para sinalizar um valor de retorno.
 * Isso ajuda o interpretador a parar a execução de um bloco ou função
 * e propagar o valor de retorno para cima na pilha de chamadas.
 */
public record ReturnValue(List<Value> values) implements Value {
    @Override
    public String toString() {
        return "ReturnValue [value=" + values + "]";
    }
}