package org.example.lang.semantica;

import org.example.lang.Exception.SemanticException;
import org.example.lang.ast.*;
import org.example.lang.ast.cmd.*;
import org.example.lang.ast.decl.*;
import org.example.lang.ast.exp.*;
import org.example.lang.ast.type.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Visitor que realiza a análise semântica (verificação de tipos).
 * Implementa a estratégia de duas passagens:
 * 1. Coleta de símbolos (funções e tipos de dados).
 * 2. Verificação de tipos dos comandos e expressões.
 */
public class TypeCheckerVisitor implements Visitor<TypeNode> {

    // Contexto de Tipos de Dados (Δ)
    private final Map<String, DataDecl> dataTypesContext = new HashMap<>();
    // Contexto de Funções (Θ)
    private final Map<String, FunDecl> functionsContext = new HashMap<>();
    // Contexto de Variáveis (Γ)
    private final Stack<Map<String, TypeNode>> varContext = new Stack<>();

    // Armazena os tipos de retorno esperados para a função atual
    private List<TypeNode> expectedReturnTypes;

    private FunDecl currentFunctionDecl = null;

    // Método principal que inicia a verificação
    public void check(Program program) {
        // Passagem 1: Coletar todas as declarações de tipos e funções
        collectSymbols(program);
        // Passagem 2: Verificar os tipos do programa
        program.accept(this);
    }

    void collectSymbols(Program program) {
        for (Decl decl : program.decls()) {
            if (decl instanceof DataDecl d) {
                if (dataTypesContext.containsKey(d.name())) {
                    throw new SemanticException("Tipo '" + d.name() + "' já definido.");
                }
                dataTypesContext.put(d.name(), d);
                // Coleta funções aninhadas em tipos de dados abstratos
                for (FunDecl fun : d.functions()) {
                    if (functionsContext.containsKey(fun.name())) {
                        throw new SemanticException("Função '" + fun.name() + "' já definida.");
                    }
                    functionsContext.put(fun.name(), fun);
                }
            } else if (decl instanceof FunDecl f) {
                if (functionsContext.containsKey(f.name())) {
                    throw new SemanticException("Função '" + f.name() + "' já definida.");
                }
                functionsContext.put(f.name(), f);
            }
        }
    }

    // --- Métodos de Visita ---

    @Override
    public TypeNode visit(Program p) {
        varContext.push(new HashMap<>());
        for (Decl decl : p.decls()) {
            decl.accept(this);
        }
        varContext.pop();
        return null;
    }

    @Override
    public TypeNode visit(FunDecl d) {
        this.currentFunctionDecl = d;
        varContext.push(new HashMap<>());
        expectedReturnTypes = d.returnTypes();

        for (FunDecl.Param param : d.params()) {
            varContext.peek().put(param.name(), param.type());
        }

        d.body().accept(this);

        varContext.pop();
        expectedReturnTypes = null;
        this.currentFunctionDecl = null;
        return null;
    }

    @Override
    public TypeNode visit(DataDecl d) {
        for (FunDecl fun : d.functions()) {
            fun.accept(this);
        }
        return null;
    }

    @Override
    public TypeNode visit(BlockCmd c) {
        varContext.push(new HashMap<>());
        for (Cmd cmd : c.cmds()) {
            cmd.accept(this);
        }
        varContext.pop();
        return null;
    }

    @Override
    public TypeNode visit(AssignCmd c) {
        // Primeiro, determina o tipo da expressão que está sendo atribuída.
        TypeNode expType = c.exp().accept(this);

        // Agora, lida com o lvalue.
        if (c.lvalue() instanceof VarAccessExp vae) {
            String varName = vae.name();
            // Verifica se a variável já está definida em algum escopo visível.
            TypeNode existingType = findVarInScopes(varName);

            if (existingType != null) {
                // Variável existe, verifica a compatibilidade de tipos.
                if (!areTypesCompatible(existingType, expType)) {
                    throw new SemanticException("Tipos incompatíveis na atribuição. A variável '" + varName + "' é do tipo " + existingType + ", mas encontrou " + expType);
                }
            } else {
                // Variável não existe, então a declaramos no escopo ATUAL.
                varContext.peek().put(varName, expType);
            }
        } else {
            // Para lvalues complexos (a[i], p.x), eles devem existir.
            // A chamada accept() aqui irá validar a existência da base (a, p).
            TypeNode lvalueType = c.lvalue().accept(this);
            if (!areTypesCompatible(lvalueType, expType)) {
                throw new SemanticException("Tipos incompatíveis na atribuição. Esperado " + lvalueType + ", mas encontrou " + expType);
            }
        }
        return null;
    }

