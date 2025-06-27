package org.example.lang.parser;

import org.example.lang.ast.*;
import org.example.lang.ast.cmd.*;
import org.example.lang.ast.decl.*;
import org.example.lang.ast.exp.*;
import org.example.lang.lexer.*;
import java.util.*;

public class Parser {
    private Lexer lexer;
    private Token currentToken;
    private static final Map<TokenType, Integer> PRECEDENCE = new HashMap<>();

    static {
        PRECEDENCE.put(TokenType.LT, 3); // Precedência dos operadores
        PRECEDENCE.put(TokenType.PLUS, 4);
        PRECEDENCE.put(TokenType.MINUS, 4);
        PRECEDENCE.put(TokenType.STAR, 5);
        PRECEDENCE.put(TokenType.SLASH, 5);
    }

    public Parser(String input) {
        this.lexer = new Lexer(input);
        this.currentToken = lexer.nextToken();
    }

    public Program parseProgram() {
        List<Decl> decls = new ArrayList<>();
        while (currentToken.type() != TokenType.EOF) {
            decls.add(parseDeclaration());
        }
        return new Program(decls);
    }

    private Decl parseDeclaration() {
        // Simplificação: apenas declarações de função
        return parseFunctionDeclaration();
    }

    private FunDecl parseFunctionDeclaration() {
        String name = eat(TokenType.ID).lexeme();
        eat(TokenType.LPAREN);
        List<FunDecl.Param> params = new ArrayList<>();
        if (currentToken.type() != TokenType.RPAREN) {
            // Parse params
            String paramName = eat(TokenType.ID).lexeme();
            eat(TokenType.COLON);
            String paramType = eat(TokenType.INT_TYPE).lexeme(); // Simplificado para Int
            params.add(new FunDecl.Param(paramName, paramType));
        }
        eat(TokenType.RPAREN);

        // Parse return type
        String returnType = "Void"; // Procedimento por padrão
        if (currentToken.type() == TokenType.COLON) {
            eat(TokenType.COLON);
            returnType = eat(TokenType.INT_TYPE).lexeme(); // Simplificado para um retorno Int
        }

        Cmd body = parseCommand();
        return new FunDecl(name, params, returnType, body);
    }

    private Cmd parseCommand() {
        return switch (currentToken.type()) {
            case LBRACE -> parseBlock();
            case IF -> parseIf();
            case PRINT -> parsePrint();
            case RETURN -> parseReturn();
            default -> {
                // Ambiguidade: pode ser atribuição ou chamada de procedimento.
                // A gramática para chamada de função com atribuição de retorno é `ID ( [exps] ) '<' lvalue ... '>'`
                // A gramática para chamada de procedimento é `ID ( [exps] )`
                // A gramática de atribuição é `lvalue '=' exp`
                // O exemplo `fat` não tem atribuições, então simplificamos.
                throw new RuntimeException("Comando não implementado ou inválido na linha " + currentToken.line());
            }
        };
    }

    private BlockCmd parseBlock() {
        eat(TokenType.LBRACE);
        List<Cmd> cmds = new ArrayList<>();
        while (currentToken.type() != TokenType.RBRACE) {
            cmds.add(parseCommand());
        }
        eat(TokenType.RBRACE);
        return new BlockCmd(cmds);
    }

    private IfCmd parseIf() {
        eat(TokenType.IF);
        eat(TokenType.LPAREN);
        Exp condition = parseExpression();
        eat(TokenType.RPAREN);
        Cmd thenBranch = parseCommand();
        Cmd elseBranch = null;
        if (currentToken.type() == TokenType.ELSE) {
            eat(TokenType.ELSE);
            elseBranch = parseCommand();
        }
        return new IfCmd(condition, thenBranch, elseBranch);
    }

    private PrintCmd parsePrint() {
        eat(TokenType.PRINT);
        Exp exp = parseExpression();
        eat(TokenType.SEMI);
        return new PrintCmd(exp);
    }

    private ReturnCmd parseReturn() {
        eat(TokenType.RETURN);
        Exp exp = parseExpression();
        eat(TokenType.SEMI);
        return new ReturnCmd(exp);
    }

    private Exp parseExpression() {
        return parseExpression(0);
    }

    // Algoritmo Precedence Climbing para tratar a precedência de operadores
    private Exp parseExpression(int minPrecedence) {
        Exp left = parsePrimaryExpression();

        while (isBinaryOperator(currentToken.type()) && PRECEDENCE.get(currentToken.type()) >= minPrecedence) {
            Token opToken = currentToken;
            int currentPrecedence = PRECEDENCE.get(opToken.type());
            eat(opToken.type());

            Exp right = parseExpression(currentPrecedence + 1);
            left = new BinOpExp(left, opToken.lexeme(), right);
        }

        return left;
    }

    private Exp parsePrimaryExpression() {
        Token token = currentToken;
        switch (token.type()) {
            case INT:
                eat(TokenType.INT);
                return new IntLiteralExp(Integer.parseInt(token.lexeme()));
            case ID:
                eat(TokenType.ID);
                if (currentToken.type() == TokenType.LPAREN) { // Chamada de função
                    eat(TokenType.LPAREN);
                    List<Exp> args = new ArrayList<>();
                    if (currentToken.type() != TokenType.RPAREN) {
                        args.add(parseExpression());
                    }
                    eat(TokenType.RPAREN);

                    // A chamada de função é uma expressão que precisa de um índice de retorno
                    eat(TokenType.LBRACK);
                    Exp index = parseExpression();
                    eat(TokenType.RBRACK);

                    return new FunCallExp(token.lexeme(), args, index);
                }
                return new VarAccessExp(token.lexeme());
            case LPAREN:
                eat(TokenType.LPAREN);
                Exp exp = parseExpression();
                eat(TokenType.RPAREN);
                return exp;
            default:
                throw new RuntimeException("Expressão primária inesperada: " + token.lexeme() + " na linha " + token.line());
        }
    }

    private Token eat(TokenType expected) {
        Token t = currentToken;
        if (t.type() != expected) {
            throw new RuntimeException("Erro de sintaxe: esperado " + expected + ", mas encontrou " + t.type() + " ('" + t.lexeme() + "') na linha " + t.line());
        }
        currentToken = lexer.nextToken();
        return t;
    }

    private boolean isBinaryOperator(TokenType type) {
        return PRECEDENCE.containsKey(type);
    }
}
