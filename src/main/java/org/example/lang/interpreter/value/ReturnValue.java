package org.example.lang.interpreter.value;

import java.util.List;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

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