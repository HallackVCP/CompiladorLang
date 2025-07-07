grammar Lang;

@header {
    package org.example.lang.parser;
}

// Parser Rules

prog
    : def* EOF
    ;

def
    : data                      # dataDef
    | fun                       # funDef
    ;

data
    : ABSTRACT? DATA TYID LBRACE (decl | fun)* RBRACE   # dataBlock
    ;

decl
    : ID DBLCOLON type SEMI    # declStmt
    ;

fun
    : ID LPAREN params? RPAREN (COLON type (COMMA type)*)? cmd   # funDefBody
    ;

params
    : param (COMMA param)*     # paramList
    ;

param
    : ID DBLCOLON type         # paramDecl
    ;

type
    : type LBRACK RBRACK       # arrayType
    | btype                   # baseType
    ;

btype
    : INT_TYPE                 # intType
    | CHAR_TYPE                # charType
    | BOOL_TYPE                # boolType
    | FLOAT_TYPE               # floatType
    | TYID                     # customType
    ;

cmd
    : block                    # blockCmd
    | IF LPAREN exp RPAREN cmd (ELSE cmd)?   # ifCmd
    | ITERATE LPAREN itcond RPAREN cmd       # iterateCmd
    | READ lvalue SEMI                        # readCmd
    | PRINT exp SEMI                         # printCmd
    | RETURN exp (COMMA exp)* SEMI           # returnCmd
    | lvalue EQ exp SEMI                      # assignCmd
    | ID LPAREN exps? RPAREN (LT lvalue (COMMA lvalue)* GT)? SEMI  # funCallCmd
    ;

itcond
    : ID COLON exp              # idItcond
    | exp                      # expItcond
    ;

block
    : LBRACE cmd* RBRACE
    ;

exp
    : logicAndExpr
    ;

logicAndExpr
    : equalityExpr (AND equalityExpr)*
    ;

equalityExpr
    : relationalExpr ((EQEQ | NEQ) relationalExpr)*
    ;

relationalExpr
    : additiveExpr (LT additiveExpr)*
    ;

additiveExpr
    : multiplicativeExpr ((PLUS | MINUS) multiplicativeExpr)*
    ;

multiplicativeExpr
    : unaryExpr ((MUL | DIV | MOD) unaryExpr)*
    ;

unaryExpr
    : MINUS unaryExpr              # unaryMinus
    | NOT unaryExpr               # logicalNot
    | primaryExpr                 # primaryUnary
    ;

primaryExpr
    : lvalue                      # lvalueExp
    | LPAREN exp RPAREN           # parens
    | NEW type (LBRACK exp RBRACK)?  # newExp
    | ID LPAREN exps? RPAREN LBRACK exp RBRACK  # funCallIndexed
    | TRUE                        # trueLiteral
    | FALSE                       # falseLiteral
    | NULL                        # nullLiteral
    | INT                         # intLiteral
    | FLOAT                       # floatLiteral
    | CHAR                        # charLiteral
    ;

lvalue
    : ID                         # simpleLvalue
    | lvalue LBRACK exp RBRACK   # arrayAccess
    | lvalue DOT ID              # fieldAccess
    ;

exps
    : exp (COMMA exp)*           # expList
    ;

// Lexer Rules

ABSTRACT    : 'abstract';
DATA        : 'data';
IF          : 'if';
ELSE        : 'else';
ITERATE     : 'iterate';
READ        : 'read';
PRINT       : 'print';
RETURN      : 'return';
NEW         : 'new';

INT_TYPE    : 'Int';
CHAR_TYPE   : 'Char';
BOOL_TYPE   : 'Bool';
FLOAT_TYPE  : 'Float';

TRUE        : 'true';
FALSE       : 'false';
NULL        : 'null';

// Identifiers and types
ID          : [a-z] [a-zA-Z0-9_]* ;
TYID        : [A-Z] [a-zA-Z0-9_]* ;

// Literals
INT         : [0-9]+ ;
FLOAT       : [0-9]* '.' [0-9]+ ;
CHAR        : '\'' ( ESC_SEQ | ~['\\\r\n] ) '\'' ;

fragment ESC_SEQ
    : '\\' [ntrb\\']         // \n, \t, \r, \b, \\, \'
    | '\\' [0-9] [0-9] [0-9]         // ASCII code \065
    ;

// Operators and punctuation
LPAREN      : '(' ;
RPAREN      : ')' ;
LBRACK      : '[' ;
RBRACK      : ']' ;
LBRACE      : '{' ;
RBRACE      : '}' ;
SEMI        : ';' ;
COLON       : ':' ;
DBLCOLON    : '::' ;
DOT         : '.' ;
COMMA       : ',' ;
EQ          : '=' ;
LT          : '<' ;
GT          : '>' ;
EQEQ        : '==' ;
NEQ         : '!=' ;
PLUS        : '+' ;
MINUS       : '-' ;
MUL         : '*' ;
DIV         : '/' ;
MOD         : '%' ;
AND         : '&&' ;
NOT         : '!' ;

// Comments
LINE_COMMENT
    : '--' ~[\r\n]* -> skip
    ;

BLOCK_COMMENT
    : '{-' .*? '-}' -> skip
    ;

// Whitespace
WS
    : [ \t\r\n]+ -> skip
    ;
