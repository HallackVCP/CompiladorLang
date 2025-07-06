package org.example.lang.parser;

import org.example.lang.Exception.ParserException;
import org.example.lang.ast.*;
import org.example.lang.ast.cmd.*;
import org.example.lang.ast.decl.*;
import org.example.lang.ast.exp.*;
import org.example.lang.ast.type.ArrayTypeNode;
import org.example.lang.ast.type.BaseTypeNode;
import org.example.lang.lexer.*;
import java.util.*;

public class Parser {
    private Lexer lexer;
    private Token currentToken;
//    private static final Map<TokenType, Integer> PRECEDENCE = new HashMap<>();
    private static final Map<TokenType, OpInfo> OPERATOR_INFO = new HashMap<>();

    static {
//        PRECEDENCE.put(TokenType.AND, 1);
//        PRECEDENCE.put(TokenType.EQ_EQ, 2);
//        PRECEDENCE.put(TokenType.NOT_EQ, 2);
//        PRECEDENCE.put(TokenType.LT, 3);
//        PRECEDENCE.put(TokenType.PLUS, 4);
//        PRECEDENCE.put(TokenType.MINUS, 4);
//        PRECEDENCE.put(TokenType.STAR, 5);
//        PRECEDENCE.put(TokenType.SLASH, 5);
//        PRECEDENCE.put(TokenType.PERCENT, 5);
        // Nível 1: Conjunção (esquerda)
        OPERATOR_INFO.put(TokenType.AND, new OpInfo(1, Associativity.LEFT));
        // Nível 2: Igualdade (esquerda)
        OPERATOR_INFO.put(TokenType.EQ_EQ, new OpInfo(2, Associativity.LEFT));
        OPERATOR_INFO.put(TokenType.NOT_EQ, new OpInfo(2, Associativity.LEFT));
        // Nível 3: Relacional (não associativo)
        OPERATOR_INFO.put(TokenType.LT, new OpInfo(3, Associativity.NON_ASSOCIATIVE));
        // Nível 4: Adição/Subtração (esquerda)
        OPERATOR_INFO.put(TokenType.PLUS, new OpInfo(4, Associativity.LEFT));
        OPERATOR_INFO.put(TokenType.MINUS, new OpInfo(4, Associativity.LEFT));
        // Nível 5: Multiplicação/Divisão (esquerda)
        OPERATOR_INFO.put(TokenType.STAR, new OpInfo(5, Associativity.LEFT));
        OPERATOR_INFO.put(TokenType.SLASH, new OpInfo(5, Associativity.LEFT));
        OPERATOR_INFO.put(TokenType.PERCENT, new OpInfo(5, Associativity.LEFT));
    }

    public Parser(String input) {
        this.lexer = new Lexer(input);
        this.currentToken = lexer.nextToken();
    }

    private Token eat(TokenType expected) { //Verifica se o token atual é o esperado e chama nextToken() para atualizar o token
        Token t = currentToken;
        if (t.type() != expected) {
            throw new ParserException("Erro de sintaxe: esperado " + expected + ", mas encontrou " + t.type() + " ('" + t.lexeme() + "') na linha " + t.line());
        }
        currentToken = lexer.nextToken();
        return t;
    }

    public Program parseProgram() {
        List<Decl> decls = new ArrayList<>();
        while (currentToken.type() != TokenType.EOF) {
            decls.add(parseDeclaration());
        }
        return new Program(decls);
    }

    // --- Métodos de Parse de Declarações ---

    private Decl parseDeclaration() {
        if (currentToken.type() == TokenType.ABSTRACT || currentToken.type() == TokenType.DATA) {
            return parseDataDeclaration();
        }
        return parseFunctionDeclaration();
    }

