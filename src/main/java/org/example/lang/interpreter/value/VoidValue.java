package org.example.lang.interpreter.value;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */



/**
 * Representa a ausência de um valor, similar ao 'void'.
 * É o resultado de comandos ou funções que não retornam nada.
 */
public record VoidValue() implements Value {
    @Override
    public String toString() {
        return "void";
    }
}
