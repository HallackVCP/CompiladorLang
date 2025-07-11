package org.example.lang.interpreter.value;


/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

/**
 * Representa um valor do tipo inteiro.
 */
public record IntValue(int value) implements Value {
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}