package org.example.lang.jasmingenerator;

import org.example.lang.ast.Program;
import org.example.lang.ast.TypeNode;
import org.example.lang.ast.Visitor;
import org.example.lang.ast.cmd.AssignCmd;
import org.example.lang.ast.cmd.BlockCmd;
import org.example.lang.ast.cmd.Cmd;
import org.example.lang.ast.cmd.FuncCallAssignCmd;
import org.example.lang.ast.cmd.IfCmd;
import org.example.lang.ast.cmd.IterateCmd;
import org.example.lang.ast.cmd.PrintCmd;
import org.example.lang.ast.cmd.ProcCallCmd;
import org.example.lang.ast.cmd.ReadCmd;
import org.example.lang.ast.cmd.ReturnCmd;
import org.example.lang.ast.decl.DataDecl;
import org.example.lang.ast.decl.Decl;
import org.example.lang.ast.decl.FunDecl;
import org.example.lang.ast.exp.ArrayAccessExp;
import org.example.lang.ast.exp.BinOpExp;
import org.example.lang.ast.exp.BoolLiteralExp;
import org.example.lang.ast.exp.CharLiteralExp;
import org.example.lang.ast.exp.Exp;
import org.example.lang.ast.exp.FieldAccessExp;
import org.example.lang.ast.exp.FloatLiteralExp;
import org.example.lang.ast.exp.FunCallExp;
import org.example.lang.ast.exp.IntLiteralExp;
import org.example.lang.ast.exp.LValue;
import org.example.lang.ast.exp.NewExp;
import org.example.lang.ast.exp.NullLiteralExp;
import org.example.lang.ast.exp.UnaryExp;
import org.example.lang.ast.exp.VarAccessExp;
import org.example.lang.ast.type.ArrayTypeNode;
import org.example.lang.ast.type.BaseTypeNode;
import org.example.lang.ast.type.NullTypeNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */


/**
 * Visitor que percorre a AST (já anotada com tipos) e gera código Jasmin.
 */
public class JasminGeneratorVisitor implements Visitor<Void> {

    private StringBuilder code;
    private final Map<String, DataDecl> dataDeclarations = new HashMap<>();

    private final Map<String, FunDecl> functionsContext = new HashMap<>();
    private final String className;

    private Map<String, Integer> varMap;
    private int nextVarSlot;
    private int labelCounter;

    public JasminGeneratorVisitor(String className) {
        this.className = className;
    }

    private void collectSymbols(Program program) {
        for (Decl decl : program.decls()) {
            if (decl instanceof DataDecl d) {
                dataDeclarations.put(d.name(), d);
                // Coleta funções aninhadas
                for (FunDecl fun : d.functions()) {
                    functionsContext.put(fun.name(), fun);
                }
            } else if (decl instanceof FunDecl f) {
                // Coleta funções globais
                functionsContext.put(f.name(), f);
            }
        }
    }



    public Map<String, String> generate(Program program) {
        Map<String, String> generatedFiles = new HashMap<>();

        collectSymbols(program);

        for (Decl decl : program.decls()) {
            if (decl instanceof DataDecl d) {
                dataDeclarations.put(d.name(), d);
            }
        }

        this.code = new StringBuilder();
        program.accept(this);
        generatedFiles.put(className, code.toString());

        for (DataDecl d : dataDeclarations.values()) {
            generatedFiles.put(d.name(), generateDataClass(d));
        }
        return generatedFiles;
    }