    private DataDecl parseDataDeclaration() {
        boolean isAbstract = false;
        if (currentToken.type() == TokenType.ABSTRACT) {
            eat(TokenType.ABSTRACT);
            isAbstract = true;
        }
        eat(TokenType.DATA);
        String name = eat(TokenType.TYID).lexeme();
        eat(TokenType.LBRACE);

        List<DataDecl.Field> fields = new ArrayList<>();
        while (currentToken.type() == TokenType.ID && peek().type() == TokenType.DOUBLE_COLON) { // Pode usar peek() para checar
            String fieldName = eat(TokenType.ID).lexeme();
            // Substitua as duas linhas 'eat(TokenType.COLON)' por esta única linha:
            eat(TokenType.DOUBLE_COLON);
            String fieldType = parseTypeNameAsString();
            eat(TokenType.SEMI);
            fields.add(new DataDecl.Field(fieldName, fieldType));
        }

        List<FunDecl> functions = new ArrayList<>();
        while (currentToken.type() != TokenType.RBRACE) {
            functions.add(parseFunctionDeclaration());
        }
        eat(TokenType.RBRACE);
        return new DataDecl(isAbstract, name, fields, functions);
    }

    private FunDecl parseFunctionDeclaration() {
        String name = eat(TokenType.ID).lexeme();
        eat(TokenType.LPAREN);
        List<FunDecl.Param> params = new ArrayList<>();
        if (currentToken.type() != TokenType.RPAREN) {
            do { //Lê os parâmetros da função
                String paramName = eat(TokenType.ID).lexeme();
                eat(TokenType.DOUBLE_COLON);
                String paramType = parseTypeNameAsString();
                params.add(new FunDecl.Param(paramName, paramType));
                if (currentToken.type() == TokenType.COMMA) eat(TokenType.COMMA); else break;
            } while (true);
        }
        eat(TokenType.RPAREN);

        List<String> returnTypes = new ArrayList<>();
        if (currentToken.type() == TokenType.COLON) {
            //Obtém os tipos de retorno da função
            eat(TokenType.COLON);
            do {
                returnTypes.add(parseTypeNameAsString());
                if (currentToken.type() == TokenType.COMMA) eat(TokenType.COMMA); else break;
            } while (true);
        }

        Cmd body = parseBlock();
        return new FunDecl(name, params, returnTypes, body);
    }

    // --- Métodos de Parse de Comandos ---

    private Cmd parseCommand() {
        return switch (currentToken.type()) {
            case LBRACE -> parseBlock();
            case IF -> parseIf();
            case ELSE -> parseElse();
            case PRINT -> parsePrint();
            case RETURN -> parseReturn();
            case ITERATE -> parseIterate();
            case READ -> parseRead();
            case ID ->{
                LValue lvalue = parseLValue();
                if (lvalue instanceof VarAccessExp && currentToken.type() == TokenType.LPAREN) yield parseCall(lvalue);
                else yield  parseAssignment(lvalue);
            }
            default -> throw new ParserException("Comando inválido '" + currentToken.lexeme() + "' na linha " + currentToken.line());
        };
    }

    private BlockCmd parseBlock() {
        eat(TokenType.LBRACE);
        List<Cmd> cmds = new ArrayList<>();
        while (currentToken.type() != TokenType.RBRACE && currentToken.type() != TokenType.EOF) {
            cmds.add(parseCommand());
        }
        eat(TokenType.RBRACE);
        return new BlockCmd(cmds);
    }

    private IfCmd parseIf() {
        eat(TokenType.IF);
        eat(TokenType.LPAREN);
        Exp condition = parseExpression(); //TODO: CHECK THIS METHOD
        eat(TokenType.RPAREN);
        Cmd thenBranch = parseCommand();
        Cmd elseBranch = null;
        if (currentToken.type() == TokenType.ELSE) {
            eat(TokenType.ELSE);
            elseBranch = parseCommand();
        }
        return new IfCmd(condition, thenBranch, elseBranch);
    }

    private Cmd parseElse(){
        eat(TokenType.ELSE);
        return parseBlock();
    }

    private IterateCmd parseIterate() {
        eat(TokenType.ITERATE);
        eat(TokenType.LPAREN);
        Optional<String> var = Optional.empty();
        Exp collection;
        if (peek().type() == TokenType.COLON) {
            var = Optional.of(eat(TokenType.ID).lexeme());
            eat(TokenType.COLON);
            collection = parseExpression();
        } else {
            collection = parseExpression();
        }
        eat(TokenType.RPAREN);
        Cmd body = parseCommand();
        return new IterateCmd(var, collection, body);
    }

    private ReadCmd parseRead() {
        eat(TokenType.READ);
        LValue lvalue = parseLValue();
        eat(TokenType.SEMI);
        return new ReadCmd(lvalue);
    }

