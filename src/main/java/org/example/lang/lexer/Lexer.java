package org.example.lang.lexer;

import java.util.Map;
import java.util.HashMap;

public class Lexer {
    private final String input;
    private int position = 0;
    private int line = 1;
    private int column = 1;
    private static final Map<String, TokenType> KEYWORDS = new HashMap<>();

    static {
        KEYWORDS.put("if", TokenType.IF);
        KEYWORDS.put("else", TokenType.ELSE);
        KEYWORDS.put("return", TokenType.RETURN);
        KEYWORDS.put("print", TokenType.PRINT);
        KEYWORDS.put("Int", TokenType.INT_TYPE);
        KEYWORDS.put("Bool", TokenType.BOOL_TYPE);
        KEYWORDS.put("Char", TokenType.CHAR_TYPE);
        KEYWORDS.put("Float", TokenType.FLOAT_TYPE);
        KEYWORDS.put("true", TokenType.TRUE);
        KEYWORDS.put("false", TokenType.FALSE);
        // Adicionar outras palavras-chave conforme necessário...
    }

    public Lexer(String input) {
        this.input = input;
    }

    public Token nextToken() {
        if (position >= input.length()) {
            return new Token(TokenType.EOF, "", line, column);
        }

        skipWhitespaceAndComments();

        if (position >= input.length()) {
            return new Token(TokenType.EOF, "", line, column);
        }

        char current = currentChar();
        int startCol = column;

        if (Character.isLetter(current)) {
            return identifierOrKeyword();
        }
        if (Character.isDigit(current)) {
            return number();
        }

        return switch (current) {
            case '(' -> consumeAndReturn(TokenType.LPAREN);
            case ')' -> consumeAndReturn(TokenType.RPAREN);
            case '{' -> consumeAndReturn(TokenType.LBRACE);
            case '}' -> consumeAndReturn(TokenType.RBRACE);
            case '[' -> consumeAndReturn(TokenType.LBRACK);
            case ']' -> consumeAndReturn(TokenType.RBRACK);
            case ',' -> consumeAndReturn(TokenType.COMMA);
            case ';' -> consumeAndReturn(TokenType.SEMI);
            case ':' -> consumeAndReturn(TokenType.COLON);
            case '.' -> consumeAndReturn(TokenType.DOT);
            case '+' -> consumeAndReturn(TokenType.PLUS);
            case '-' -> consumeAndReturn(TokenType.MINUS);
            case '*' -> consumeAndReturn(TokenType.STAR);
            case '/' -> consumeAndReturn(TokenType.SLASH);
            case '%' -> consumeAndReturn(TokenType.PERCENT);
            case '<' -> {
                // Em 'lang', '<' pode ser um operador relacional ou o início da atribuição de múltiplos retornos.
                // O parser resolverá a ambiguidade com base no contexto. O lexer só precisa tokenizar.
                // A gramática `lvalue { ',' lvalue}` '>' ajuda a diferenciar. Aqui, vamos assumir que pode ser para ambos.
                yield consumeAndReturn(TokenType.LT);
            }
            case '>' -> consumeAndReturn(TokenType.RETURN_VALUES_CLOSE);
            case '=' -> {
                if (peek() == '=') {
                    advance();
                    yield consumeAndReturn(TokenType.EQ_EQ);
                }
                yield consumeAndReturn(TokenType.ASSIGN);
            }
            case '!' -> {
                if (peek() == '=') {
                    advance();
                    yield consumeAndReturn(TokenType.NOT_EQ);
                }
                yield consumeAndReturn(TokenType.NOT);
            }
            case '&' -> {
                if (peek() == '&') {
                    advance();
                    yield consumeAndReturn(TokenType.AND);
                }
                yield new Token(TokenType.UNKNOWN, String.valueOf(current), line, startCol);
            }
            default -> {
                advance();
                yield new Token(TokenType.UNKNOWN, String.valueOf(current), line, startCol);
            }
        };
    }

    private void skipWhitespaceAndComments() {
        while (position < input.length()) {
            char current = currentChar();
            if (Character.isWhitespace(current)) {
                if (current == '\n') {
                    line++;
                    column = 1;
                } else {
                    column++;
                }
                position++;
            } else if (current == '/' && peek() == '/') { // Comentário de uma linha
                while (position < input.length() && currentChar() != '\n') {
                    position++;
                }
            } else if (current == '{' && peek() == '-') { // Comentário de múltiplas linhas
                position += 2; // Pula {-
                while (position < input.length() - 1 && !(currentChar() == '-' && peek() == '}')) {
                    if (currentChar() == '\n') {
                        line++;
                        column = 1;
                    } else {
                        column++;
                    }
                    position++;
                }
                if (position < input.length() - 1) {
                    position += 2; // Pula -}
                }
            } else {
                break;
            }
        }
    }

    private Token identifierOrKeyword() {
        int start = position;
        int startCol = column;
        // Um identificador é uma sequência de letras, dígitos e underscores
        while (position < input.length() && (Character.isLetterOrDigit(currentChar()) || currentChar() == '_')) {
            advance();
        }
        String lexeme = input.substring(start, position);
        TokenType type = KEYWORDS.getOrDefault(lexeme, TokenType.ID);

        // Se é um ID, verificar se é TYID
        if (type == TokenType.ID && Character.isUpperCase(lexeme.charAt(0))) {
            type = TokenType.TYID; // Nomes de tipo começam com letra maiúscula
        } else if (type == TokenType.ID && !Character.isLowerCase(lexeme.charAt(0))) {
            // IDs devem começar com letra minúscula
            throw new RuntimeException("Erro léxico: Identificador '" + lexeme + "' deve começar com letra minúscula na linha " + line);
        }

        return new Token(type, lexeme, line, startCol);
    }

    private Token number() {
        int start = position;
        int startCol = column;
        while (position < input.length() && Character.isDigit(currentChar())) {
            advance();
        }
        // Simplificação: não estamos tratando floats nesta entrega, conforme o exemplo.
        String lexeme = input.substring(start, position);
        return new Token(TokenType.INT, lexeme, line, startCol);
    }

    private char currentChar() {
        return input.charAt(position);
    }

    private char peek() {
        if (position + 1 >= input.length()) return '\0';
        return input.charAt(position + 1);
    }

    private void advance() {
        if (position < input.length()) {
            column++;
            position++;
        }
    }

    private Token consumeAndReturn(TokenType type) {
        String lexeme = input.substring(position, position + type.toString().length());
        int startCol = column;
        advance();
        return new Token(type, lexeme, line, startCol);
    }
}