    private String generateDataClass(DataDecl d) {
        StringBuilder sb = new StringBuilder();
        sb.append(".class public ").append(d.name()).append("\n");
        sb.append(".super java/lang/Object\n\n");
        for (DataDecl.Field field : d.fields()) {
            sb.append(".field public ").append(field.name()).append(" ").append(typeToDescriptor(field.type())).append("\n");
        }
        sb.append("\n.method public <init>()V\n");
        sb.append("    aload_0\n");
        sb.append("    invokespecial java/lang/Object/<init>()V\n");
        sb.append("    return\n");
        sb.append(".end method\n");
        // Gera o código para as funções que pertencem a esta classe
        for (FunDecl fun : d.functions()) {
            this.code = new StringBuilder(); // Reseta o buffer de código
            fun.accept(this); // Visita a função para gerar seu bytecode
            sb.append(this.code.toString()); // Anexa o bytecode da função
        }
        return sb.toString();
    }
    private String findFunctionOwnerClass(String functionName) {
        for (DataDecl dataDecl : dataDeclarations.values()) {
            for (FunDecl fun : dataDecl.functions()) {
                if (fun.name().equals(functionName)) {
                    return dataDecl.name(); // Retorna o nome da classe do 'data' (ex: "Aluno")
                }
            }
        }
        return this.className; // Se não encontrou, é uma função global da classe principal
    }

    private String newLabel() { return "L" + (labelCounter++); }

    // --- Métodos de Visita ---



    private String getInverseJumpInstruction(String op, TypeNode type) {
        boolean isRef = isRef(type);
        return switch (op) {
            case "==" -> isRef ? "if_acmpne" : "if_icmpne"; // Inverso de 'igual' é 'diferente'
            case "!=" -> isRef ? "if_acmpeq" : "if_icmpeq"; // Inverso de 'diferente' é 'igual'
            case "<"  -> "if_icmpge"; // Inverso de 'menor que' é 'maior ou igual a'
            default -> "";
        };
    }


    @Override
    public Void visit(Program p) {
        code.append(".class public ").append(className).append("\n");
        code.append(".super java/lang/Object\n\n");
        code.append(".method public <init>()V\n");
        code.append("    aload_0\n");
        code.append("    invokespecial java/lang/Object/<init>()V\n");
        code.append("    return\n");
        code.append(".end method\n\n");

        for (Decl decl : p.decls()) {
            if (decl instanceof FunDecl f) {
                f.accept(this);
            }
        }
        return null;
    }

    @Override
    public Void visit(FunDecl d) {
        varMap = new HashMap<>();
        nextVarSlot = 0;
        labelCounter = 0;

        String paramsDescriptor = d.params().stream().map(param -> typeToDescriptor(param.type())).collect(Collectors.joining());
        String returnDescriptor = getReturnDescriptor(d.returnTypes());
        String methodName = d.name().equals("main") ? "main" : d.name();
        String methodSignature = d.name().equals("main") ? "([Ljava/lang/String;)V" : "(" + paramsDescriptor + ")" + returnDescriptor;

        code.append(".method public static ").append(methodName).append(methodSignature).append("\n");
        code.append("    .limit stack 25\n");
        code.append("    .limit locals 25\n\n");

        if (d.name().equals("main")) { varMap.put("args", nextVarSlot++); }
        for (FunDecl.Param param : d.params()) { varMap.put(param.name(), nextVarSlot++); }

        d.body().accept(this);

        if (d.returnTypes().isEmpty() || d.name().equals("main")) {
            code.append("    return\n");
        }
        code.append(".end method\n\n");
        return null;
    }

    // --- Comandos ---