    private PrintCmd parsePrint() {
        eat(TokenType.PRINT);
        Exp exp = parseExpression();
        eat(TokenType.SEMI);
        return new PrintCmd(exp);
    }

    private ReturnCmd parseReturn() {
        eat(TokenType.RETURN);
        List<Exp> exps = parseExpressionList();
        eat(TokenType.SEMI);
        return new ReturnCmd(exps);
    }

    private Cmd parseAssignment(LValue lvalue){
        eat(TokenType.ASSIGN);
        Exp exp = parseExpression();//TODO: CHECK THIS METHOD
        eat(TokenType.SEMI);
        return new AssignCmd(lvalue, exp);
    }

    private Cmd parseCall(LValue lvalue){

        // É uma chamada de função ou procedimento
        String funcName = ((VarAccessExp)lvalue).name();
        eat(TokenType.LPAREN);
        List<Exp> args = parseExpressionList();
        eat(TokenType.RPAREN);

        if (currentToken.type() == TokenType.LT) { // Atribuição de retorno: f(..)<v1,v2>;
            eat(TokenType.LT);
            List<LValue> assignTo = new ArrayList<>();
            if (currentToken.type() != TokenType.RETURN_VALUES_CLOSE) {
                do {
                    assignTo.add(parseLValue());
                    if (currentToken.type() == TokenType.COMMA) eat(TokenType.COMMA); else break;
                } while (true);
            }
            eat(TokenType.RETURN_VALUES_CLOSE);
            eat(TokenType.SEMI);
            FunCallExp call = new FunCallExp(funcName, args, Optional.empty());
            return new FuncCallAssignCmd(call, assignTo);
        } else { // Chamada de procedimento: f(...);
            eat(TokenType.SEMI);
            return new ProcCallCmd(funcName, args);
        }
    }

    // --- Métodos de Parse de Expressões e Tipos ---

    private Exp parseExpression() { return parseExpression(0); }

//    private Exp parseExpression(int minPrecedence) {
//        Exp left;
//        if (currentToken.type() == TokenType.NOT || currentToken.type() == TokenType.MINUS) {
//            Token op = eat(currentToken.type());
//            Exp exp = parseExpression(6); // Precedência de unários
//            left = new UnaryExp(op.lexeme(), exp);
//        } else {
//            left = parsePrimaryExpression();
//        }
//
//        while (PRECEDENCE.containsKey(currentToken.type()) && PRECEDENCE.get(currentToken.type()) >= minPrecedence) {
//            Token opToken = eat(currentToken.type());
//            int currentPrecedence = PRECEDENCE.get(opToken.type());
//            // Associatividade à direita (não temos, mas seria `currentPrecedence`)
//            Exp right = parseExpression(currentPrecedence + 1);
//            left = new BinOpExp(left, opToken.lexeme(), right);
//        }
//        return left;
//    }
    private Exp parseExpression(int minPrecedence) {
        Exp left;
        if (currentToken.type() == TokenType.NOT || currentToken.type() == TokenType.MINUS) {
            Token op = eat(currentToken.type());
            Exp exp = parseExpression(6); // Precedência de unários
            left = new UnaryExp(op.lexeme(), exp);
        } else {
            left = parsePrimaryExpression();
        }

        while (isBinaryOperator(currentToken.type()) && OPERATOR_INFO.get(currentToken.type()).precedence() >= minPrecedence) {
            Token opToken = eat(currentToken.type());
            OpInfo opInfo = OPERATOR_INFO.get(opToken.type());
            int currentPrecedence = opInfo.precedence();

            // Para associatividade à DIREITA, a recursão usa a MESMA precedência.
            // Para associatividade à ESQUERDA e NÃO-ASSOCIATIVA, a precedência da recursão
            // aumenta para evitar que operadores de mesma precedência sejam agrupados à direita.
            int nextMinPrecedence = (opInfo.assoc() == Associativity.RIGHT)
                    ? currentPrecedence
                    : currentPrecedence + 1;

            Exp right = parseExpression(nextMinPrecedence);

            // Agora, construímos o nó da árvore para a operação atual.
            left = new BinOpExp(left, opToken.lexeme(), right);

            // VERIFICAÇÃO FINAL PARA NÃO-ASSOCIATIVIDADE
            // Esta verificação acontece DEPOIS de construir o nó `left`, quando o `currentToken`
            // já é o próximo operador na fila, permitindo a comparação correta.
            if (opInfo.assoc() == Associativity.NON_ASSOCIATIVE) {
                if (isBinaryOperator(currentToken.type()) && OPERATOR_INFO.get(currentToken.type()).precedence() == currentPrecedence) {
                    throw new ParserException("Erro de sintaxe: O operador '" + opToken.lexeme() + "' não é associativo e não pode ser encadeado.");
                }
            }
        }

        return left;
    }
    private boolean isBinaryOperator(TokenType type) {
        return OPERATOR_INFO.containsKey(type);
    }

