package org.example.lang.interpreter.value;

/**
 * Uma classe wrapper especial para sinalizar um valor de retorno.
 * Isso ajuda o interpretador a parar a execução de um bloco ou função
 * e propagar o valor de retorno para cima na pilha de chamadas.
 */
public record ReturnValue(Value value) implements Value {
    @Override
    public String toString() {
        return "ReturnValue [value=" + value + "]";
    }
}