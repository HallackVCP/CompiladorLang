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
        KEYWORDS.put("read", TokenType.READ);
        KEYWORDS.put("iterate", TokenType.ITERATE);
        KEYWORDS.put("data", TokenType.DATA);
        KEYWORDS.put("abstract", TokenType.ABSTRACT);
        KEYWORDS.put("new", TokenType.NEW);
        KEYWORDS.put("Int", TokenType.INT_TYPE);
        KEYWORDS.put("Bool", TokenType.BOOL_TYPE);
        KEYWORDS.put("Char", TokenType.CHAR_TYPE);
        KEYWORDS.put("Float", TokenType.FLOAT_TYPE);
        KEYWORDS.put("true", TokenType.TRUE);
        KEYWORDS.put("false", TokenType.FALSE);
        KEYWORDS.put("null", TokenType.NULL);
    }

    public Lexer(String input) {
        this.input = input;
    }

    public Token nextToken() {
        skipWhitespaceAndComments();

        if (position >= input.length()) {
            return new Token(TokenType.EOF, "", line, column);
        }

        char current = currentChar();
        int startCol = column;

        if (Character.isLetter(current)) {
            return identifierOrKeyword();
        }
        if (Character.isDigit(current) || (current == '.' && Character.isDigit(peek()))) {
            return number();
        }

        switch (current) {
            case '(': return consumeAndReturn(TokenType.LPAREN, 1);
            case ')': return consumeAndReturn(TokenType.RPAREN, 1);
            case '{': return consumeAndReturn(TokenType.LBRACE, 1);
            case '}': return consumeAndReturn(TokenType.RBRACE, 1);
            case '[': return consumeAndReturn(TokenType.LBRACK, 1);
            case ']': return consumeAndReturn(TokenType.RBRACK, 1);
            case ',': return consumeAndReturn(TokenType.COMMA, 1);
            case ';': return consumeAndReturn(TokenType.SEMI, 1);
            case '.': return consumeAndReturn(TokenType.DOT, 1);
            case '+': return consumeAndReturn(TokenType.PLUS, 1);
            case '-': return consumeAndReturn(TokenType.MINUS, 1);
            case '*': return consumeAndReturn(TokenType.STAR, 1);
            case '/': return consumeAndReturn(TokenType.SLASH, 1);
            case '%': return consumeAndReturn(TokenType.PERCENT, 1);
            case '>': return consumeAndReturn(TokenType.RETURN_VALUES_CLOSE, 1);

            case '\'': return charLiteral();

            case ':':
                if (peek() == ':') {
                    return consumeAndReturn(TokenType.DOUBLE_COLON, 2); // Gera '::' como um token único e distinto
                }
                return consumeAndReturn(TokenType.COLON, 1); // Gera ':' normal

            case '<':
                // O parser diferencia `divmod(..)<...>` de `a < b` pelo contexto.
                return consumeAndReturn(TokenType.LT, 1);

            case '=':
                if (peek() == '=') {
                    return consumeAndReturn(TokenType.EQ_EQ, 2);
                }
                return consumeAndReturn(TokenType.ASSIGN, 1);

            case '!':
                if (peek() == '=') {
                    return consumeAndReturn(TokenType.NOT_EQ, 2);
                }
                return consumeAndReturn(TokenType.NOT, 1);

            case '&':
                if (peek() == '&') {
                    return consumeAndReturn(TokenType.AND, 2);
                }
                break;
        }

        advance();
        return new Token(TokenType.UNKNOWN, String.valueOf(current), line, startCol);
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
            } else if (current == '/' && peek() == '/') { // Comentário de linha `//`
                while (position < input.length() && currentChar() != '\n') {
                    position++;
                }
            } else if (current == '{' && peek() == '-') { // Comentário de bloco `{- ... -}`
                position += 2;
                while (position < input.length() - 1 && !(currentChar() == '-' && peek() == '}')) {
                    if (currentChar() == '\n') { line++; column = 1; } else { column++; }
                    position++;
                }
                if (position < input.length() - 1) { position += 2; }
            } else {
                break;
            }
        }
    }

    private Token identifierOrKeyword() {
        int start = position;
        int startCol = column;
        while (position < input.length() && (Character.isLetterOrDigit(currentChar()) || currentChar() == '_')) {
            advance();
        }
        String lexeme = input.substring(start, position);
        TokenType type = KEYWORDS.getOrDefault(lexeme, TokenType.ID);

        if (type == TokenType.ID) {
            if (Character.isUpperCase(lexeme.charAt(0))) {
                type = TokenType.TYID;
            } else if (!Character.isLowerCase(lexeme.charAt(0))) {
                throw new RuntimeException("Erro léxico: Identificador '" + lexeme + "' deve começar com letra na linha " + line);
            }
        }
        return new Token(type, lexeme, line, startCol);
    }

    private Token number() {
        int start = position;
        int startCol = column;
        boolean isFloat = false;
        while (position < input.length() && Character.isDigit(currentChar())) {
            advance();
        }
        if (position < input.length() && currentChar() == '.') {
            isFloat = true;
            advance();
            while (position < input.length() && Character.isDigit(currentChar())) {
                advance();
            }
        }
        String lexeme = input.substring(start, position);
        return new Token(isFloat ? TokenType.FLOAT : TokenType.INT, lexeme, line, startCol);
    }

    private Token charLiteral() {
        int startCol = column;
        eat('\'');
        char value = currentChar();
        if (value == '\\') { // Caractere de escape
            advance();
            value = switch (currentChar()) {
                case 'n' -> '\n';
                case 't' -> '\t';
                case '\'' -> '\'';
                case '\\' -> '\\';
                default -> throw new RuntimeException("Caractere de escape inválido: \\" + currentChar());
            };
        }
        advance();
        eat('\'');
        return new Token(TokenType.CHAR, String.valueOf(value), line, startCol);
    }

    private void eat(char c) {
        if (position < input.length() && currentChar() == c) {
            advance();
        } else {
            throw new RuntimeException("Erro léxico: esperado '" + c + "' na linha " + line);
        }
    }

    private char currentChar() {
        if (position >= input.length()) return '\0';
        return input.charAt(position);
    }

    private char peek() {
        if (position + 1 >= input.length()) return '\0';
        return input.charAt(position + 1);
    }

    private void advance() {
        if (position < input.length()) {
            if (currentChar() == '\n') { line++; column = 1; } else { column++; }
            position++;
        }
    }

    private Token consumeAndReturn(TokenType type, int length) {
        String lexeme = input.substring(position, position + length);
        int startCol = column;
        for (int i = 0; i < length; i++) advance();
        return new Token(type, lexeme, line, startCol);
    }
    /**
     * Retorna toda a string de entrada do código fonte.
     * Usado pelo Parser para a funcionalidade de 'peek'.
     */
    public String getInput() {
        return this.input;
    }

    /**
     * Retorna a posição atual do cursor no código fonte.
     * Usado pelo Parser para a funcionalidade de 'peek'.
     */
    public int getPosition() {
        return this.position;
    }
}