    private Exp parsePrimaryExpression() {
        Token token = currentToken;
        switch (token.type()) {
            case INT:
                eat(TokenType.INT);
                return new IntLiteralExp(Integer.parseInt(token.lexeme()));
            case FLOAT:
                eat(TokenType.FLOAT);
                return new FloatLiteralExp(Float.parseFloat(token.lexeme()));
            case CHAR:
                eat(TokenType.CHAR);
                return new CharLiteralExp(token.lexeme().charAt(0));
            case TRUE:
                eat(TokenType.TRUE);
                return new BoolLiteralExp(true);
            case FALSE:
                eat(TokenType.FALSE);
                return new BoolLiteralExp(false);
            case NULL:
                eat(TokenType.NULL);
                return new NullLiteralExp();
            case NEW:
                return parseNewExpression();
            case LPAREN:
                eat(TokenType.LPAREN);
                Exp exp = parseExpression();
                eat(TokenType.RPAREN);
                return exp;
            case ID:
                // CORREÇÃO: Verificamos o próximo token para resolver a ambiguidade.
                // Se for '(', é uma chamada de função. Senão, é um lvalue.
                if (peek().type() == TokenType.LPAREN) {
                    return parseFunctionCallExpression();
                } else {
                    return parseLValue();
                }
            default:
                throw new ParserException("Expressão primária inesperada: " + token.lexeme() + " na linha " + token.line());
        }
    }

    private FunCallExp parseFunctionCallExpression() {
        String idName = eat(TokenType.ID).lexeme();
        eat(TokenType.LPAREN);
        List<Exp> args = parseExpressionList();
        eat(TokenType.RPAREN);
        Optional<Exp> returnIndex = Optional.empty();
        if (currentToken.type() == TokenType.LBRACK) {
            eat(TokenType.LBRACK);
            returnIndex = Optional.of(parseExpression());
            eat(TokenType.RBRACK);
        }
        return new FunCallExp(idName, args, returnIndex);
    }

//    private NewExp parseNewExpression() {
//        eat(TokenType.NEW);
//        TypeNode type = parseTypeNode();
//        Optional<Exp> size = Optional.empty();
//        if (currentToken.type() == TokenType.LBRACK) {
//            eat(TokenType.LBRACK);
//            size = Optional.of(parseExpression());
//            eat(TokenType.RBRACK);
//        }
//        return new NewExp(type, size);
//    }
//    private NewExp parseNewExpression() {
//        eat(TokenType.NEW);
//        // Primeiro, analisamos apenas o tipo base (ex: Int, Ponto, etc.)
//        TypeNode baseType = parseBaseTypeNode();
//        //TypeNode baseType = parseTypeNode();
//
//        // Em seguida, verificamos se é uma alocação de array com tamanho
//        if (currentToken.type() == TokenType.LBRACK) {
//            eat(TokenType.LBRACK);
//            Optional<Exp> size = Optional.of(parseExpression());
//            eat(TokenType.RBRACK);
//
//            // O tipo final é um tipo de array
//            TypeNode arrayType = new ArrayTypeNode(baseType);
//            return new NewExp(arrayType, size);
//        } else {
//            // Se não houver colchetes, é uma alocação de registro (ex: new Ponto)
//            return new NewExp(baseType, Optional.empty());
//        }
//    }
    private NewExp parseNewExpression() {
        eat(TokenType.NEW);

        // 1. Primeiro, analisamos o tipo base (ex: Char, Int, Ponto).
        TypeNode type = parseBaseTypeNode();

        // 2. Em seguida, contamos e consumimos as dimensões de tipo vazias ('[]').
        // Para `new Char[][][nl]`, isso irá consumir os dois primeiros pares de colchetes.
        while (currentToken.type() == TokenType.LBRACK && peek().type() == TokenType.RBRACK) {
            eat(TokenType.LBRACK);
            eat(TokenType.RBRACK);
            type = new ArrayTypeNode(type);
        }

        // 3. Finalmente, verificamos a dimensão de alocação, que contém uma expressão de tamanho.
        // Para `new Char[][][nl]`, esta é a parte `[nl]`.
        if (currentToken.type() == TokenType.LBRACK) {
            eat(TokenType.LBRACK);
            Optional<Exp> size = Optional.of(parseExpression());
            eat(TokenType.RBRACK);

            // O tipo final do objeto criado é um array do tipo que acabamos de analisar.
            TypeNode finalType = new ArrayTypeNode(type);
            return new NewExp(finalType, size);
        } else {
            // Se não houver colchetes de tamanho, é uma alocação de registro (ex: new Ponto).
            return new NewExp(type, Optional.empty());
        }
    }

