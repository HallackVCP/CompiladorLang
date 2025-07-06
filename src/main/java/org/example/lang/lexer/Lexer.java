package org.example.lang.lexer;

import org.example.lang.Exception.LexerException;

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
            return getIdentifierOrKeywordToken();
        }
        if (Character.isDigit(current) || (current == '.' && Character.isDigit(peek()))) {
            return getNumberToken();
        }

        switch (current) {
            case '(': return getSpecialCharacterToken(TokenType.LPAREN, 1);
            case ')': return getSpecialCharacterToken(TokenType.RPAREN, 1);
            case '{': return getSpecialCharacterToken(TokenType.LBRACE, 1);
            case '}': return getSpecialCharacterToken(TokenType.RBRACE, 1);
            case '[': return getSpecialCharacterToken(TokenType.LBRACK, 1);
            case ']': return getSpecialCharacterToken(TokenType.RBRACK, 1);
            case ',': return getSpecialCharacterToken(TokenType.COMMA, 1);
            case ';': return getSpecialCharacterToken(TokenType.SEMI, 1);
            case '.': return getSpecialCharacterToken(TokenType.DOT, 1);
            case '+': return getSpecialCharacterToken(TokenType.PLUS, 1);
            case '-': return getSpecialCharacterToken(TokenType.MINUS, 1);
            case '*': return getSpecialCharacterToken(TokenType.STAR, 1);
            case '/': return getSpecialCharacterToken(TokenType.SLASH, 1);
            case '%': return getSpecialCharacterToken(TokenType.PERCENT, 1);
            case '>': return getSpecialCharacterToken(TokenType.RETURN_VALUES_CLOSE, 1);

            case '\'': return getCharToken();

            case ':':
                if (peek() == ':') {
                    return getSpecialCharacterToken(TokenType.DOUBLE_COLON, 2); // Gera '::' como um token único e distinto
                }
                return getSpecialCharacterToken(TokenType.COLON, 1); // Gera ':' normal

            case '<':
                return getSpecialCharacterToken(TokenType.LT, 1);

            case '=':
                if (peek() == '=') {
                    return getSpecialCharacterToken(TokenType.EQ_EQ, 2);
                }
                return getSpecialCharacterToken(TokenType.ASSIGN, 1);

            case '!':
                if (peek() == '=') {
                    return getSpecialCharacterToken(TokenType.NOT_EQ, 2);
                }
                return getSpecialCharacterToken(TokenType.NOT, 1);

            case '&':
                if (peek() == '&') {
                    return getSpecialCharacterToken(TokenType.AND, 2);
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
            } else if (current == '-' && peek() == '-') { // Comentário de linha `--`
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

    private Token getIdentifierOrKeywordToken() {
        int start = position;
        int startCol = column;
        while (position < input.length() && (Character.isLetterOrDigit(currentChar()) || currentChar() == '_')) { //Encontra posição final da palavra
            advance();
        }
        String lexeme = input.substring(start, position); //Obtém palavra
        TokenType type = KEYWORDS.getOrDefault(lexeme, TokenType.ID);

        if (type == TokenType.ID) {
            if (Character.isUpperCase(lexeme.charAt(0))) {
                type = TokenType.TYID;
            } else if (!Character.isLowerCase(lexeme.charAt(0))) {
                throw new LexerException("Erro léxico: Identificador '" + lexeme + "' deve começar com letra na linha " + line);
            }
        }
        return new Token(type, lexeme, line, startCol);
    }

    private Token getNumberToken() {
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

    private char peekNext() {
        if (position + 2 >= input.length()) return '\0';
        return input.charAt(position + 2);
    }

    // Substitua o método charLiteral inteiro por esta nova versão
    private Token getCharToken() {
        int startCol = column;
        eat('\''); // Consome a aspa inicial

        char value;
        if (currentChar() == '\\') { // Sequência de escape
            advance(); // Consome a barra invertida '\'

            if (Character.isDigit(currentChar())) { // Verifica se é um código ASCII
                if (position + 2 >= input.length()) {
                    throw new LexerException("Código ASCII incompleto na linha " + line);
                }

                // Lê os três dígitos
                String asciiCodeStr = "" + currentChar() + peek() + peekNext();

                // Avança o cursor para depois dos três dígitos
                advance(); advance(); advance();

                try {
                    int asciiCode = Integer.parseInt(asciiCodeStr);
                    value = (char) asciiCode;
                } catch (NumberFormatException e) {
                    throw new LexerException("Código ASCII inválido: " + asciiCodeStr);
                }

            } else { // Verifica outros escapes conhecidos
                value = switch (currentChar()) {
                    case 'n' -> '\n';
                    case 't' -> '\t';
                    case '\'' -> '\'';
                    case '\\' -> '\\';
                    case 'b' -> '\b'; // Adicionando backspace, 'r' para carriage return
                    case 'r' -> '\r';
                    default -> throw new LexerException("Caractere de escape inválido: \\" + currentChar());
                };
                advance(); // Consome o caractere de escape (n, t, etc.)
            }
        } else { // Caractere normal
            value = currentChar();
            advance();
        }

        eat('\''); // Consome a aspa final
        return new Token(TokenType.CHAR, String.valueOf(value), line, startCol);
    }
//    private Token getCharToken() {
//        int startCol = column;
//        eat('\'');
//        char value = currentChar();
//        if (value == '\\') { // Caractere de escape
//            advance();
//            value = switch (currentChar()) {
//                case 'n' -> '\n';
//                case 't' -> '\t';
//                case '\'' -> '\'';
//                case '\\' -> '\\';
//                default -> throw new LexerException("Caractere de escape inválido: \\" + currentChar());
//            };
//        }
//        advance();
//        eat('\'');
//        return new Token(TokenType.CHAR, String.valueOf(value), line, startCol);
//    }

    private void eat(char c) {
        if (position < input.length() && currentChar() == c) {
            advance();
        } else {
            throw new LexerException("Erro léxico: esperado '" + c + "' na linha " + line);
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

    private Token getSpecialCharacterToken(TokenType type, int length) {
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