    @Override
    public TypeNode visit(IfCmd c) {
        TypeNode conditionType = c.condition().accept(this);
        if (!isBool(conditionType)) {
            throw new SemanticException("Condição do 'if' deve ser do tipo Bool, mas é " + conditionType);
        }
        c.thenBranch().accept(this);
        if (c.elseBranch() != null) {
            c.elseBranch().accept(this);
        }
        return null;
    }

    @Override
    public TypeNode visit(ReturnCmd c) {
        if (expectedReturnTypes == null) {
            throw new SemanticException("Comando 'return' fora de uma função.");
        }
        List<Exp> returnExps = c.exps();
        if (returnExps.size() != expectedReturnTypes.size()) {
            throw new SemanticException("Número incorreto de valores de retorno. Esperado " + expectedReturnTypes.size() + ", mas encontrou " + returnExps.size());
        }

        for (int i = 0; i < returnExps.size(); i++) {
            TypeNode returnedType = returnExps.get(i).accept(this);
            TypeNode expectedType = expectedReturnTypes.get(i);
            if (!areTypesCompatible(expectedType, returnedType)) {
                throw new SemanticException("Tipo de retorno incorreto. Esperado " + expectedType + ", mas encontrou " + returnedType);
            }
        }
        return null;
    }

    @Override
    public TypeNode visit(FunCallExp e) {
        FunDecl func = functionsContext.get(e.name());
        if (func == null) {
            throw new SemanticException("Função '" + e.name() + "' não definida.");
        }

        checkFunctionCallArgs(e.name(), e.args(), func.params());

        if (e.returnIndex().isPresent()) {
            Exp indexExp = e.returnIndex().get();
            TypeNode indexType = indexExp.accept(this);

            // Verifica se o tipo do índice é Int
            if (!isInt(indexType)) {
                throw new SemanticException("Índice de retorno de função deve ser do tipo Int.");
            }

            // CORREÇÃO: Para uma verificação estática robusta, o índice deve ser um literal constante.
            if (!(indexExp instanceof IntLiteralExp)) {
                throw new SemanticException("Índice de retorno de função deve ser um literal inteiro constante para verificação estática.");
            }

            int indexValue = ((IntLiteralExp) indexExp).value();
            List<TypeNode> returnTypes = func.returnTypes();

            // Verifica se o índice está dentro dos limites dos valores de retorno
            if (indexValue < 0 || indexValue >= returnTypes.size()) {
                throw new SemanticException("Índice de retorno " + indexValue + " fora dos limites para a função '" + e.name() + "', que retorna " + returnTypes.size() + " valores.");
            }

            return returnTypes.get(indexValue);
        }

        return null; // Contexto de atribuição múltipla
    }

    @Override
    public TypeNode visit(VarAccessExp e) {
        TypeNode type = findVarInScopes(e.name());
        if (type != null) {
            return type;
        }
        throw new SemanticException("Variável '" + e.name() + "' não definida neste escopo.");
    }

    @Override
    public TypeNode visit(PrintCmd c) {
        TypeNode type = c.exp().accept(this);
        if (!(isInt(type) || isFloat(type) || isChar(type) || isBool(type))) {
            throw new SemanticException("Comando 'print' não suporta o tipo " + type);
        }
        return null;
    }

    @Override
    public TypeNode visit(ReadCmd c) {
        TypeNode type = c.lvalue().accept(this);
        if (!(isInt(type) || isFloat(type) || isChar(type))) {
            throw new SemanticException("Comando 'read' não suporta o tipo " + type);
        }
        return null;
    }

    @Override
    public TypeNode visit(IterateCmd c) {
        TypeNode collectionType = c.collection().accept(this);
        if (c.var().isPresent()) {
            String varName = c.var().get();
            varContext.push(new HashMap<>());
            if (collectionType instanceof ArrayTypeNode atn) {
                varContext.peek().put(varName, atn.elementType());
            } else if (isInt(collectionType)) {
                varContext.peek().put(varName, new BaseTypeNode("Int"));
            } else {
                throw new SemanticException("'iterate' com variável requer um Array ou Int, mas encontrou " + collectionType);
            }
            c.body().accept(this);
            varContext.pop();
        } else {
            if (!isInt(collectionType)) {
                throw new SemanticException("'iterate' sem variável requer um Int, mas encontrou " + collectionType);
            }
            c.body().accept(this);
        }
        return null;
    }