    private BaseTypeNode parseBaseTypeNode() {
        Token t = currentToken;
        return switch (t.type()) {
            case INT_TYPE -> new BaseTypeNode(eat(TokenType.INT_TYPE).lexeme());
            case BOOL_TYPE -> new BaseTypeNode(eat(TokenType.BOOL_TYPE).lexeme());
            case CHAR_TYPE -> new BaseTypeNode(eat(TokenType.CHAR_TYPE).lexeme());
            case FLOAT_TYPE -> new BaseTypeNode(eat(TokenType.FLOAT_TYPE).lexeme());
            case TYID -> new BaseTypeNode(eat(TokenType.TYID).lexeme());
            default -> throw new ParserException("Nome de tipo base esperado, mas encontrou " + t.lexeme());
        };
    }

    private LValue parseLValue() {
        Exp base = new VarAccessExp(eat(TokenType.ID).lexeme());
        while (true) {
            if (currentToken.type() == TokenType.LBRACK) {
                eat(TokenType.LBRACK);
                Exp index = parseExpression();
                eat(TokenType.RBRACK);
                base = new ArrayAccessExp(base, index);
            } else if (currentToken.type() == TokenType.DOT) {
                eat(TokenType.DOT);
                String fieldName = eat(TokenType.ID).lexeme();
                base = new FieldAccessExp(base, fieldName);
            } else {
                break;
            }
        }
        if (!(base instanceof LValue)) {
            throw new ParserException("Expressão inválida para o lado esquerdo de uma atribuição.");
        }
        return (LValue)base;
    }

    private List<Exp> parseExpressionList() {
        List<Exp> exps = new ArrayList<>();
        if (currentToken.type() == TokenType.RPAREN || currentToken.type() == TokenType.SEMI) {
            return exps;
        }
        do {
            exps.add(parseExpression());
            if (currentToken.type() == TokenType.COMMA) eat(TokenType.COMMA); else break;
        } while (true);
        return exps;
    }

    private TypeNode parseTypeNode() {
        TypeNode type = switch(currentToken.type()) {
            case INT_TYPE -> new BaseTypeNode(eat(TokenType.INT_TYPE).lexeme());
            case BOOL_TYPE -> new BaseTypeNode(eat(TokenType.BOOL_TYPE).lexeme());
            case CHAR_TYPE -> new BaseTypeNode(eat(TokenType.CHAR_TYPE).lexeme());
            case FLOAT_TYPE -> new BaseTypeNode(eat(TokenType.FLOAT_TYPE).lexeme());
            case TYID -> new BaseTypeNode(eat(TokenType.TYID).lexeme());
            default -> throw new ParserException("Nome de tipo base esperado.");
        };

        while (currentToken.type() == TokenType.LBRACK) {
            eat(TokenType.LBRACK);
            eat(TokenType.RBRACK);
            type = new ArrayTypeNode(type);
        }
        return type;
    }

    // Helper para manter a simplicidade em locais que só precisam do nome do tipo como String
    private String parseTypeNameAsString() {
        return parseTypeNode().toString(); // Uma representação em string simples
    }

    private Token peek() {
        // Implementar um buffer de lookahead no Lexer seria mais eficiente
        // Esta é uma simplificação.
        Lexer tempLexer = new Lexer(lexer.getInput().substring(lexer.getPosition()));
        return tempLexer.nextToken();
    }
}