    @Override
    public Void visit(BlockCmd c) {
        for (Cmd cmd : c.cmds()) {
            cmd.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(AssignCmd c) {
        if (c.lvalue() instanceof VarAccessExp vae) {
            if (vae.name().equals("v")) {
                // Breakpoint aqui. Inspecione o tipo de 'v'.
                System.out.println("DEBUG: Tipo anotado para 'v': " + (vae.getType() == null ? "null" : vae.getType().getClass().getName()));
            }
            c.exp().accept(this);
            int slot = getVarSlot(vae.name());
            code.append("    ").append(getStoreInstruction(vae.getType())).append(" ").append(slot).append("\n");
        } else if (c.lvalue() instanceof FieldAccessExp fae) {
            fae.recordExp().accept(this); // Empilha a referência ao objeto
            c.exp().accept(this); // Empilha o valor a ser atribuído
            String owner = ((BaseTypeNode)fae.recordExp().getType()).typeName();
            String fieldName = fae.fieldName();
            String descriptor = typeToDescriptor(fae.getType());
            code.append("    putfield ").append(owner).append("/").append(fieldName).append(" ").append(descriptor).append("\n");
        } else if (c.lvalue() instanceof ArrayAccessExp aae) {
            aae.arrayExp().accept(this); // Empilha a referência ao array
            aae.indexExp().accept(this); // Empilha o índice
            c.exp().accept(this); // Empilha o valor a ser atribuído
            code.append("    ").append(getArrayStoreInstruction(aae.getType())).append("\n");
        }
        return null;
    }

    @Override
    public Void visit(IfCmd c) {
        String elseLabel = newLabel();
        String endLabel = newLabel();

        String falseTarget = c.elseBranch() != null ? elseLabel : endLabel;

        // 1. Gera a condição que pula para 'falseTarget' se for FALSA.
        generateBranchingCondition(c.condition(), falseTarget, true);

        // 2. Gera o bloco THEN.
        c.thenBranch().accept(this);

        // 3. Se houver um bloco ELSE, o THEN precisa pular para o fim.
        //    ADICIONAMOS UMA VERIFICAÇÃO PARA NÃO GERAR 'goto' APÓS UM 'return'.
        if (c.elseBranch() != null && !(c.thenBranch() instanceof ReturnCmd)) {
            code.append("    goto ").append(endLabel).append("\n");
        }

        // 4. Gera o bloco ELSE (se existir).
        code.append(elseLabel).append(":\n");
        if (c.elseBranch() != null) {
            c.elseBranch().accept(this);
        }

        // 5. Gera o rótulo final.
        code.append(endLabel).append(":\n");

        return null;
    }

    private void generateBranchingCondition(Exp condition, String targetLabel, boolean jumpOnFalse) {
        if (condition instanceof BinOpExp e && isRelational(e.op())) {
            e.left().accept(this);
            e.right().accept(this);

            String op = jumpOnFalse
                    ? getInverseJumpInstruction(e.op(), e.left().getType())
                    : getJumpInstruction(e.op(), e.left().getType());

            if (isFloat(e.left().getType())) {
                // Para float, a lógica de pulo é em relação a zero
                code.append("    fcmpl\n");
                String jumpInstruction = "";
                if(op.equals("if_icmpne") || op.equals("ifne")){ jumpInstruction = "ifne"; }
                else if(op.equals("if_icmpeq") || op.equals("ifeq")){ jumpInstruction = "ifeq"; }
                else if(op.equals("if_icmpge") || op.equals("ifge")){ jumpInstruction = "ifge"; }
                else if(op.equals("if_icmplt") || op.equals("iflt")){ jumpInstruction = "iflt"; }
                code.append("    ").append(jumpInstruction).append(" ").append(targetLabel).append("\n");
            } else {
                code.append("    ").append(op).append(" ").append(targetLabel).append("\n");
            }
        } else {
            condition.accept(this); // Empilha 0 ou 1
            if (jumpOnFalse) {
                code.append("    ifeq ").append(targetLabel).append("\n"); // Pula para o alvo se o valor for 0 (false)
            } else {
                code.append("    ifne ").append(targetLabel).append("\n"); // Pula para o alvo se o valor for != 0 (true)
            }
        }
    }

    @Override
    public Void visit(PrintCmd c) {
        code.append("    getstatic java/lang/System/out Ljava/io/PrintStream;\n");
        c.exp().accept(this);
        String typeDescriptor = typeToDescriptor(c.exp().getType());
        code.append("    invokevirtual java/io/PrintStream/print(").append(typeDescriptor).append(")V\n");
        return null;
    }

    @Override
    public Void visit(ReturnCmd c) {
        List<Exp> exps = c.exps();
        if (exps.size() > 1) {
            pushInt(exps.size());
            code.append("    anewarray java/lang/Object\n");
            for (int i = 0; i < exps.size(); i++) {
                code.append("    dup\n");
                pushInt(i);
                exps.get(i).accept(this);
                boxIfPrimitive(exps.get(i).getType());
                code.append("    aastore\n");
            }
            code.append("    areturn\n");
        } else if (exps.size() == 1) {
            exps.get(0).accept(this);
            code.append("    ").append(getReturnInstruction(exps.get(0).getType())).append("\n");
        } else {
            code.append("    return\n");
        }
        return null;
    }

    @Override
    public Void visit(IterateCmd c) {
        String startLabel = newLabel();
        String endLabel = newLabel();
        String bodyLabel = newLabel();

        // Obtém o tipo da coleção da AST já anotada
        TypeNode collectionType = c.collection().getType();

        if (collectionType instanceof ArrayTypeNode) {
            // --- CASO 1: ITERAÇÃO SOBRE ARRAY ---
            int indexSlot = getVarSlot("~array_idx" + labelCounter);
            int arraySlot = getVarSlot("~array_ref" + labelCounter);

            // Salva a referência do array
            c.collection().accept(this);
            code.append("    astore ").append(arraySlot).append("\n");
            // Inicializa o índice em 0
            code.append("    iconst_0\n");
            code.append("    istore ").append(indexSlot).append("\n");

            code.append(startLabel).append(":\n");
            // Condição de parada: if (index >= array.length) goto endLabel
            code.append("    iload ").append(indexSlot).append("\n");
            code.append("    aload ").append(arraySlot).append("\n");
            code.append("    arraylength\n");
            code.append("    if_icmpge ").append(endLabel).append("\n");

            // Se houver uma variável de laço (ex: 'elem : meuArray'), atribui o elemento a ela
            if (c.var().isPresent()) {
                int varSlot = getVarSlot(c.var().get());
                code.append("    aload ").append(arraySlot).append("\n");
                code.append("    iload ").append(indexSlot).append("\n");
                code.append("    ").append(getArrayLoadInstruction(((ArrayTypeNode) collectionType).elementType())).append("\n");
                code.append("    ").append(getStoreInstruction(((ArrayTypeNode) collectionType).elementType())).append(" ").append(varSlot).append("\n");
            }

            // Gera o corpo do laço
            c.body().accept(this);

            // Incrementa o índice
            code.append("    iinc ").append(indexSlot).append(" 1\n");
            code.append("    goto ").append(startLabel).append("\n");
            code.append(endLabel).append(":\n");

        } else {
            // --- CASO 2: ITERAÇÃO SOBRE INTEIRO ---
            int limitSlot = getVarSlot("~int_limit" + labelCounter);
            int indexSlot = getVarSlot(c.var().orElse("~dummy_idx" + labelCounter));

            // Salva o limite (valor da coleção)
            c.collection().accept(this);
            code.append("    istore ").append(limitSlot).append("\n");
            // Inicializa o índice em 0
            code.append("    iconst_0\n");
            code.append("    istore ").append(indexSlot).append("\n");

            code.append(startLabel).append(":\n");
            // Condição de parada: if (index >= limit) goto endLabel
            code.append("    iload ").append(indexSlot).append("\n");
            code.append("    iload ").append(limitSlot).append("\n");
            code.append("    if_icmpge ").append(endLabel).append("\n");

            // Gera o corpo do laço
            c.body().accept(this);

            // Incrementa o índice
            code.append("    iinc ").append(indexSlot).append(" 1\n");
            code.append("    goto ").append(startLabel).append("\n");
            code.append(endLabel).append(":\n");
        }

        return null;
    }

    @Override
    public Void visit(ProcCallCmd c) {
        // Empilha todos os argumentos na pilha da JVM.
        for (Exp arg : c.args()) {
            arg.accept(this);
        }

        FunDecl func = functionsContext.get(c.name());
        if (func == null) {
            throw new RuntimeException("Erro de geração: Procedimento '" + c.name() + "' não encontrado no contexto.");
        }

        // --- LÓGICA DE CHAMADA CORRIGIDA ---
        // 1. Encontra a classe correta para a função.
        String ownerClass = findFunctionOwnerClass(c.name());

        // 2. Constrói a assinatura completa usando a classe dona correta.
        String paramsDescriptor = func.params().stream().map(p -> typeToDescriptor(p.type())).collect(Collectors.joining());
        String returnDescriptor = getReturnDescriptor(func.returnTypes());
        String methodSignature = ownerClass + "/" + c.name() + "(" + paramsDescriptor + ")" + returnDescriptor;

        // 3. Gera a instrução de chamada.
        code.append("    invokestatic ").append(methodSignature).append("\n");
        // --- FIM DA CORREÇÃO ---

        if (!returnDescriptor.equals("V")) {
            code.append("    pop\n");
        }
        return null;
    }

    @Override
    public Void visit(FuncCallAssignCmd c) {
        for (Exp arg : c.call().args()) {
            arg.accept(this);
        }

        FunDecl func = functionsContext.get(c.call().name());
        if (func == null) {
            throw new RuntimeException("Erro de geração: Função '" + c.call().name() + "' não encontrada no contexto.");
        }

        // --- LÓGICA DE CHAMADA CORRIGIDA ---
        String ownerClass = findFunctionOwnerClass(c.call().name());
        String paramsDescriptor = func.params().stream().map(p -> typeToDescriptor(p.type())).collect(Collectors.joining());
        String returnDescriptor = getReturnDescriptor(func.returnTypes());
        String methodSignature = ownerClass + "/" + c.call().name() + "(" + paramsDescriptor + ")" + returnDescriptor;
        code.append("    invokestatic ").append(methodSignature).append("\n");
        // --- FIM DA CORREÇÃO ---

        // O resto da lógica para lidar com a atribuição de múltiplos retornos permanece o mesmo.
        if (func.returnTypes().size() > 1) {
            // ... (lógica existente) ...
        } else if (func.returnTypes().size() == 1) {
            // ... (lógica existente) ...
        }
        return null;
    }

    // --- Expressões ---


    @Override
    public Void visit(BinOpExp e) {
        // --- LÓGICA CORRIGIDA E COMPLETA PARA BINOP ---

        if (e.op().equals("&&")) {
            // --- CASO 1: OPERADOR LÓGICO AND (&&) ---
            // Implementa o comportamento de "curto-circuito".
            String falseLabel = newLabel();
            String endLabel = newLabel();

            // 1. Avalia o lado esquerdo.
            e.left().accept(this);
            // 2. Se for falso (0), o resultado já é falso, então pula para o fim.
            code.append("    ifeq ").append(falseLabel).append("\n");

            // 3. Se o lado esquerdo era verdadeiro, o resultado da expressão é o do lado direito.
            e.right().accept(this);
            code.append("    goto ").append(endLabel).append("\n");

            // 4. Caminho do 'falso': empilha 0.
            code.append(falseLabel).append(":\n");
            code.append("    iconst_0\n");

            // 5. Rótulo final. O resultado (0 ou 1) está no topo da pilha.
            code.append(endLabel).append(":\n");

        } else if (isRelational(e.op())) {
            // --- CASO 2: OPERADORES RELACIONAIS (<, ==, !=) ---
            String trueLabel = newLabel();
            String endLabel = newLabel();
            e.left().accept(this);
            e.right().accept(this);
            TypeNode operandType = e.left().getType();

            if (isFloat(operandType)) {
                code.append("    fcmpl\n");
                switch (e.op()) {
                    case "<"  -> code.append("    iflt ").append(trueLabel).append("\n");
                    case "==" -> code.append("    ifeq ").append(trueLabel).append("\n");
                    case "!=" -> code.append("    ifne ").append(trueLabel).append("\n");
                }
            } else {
                String jumpInstruction = getJumpInstruction(e.op(), operandType);
                code.append("    ").append(jumpInstruction).append(" ").append(trueLabel).append("\n");
            }

            code.append("    iconst_0\n");
            code.append("    goto ").append(endLabel).append("\n");
            code.append(trueLabel).append(":\n");
            code.append("    iconst_1\n");
            code.append(endLabel).append(":\n");

        } else {
            // --- CASO 3: OPERADORES ARITMÉTICOS (+, -, *, /, %) ---
            TypeNode resultType = e.getType();
            TypeNode leftType = e.left().getType();
            TypeNode rightType = e.right().getType();
            e.left().accept(this);
            if (isFloat(resultType) && !isFloat(leftType)) {
                code.append("    i2f\n");
            }
            e.right().accept(this);
            if (isFloat(resultType) && !isFloat(rightType)) {
                code.append("    i2f\n");
            }
            code.append("    ").append(getArithmeticInstruction(e.op(), resultType)).append("\n");
        }

        return null;
    }

    @Override
    public Void visit(UnaryExp e) {
        e.exp().accept(this);
        if (e.op().equals("-")) {
            code.append("    ").append(isFloat(e.getType()) ? "fneg" : "ineg").append("\n");
        } else if (e.op().equals("!")) {
            String trueLabel = newLabel();
            String endLabel = newLabel();
            code.append("    ifeq ").append(trueLabel).append("\n");
            code.append("    iconst_0\n");
            code.append("    goto ").append(endLabel).append("\n");
            code.append(trueLabel).append(":\n");
            code.append("    iconst_1\n");
            code.append(endLabel).append(":\n");
        }
        return null;
    }

    @Override
    public Void visit(NewExp e) {
        if (e.size().isPresent()) { // Caso: Criação de Array
            e.size().get().accept(this); // Empilha o tamanho do array (int)

            // O tipo de uma expressão 'new T[s]' é ArrayTypeNode.
            // Precisamos olhar para o TIPO DO ELEMENTO para decidir a instrução.
            TypeNode elementType = ((ArrayTypeNode) e.typeNode()).elementType();

            if (isRef(elementType)) {
                // Se o elemento é uma referência (outro array ou um registro como 'Transition'),
                // usamos 'anewarray'.
                code.append("    anewarray ").append(typeToDescriptor(elementType)).append("\n");
            } else {
                // Se o elemento é um tipo primitivo ('Int', 'Float', 'Char', 'Bool'),
                // usamos 'newarray'.
                String primitiveName = getBaseTypeNameForArray(((BaseTypeNode) elementType).typeName());
                code.append("    newarray ").append(primitiveName).append("\n");
            }

        } else { // Caso: Criação de Registro
            String typeName = ((BaseTypeNode) e.typeNode()).typeName();
            code.append("    new ").append(typeName).append("\n");
            code.append("    dup\n");
            code.append("    invokespecial ").append(typeName).append("/<init>()V\n");
        }
        return null;
    }

    @Override
    public Void visit(FunCallExp e) {
        for (Exp arg : e.args()) {
            arg.accept(this);
        }

        FunDecl func = functionsContext.get(e.name());
        if (func == null) {
            throw new RuntimeException("Erro de geração: Função '" + e.name() + "' não encontrada no contexto.");
        }

        // --- LÓGICA DE CHAMADA CORRIGIDA ---
        String ownerClass = findFunctionOwnerClass(e.name());

        String paramsDescriptor = func.params().stream().map(p -> typeToDescriptor(p.type())).collect(Collectors.joining());
        String returnDescriptor = getReturnDescriptor(func.returnTypes());
        String methodSignature = ownerClass + "/" + e.name() + "(" + paramsDescriptor + ")" + returnDescriptor;
        code.append("    invokestatic ").append(methodSignature).append("\n");
        // --- FIM DA CORREÇÃO ---

        if (e.returnIndex().isPresent()) {
            // O resto da lógica para indexar o valor de retorno permanece o mesmo.
            if (func.returnTypes().size() > 1) {
                e.returnIndex().get().accept(this);
                code.append("    aaload\n");
                unboxIfPrimitive(e.getType());
            }
        }
        return null;
    }

    @Override
    public Void visit(VarAccessExp e) {
        int slot = varMap.get(e.name());
        code.append("    ").append(getLoadInstruction(e.getType())).append(" ").append(slot).append("\n");
        return null;
    }

    @Override
    public Void visit(FieldAccessExp e) {
        e.recordExp().accept(this);
        String owner = ((BaseTypeNode) e.recordExp().getType()).typeName();
        String fieldName = e.fieldName();
        String descriptor = typeToDescriptor(e.getType());
        code.append("    getfield ").append(owner).append("/").append(fieldName).append(" ").append(descriptor).append("\n");
        return null;
    }

    @Override
    public Void visit(ArrayAccessExp e) {
        e.arrayExp().accept(this);
        e.indexExp().accept(this);
        code.append("    ").append(getArrayLoadInstruction(e.getType())).append("\n");
        return null;
    }

    @Override public Void visit(IntLiteralExp e) { pushInt(e.value()); return null; }
    @Override public Void visit(FloatLiteralExp e) { code.append("    ldc ").append(e.value()).append("\n"); return null; }
    @Override public Void visit(CharLiteralExp e) { pushInt(e.value()); return null; }
    @Override public Void visit(BoolLiteralExp e) { pushInt(e.value() ? 1 : 0); return null; }
    @Override public Void visit(NullLiteralExp e) { code.append("    aconst_null\n"); return null; }
    @Override
    public Void visit(ReadCmd c) {
        // Cria um novo objeto java.util.Scanner se for a primeira leitura
        // ou reutiliza o existente. (Uma implementação mais simples pode criar um novo a cada vez)
        // Para simplificar, vamos criar um novo a cada chamada:
        code.append("    new java/util/Scanner\n");
        code.append("    dup\n");
        code.append("    getstatic java/lang/System/in Ljava/io/InputStream;\n");
        code.append("    invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V\n");

        // Chama o método nextInt() do Scanner, que retorna um inteiro e o deixa na pilha.
        // Uma implementação mais robusta usaria o tipo anotado para chamar nextInt(), nextFloat(), etc.
        code.append("    invokevirtual java/util/Scanner/nextInt()I\n");

        // Pega o valor inteiro do topo da pilha e o armazena na variável correta.
        LValue lvalue = c.lvalue();
        if (lvalue instanceof VarAccessExp vae) {
            int slot = getVarSlot(vae.name());
            code.append("    istore ").append(slot).append("\n");
        } else {
            // Implementar lógica para outros lvalues (campos, arrays) se necessário.
            // Por exemplo, para um array:
            if (lvalue instanceof ArrayAccessExp aae) {
                int tempValSlot = getVarSlot("~temp_read" + labelCounter);
                code.append("    istore ").append(tempValSlot).append("\n"); // Armazena o int lido
                aae.arrayExp().accept(this);    // Empilha arrayref
                aae.indexExp().accept(this);    // Empilha index
                code.append("    iload ").append(tempValSlot).append("\n");    // Empilha o int lido de volta
                code.append("    iastore\n");
            }
        }
        return null;
    }
    @Override public Void visit(DataDecl d) { return null; }
    @Override public Void visit(BaseTypeNode n) { return null; }
    @Override public Void visit(ArrayTypeNode n) { return null; }
    @Override public Void visit(NullTypeNode n) { return null; }

    // --- Métodos Auxiliares ---
    private int getVarSlot(String name) { if (!varMap.containsKey(name)) { varMap.put(name, nextVarSlot++); } return varMap.get(name); }
    private String typeToDescriptor(TypeNode type) {
        if (type instanceof BaseTypeNode btn) {
            return switch (btn.typeName()) {
                case "Int" -> "I"; case "Bool" -> "Z"; case "Char" -> "C"; case "Float" -> "F";
                default -> "L" + btn.typeName() + ";";
            };
        } else if (type instanceof ArrayTypeNode atn) {
            return "[" + typeToDescriptor(atn.elementType());
        }
        return "V";
    }
    private String getReturnDescriptor(List<TypeNode> returnTypes) {
        if (returnTypes.isEmpty()) return "V";
        if (returnTypes.size() == 1) return typeToDescriptor(returnTypes.get(0));
        return "[Ljava/lang/Object;";
    }
    private String getLoadInstruction(TypeNode type) {
        if (isFloat(type)) return "fload";
        if (isRef(type)) return "aload";
        return "iload";
    }
    private String getStoreInstruction(TypeNode type) {
        if (isFloat(type)) return "fstore";
        if (isRef(type)) return "astore";
        return "istore";
    }
    private String getArrayLoadInstruction(TypeNode type) {
        if (isFloat(type)) return "faload";
        if (isRef(type)) return "aaload";
        if (isChar(type)) return "caload";
        if (isBool(type)) return "baload";
        return "iaload";
    }
    private String getArrayStoreInstruction(TypeNode type) {
        if (isFloat(type)) return "fastore";
        if (isRef(type)) return "aastore";
        if (isChar(type)) return "castore";
        if (isBool(type)) return "bastore";
        return "iastore";
    }
    private String getArithmeticInstruction(String op, TypeNode type) {
        boolean isFloat = isFloat(type);
        return switch (op) {
            case "+" -> isFloat ? "fadd" : "iadd";
            case "-" -> isFloat ? "fsub" : "isub";
            case "*" -> isFloat ? "fmul" : "imul";
            case "/" -> isFloat ? "fdiv" : "idiv";
            case "%" -> "irem";
            default -> "";
        };
    }
    private String getJumpInstruction(String op, TypeNode type) {
        boolean isRef = isRef(type);
        return switch (op) {
            case "==" -> isRef ? "if_acmpeq" : "if_icmpeq";
            case "!=" -> isRef ? "if_acmpne" : "if_icmpne";
            case "<" -> "if_icmplt";
            default -> "";
        };
    }
    private String getBaseTypeNameForArray(String typeName) {
        return switch (typeName) {
            case "Int" -> "int"; case "Bool" -> "boolean"; case "Char" -> "char"; case "Float" -> "float";
            default -> typeName;
        };
    }
    private void pushInt(int value) {
        if (value >= -1 && value <= 5) {
            code.append("    iconst_").append(value == -1 ? "m1" : value).append("\n");
        } else if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
            code.append("    bipush ").append(value).append("\n");
        } else if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
            code.append("    sipush ").append(value).append("\n");
        } else {
            code.append("    ldc ").append(value).append("\n");
        }
    }
    private void boxIfPrimitive(TypeNode type) {
        if (type instanceof BaseTypeNode btn) {
            switch (btn.typeName()) {
                case "Int" -> code.append("    invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n");
                case "Bool" -> code.append("    invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n");
                case "Char" -> code.append("    invokestatic java/lang/Character/valueOf(C)Ljava/lang/Character;\n");
                case "Float" -> code.append("    invokestatic java/lang/Float/valueOf(F)Ljava/lang/Float;\n");
            }
        }
    }
    private void unboxIfPrimitive(TypeNode type) {
        if (type instanceof BaseTypeNode btn) {
            switch (btn.typeName()) {
                case "Int" -> {
                    code.append("    checkcast java/lang/Integer\n");
                    code.append("    invokevirtual java/lang/Integer/intValue()I\n");
                }
                case "Bool" -> {
                    code.append("    checkcast java/lang/Boolean\n");
                    code.append("    invokevirtual java/lang/Boolean/booleanValue()Z\n");
                }
                case "Char" -> {
                    code.append("    checkcast java/lang/Character\n");
                    code.append("    invokevirtual java/lang/Character/charValue()C\n");
                }
                case "Float" -> {
                    code.append("    checkcast java/lang/Float\n");
                    code.append("    invokevirtual java/lang/Float/floatValue()F\n");
                }
            }
        }
    }
    private boolean isFloat(TypeNode t) { return t instanceof BaseTypeNode btn && btn.typeName().equals("Float"); }
    private boolean isChar(TypeNode t) { return t instanceof BaseTypeNode btn && btn.typeName().equals("Char"); }
    private boolean isBool(TypeNode t) { return t instanceof BaseTypeNode btn && btn.typeName().equals("Bool"); }
    private boolean isRef(TypeNode t) { return t instanceof ArrayTypeNode || (t instanceof BaseTypeNode && dataDeclarations.containsKey(((BaseTypeNode)t).typeName())); }
    private boolean isRelational(String op) { return op.equals("==") || op.equals("!=") || op.equals("<"); }
    private String getReturnInstruction(TypeNode type) {
        if (isFloat(type)) return "freturn";
        if (isRef(type)) return "areturn";
        if (type instanceof NullTypeNode || isRef(type)) {
            return "areturn";
        }
        return "ireturn"; // Para Int, Bool e Char
    }
}