    @Override
    public TypeNode visit(ProcCallCmd c) {
        FunDecl func = functionsContext.get(c.name());
        if (func == null) {
            throw new SemanticException("Procedimento '" + c.name() + "' não definido.");
        }
        checkFunctionCallArgs(c.name(), c.args(), func.params());
        return null;
    }

    @Override
    public TypeNode visit(FuncCallAssignCmd c) {
        FunDecl func = functionsContext.get(c.call().name());
        if (func == null) {
            throw new SemanticException("Função '" + c.call().name() + "' não definida.");
        }
        checkFunctionCallArgs(c.call().name(), c.call().args(), func.params());

        List<TypeNode> returnTypes = func.returnTypes();
        List<LValue> lvalues = c.lvalues();
        if (returnTypes.size() != lvalues.size()) {
            throw new SemanticException("Número de variáveis (" + lvalues.size() + ") incompatível com o número de retornos (" + returnTypes.size() + ") da função '" + func.name() + "'.");
        }
        for (int i = 0; i < lvalues.size(); i++) {
            LValue currentLValue = lvalues.get(i);
            TypeNode expectedType = returnTypes.get(i);

            if (currentLValue instanceof VarAccessExp vae) {
                String varName = vae.name();
                TypeNode existingType = findVarInScopes(varName);

                if (existingType != null) {
                    // Variável já existe, apenas checa a compatibilidade de tipo.
                    if (!areTypesCompatible(existingType, expectedType)) {
                        throw new SemanticException("Tipo do lvalue '" + varName + "' ("+ existingType +") incompatível com o retorno da função ("+ expectedType +").");
                    }
                } else {
                    // Variável NÃO existe, então a declaramos no escopo ATUAL.
                    varContext.peek().put(varName, expectedType);
                }
            } else {
                // Para lvalues complexos (a[i], p.x), eles devem existir.
                TypeNode lvalueType = currentLValue.accept(this);
                if (!areTypesCompatible(lvalueType, expectedType)) {
                    throw new SemanticException("Tipo do lvalue #" + (i+1) + " incompatível com o retorno da função.");
                }
            }
        }
        return null;
    }

    @Override
    public TypeNode visit(BinOpExp e) {
        TypeNode left = e.left().accept(this);
        TypeNode right = e.right().accept(this);

        switch (e.op()) {
            case "+", "-", "*", "/":
                if ((isInt(left) || isFloat(left)) && (isInt(right) || isFloat(right))) {
                    return (isFloat(left) || isFloat(right)) ? new BaseTypeNode("Float") : new BaseTypeNode("Int");
                }
                break;
            case "%":
                if (isInt(left) && isInt(right)) return new BaseTypeNode("Int");
                break;
            case "<":
                if ((isInt(left) && isInt(right)) || (isFloat(left) && isFloat(right)) || (isChar(left) && isChar(right))) {
                    return new BaseTypeNode("Bool");
                }
                break;
            case "==", "!=":
                if (areTypesCompatible(left, right) || areTypesCompatible(right, left)) {
                    return new BaseTypeNode("Bool");
                }
                break;
            case "&&":
                if (isBool(left) && isBool(right)) return new BaseTypeNode("Bool");
                break;
        }
        throw new SemanticException("Operador '" + e.op() + "' não pode ser aplicado aos tipos " + left + " e " + right);
    }

    @Override
    public TypeNode visit(UnaryExp e) {
        TypeNode type = e.exp().accept(this);
        switch (e.op()) {
            case "-":
                if (isInt(type) || isFloat(type)) return type;
                break;
            case "!":
                if (isBool(type)) return new BaseTypeNode("Bool");
                break;
        }
        throw new SemanticException("Operador unário '" + e.op() + "' não pode ser aplicado ao tipo " + type);
    }

    @Override
    public TypeNode visit(NewExp e) {
        if (e.size().isPresent()) { // Alocação de array
            TypeNode sizeType = e.size().get().accept(this);
            if (!isInt(sizeType)) {
                throw new SemanticException("Tamanho do array deve ser do tipo Int, mas é " + sizeType);
            }
            return e.typeNode();
        } else { // Alocação de registro
            if (e.typeNode() instanceof BaseTypeNode btn && dataTypesContext.containsKey(btn.typeName())) {
                return e.typeNode();
            }
            throw new SemanticException("Tipo '" + e.typeNode() + "' não é um tipo de dado (registro) válido para 'new'.");
        }
    }

