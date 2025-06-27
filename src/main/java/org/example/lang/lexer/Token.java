package org.example.lang.lexer;

public record Token(TokenType type, String lexeme, int line, int column) {
}
