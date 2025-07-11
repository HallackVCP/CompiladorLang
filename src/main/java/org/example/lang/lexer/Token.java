package org.example.lang.lexer;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */
public record Token(TokenType type, String lexeme, int line, int column) {
}