    @Override
    public TypeNode visit(FieldAccessExp e) {
        TypeNode recordType = e.recordExp().accept(this);
        if (recordType instanceof BaseTypeNode btn && dataTypesContext.containsKey(btn.typeName())) {
            DataDecl decl = dataTypesContext.get(btn.typeName());
            if (decl.isAbstract()) {
                boolean isAccessAllowed = false;
                // O acesso só é permitido se estivermos dentro de uma função
                if (currentFunctionDecl != null) {
                    // E se essa função for uma das funções definidas dentro do tipo de dado
                    for (FunDecl funcInScope : decl.functions()) {
                        if (funcInScope.name().equals(currentFunctionDecl.name())) {
                            isAccessAllowed = true;
                            break;
                        }
                    }
                }
                if (!isAccessAllowed) {
                    throw new SemanticException("Acesso ilegal ao campo '" + e.fieldName() + "'. O tipo '" + btn.typeName() + "' é abstrato e seus campos só podem ser acessados por seus próprios métodos.");
                }
            }
            for (DataDecl.Field field : decl.fields()) {
                if (field.name().equals(e.fieldName())) {
                    return field.type();
                }
            }
            throw new SemanticException("Tipo '" + btn.typeName() + "' não possui o campo '" + e.fieldName() + "'.");
        }
        throw new SemanticException("Acesso a campo '.' só pode ser aplicado a um tipo de registro.");
    }

    @Override
    public TypeNode visit(ArrayAccessExp e) {
        TypeNode arrayType = e.arrayExp().accept(this);
        TypeNode indexType = e.indexExp().accept(this);

        if (!isInt(indexType)) {
            throw new SemanticException("Índice de array deve ser do tipo Int, mas é " + indexType);
        }
        if (arrayType instanceof ArrayTypeNode atn) {
            return atn.elementType();
        }
        throw new SemanticException("Acesso a array '[]' só pode ser aplicado a um tipo de array.");
    }

    @Override
    public TypeNode visit(NullTypeNode n) {
        return null;
    }

    // --- Métodos de Visita para Literais e Nós de Tipo ---
    @Override public TypeNode visit(IntLiteralExp e) { return new BaseTypeNode("Int"); }
    @Override public TypeNode visit(FloatLiteralExp e) { return new BaseTypeNode("Float"); }
    @Override public TypeNode visit(CharLiteralExp e) { return new BaseTypeNode("Char"); }
    @Override public TypeNode visit(BoolLiteralExp e) { return new BaseTypeNode("Bool"); }
    @Override public TypeNode visit(NullLiteralExp e) { return new NullTypeNode(); }
    @Override public TypeNode visit(BaseTypeNode n) { return n; }
    @Override public TypeNode visit(ArrayTypeNode n) { return n; }

    // --- Métodos Auxiliares ---
    private boolean areTypesCompatible(TypeNode expected, TypeNode actual) {
        if (actual instanceof NullTypeNode) {
            return (expected instanceof ArrayTypeNode || (expected instanceof BaseTypeNode && dataTypesContext.containsKey(((BaseTypeNode)expected).typeName())));
        }
        return expected.equals(actual);
    }

    private void checkFunctionCallArgs(String funcName, List<Exp> args, List<FunDecl.Param> params) {
        if (args.size() != params.size()) {
            throw new SemanticException("Número incorreto de argumentos para a função '" + funcName + "'. Esperado " + params.size() + ", mas encontrou " + args.size());
        }
        for (int i = 0; i < args.size(); i++) {
            TypeNode argType = args.get(i).accept(this);
            TypeNode paramType = params.get(i).type();
            if (!areTypesCompatible(paramType, argType)) {
                throw new SemanticException("Argumento " + i + " da função '" + funcName + "' tem tipo incorreto. Esperado " + paramType + ", mas encontrou " + argType);
            }
        }
    }

    private TypeNode findVarInScopes(String name) {
        for (int i = varContext.size() - 1; i >= 0; i--) {
            if (varContext.get(i).containsKey(name)) {
                return varContext.get(i).get(name);
            }
        }
        return null;
    }

    private boolean isInt(TypeNode type) { return type instanceof BaseTypeNode btn && btn.typeName().equals("Int"); }
    private boolean isFloat(TypeNode type) { return type instanceof BaseTypeNode btn && btn.typeName().equals("Float"); }
    private boolean isChar(TypeNode type) { return type instanceof BaseTypeNode btn && btn.typeName().equals("Char"); }
    private boolean isBool(TypeNode type) { return type instanceof BaseTypeNode btn && btn.typeName().equals("Bool"); }
}
