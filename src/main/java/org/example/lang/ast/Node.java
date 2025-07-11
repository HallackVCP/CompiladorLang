package org.example.lang.ast;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */


// Visitor Pattern
public interface Node {
    <T> T accept(Visitor<T> visitor);
}
