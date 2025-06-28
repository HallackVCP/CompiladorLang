package org.example.lang.lexer;

public enum TokenType {
    // Palavras-chave
    IF, ELSE, RETURN, PRINT,
    DATA, ABSTRACT, ITERATE, READ,
    INT_TYPE, CHAR_TYPE, BOOL_TYPE, FLOAT_TYPE,
    TRUE, FALSE, NULL, NEW,

    // Identificadores e Literais
    ID, // var, fun10
    TYID, // Racional, Point
    INT, // 123
    FLOAT, // 3.14
    CHAR, // 'a'

    // Operadores e Símbolos
    LPAREN, RPAREN, // ( )
    LBRACE, RBRACE, // { }
    LBRACK, RBRACK, // [ ]
    COMMA, SEMI, COLON, DOUBLE_COLON, DOT, // , ; : .
    PLUS, MINUS, STAR, SLASH, PERCENT, // + - * / %
    LT, EQ_EQ, NOT_EQ, // < == !=
    AND, NOT, // && !
    ASSIGN, // =
    RETURN_VALUES_OPEN, // < (em `divmod(5,2)<q, r>`)
    RETURN_VALUES_CLOSE, // >

    // Controle
    EOF, // Fim do arquivo
    UNKNOWN // Token desconhecido
}