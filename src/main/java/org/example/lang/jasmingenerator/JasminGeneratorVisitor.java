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
 * Visitor que percorre a AST (já anotada com tipos) e gera código Jasmin.
 */
public class JasminGeneratorVisitor implements Visitor<Void> {

    private StringBuilder code;
    private final Map<String, DataDecl> dataDeclarations = new HashMap<>();
    private final String className;

    private Map<String, Integer> varMap;
    private int nextVarSlot;
    private int labelCounter;

    public JasminGeneratorVisitor(String className) {
        this.className = className;
    }

    public Map<String, String> generate(Program program) {
        Map<String, String> generatedFiles = new HashMap<>();

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
        return sb.toString();
    }

    private String newLabel() { return "L" + (labelCounter++); }

    // --- Métodos de Visita ---

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

        c.condition().accept(this);
        code.append("    ifeq ").append(elseLabel).append("\n");

        c.thenBranch().accept(this);
        code.append("    goto ").append(endLabel).append("\n");

        code.append(elseLabel).append(":\n");
        if (c.elseBranch() != null) {
            c.elseBranch().accept(this);
        }

        code.append(endLabel).append(":\n");
        return null;
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

        c.collection().accept(this);
        int counterSlot = getVarSlot("~iterator_counter" + labelCounter); // Nome único
        code.append("    istore ").append(counterSlot).append("\n");

        code.append(startLabel).append(":\n");
        code.append("    iload ").append(counterSlot).append("\n");
        code.append("    ifeq ").append(endLabel).append("\n");

        // Corpo do laço
        c.body().accept(this);

        // Decrementa o contador
        code.append("    iinc ").append(counterSlot).append(" -1\n");
        code.append("    goto ").append(startLabel).append("\n");

        code.append(endLabel).append(":\n");
        return null;
    }

    @Override
    public Void visit(ProcCallCmd c) {
        for (Exp arg : c.args()) {
            arg.accept(this);
        }
        FunDecl func = dataDeclarations.values().stream()
                .flatMap(d -> d.functions().stream())
                .filter(f -> f.name().equals(c.name()))
                .findFirst()
                .orElse(null);
        if (func == null) {
            // Lógica para funções globais se necessário
        }
        String paramsDescriptor = func.params().stream().map(p -> typeToDescriptor(p.type())).collect(Collectors.joining());
        String returnDescriptor = getReturnDescriptor(func.returnTypes());
        String methodSignature = className + "/" + c.name() + "(" + paramsDescriptor + ")" + returnDescriptor;
        code.append("    invokestatic ").append(methodSignature).append("\n");
        // Se a função retorna algo, precisamos remover o valor da pilha
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
        FunDecl func = dataDeclarations.values().stream()
                .flatMap(d -> d.functions().stream())
                .filter(f -> f.name().equals(c.call().name()))
                .findFirst()
                .orElse(null);

        String paramsDescriptor = func.params().stream().map(p -> typeToDescriptor(p.type())).collect(Collectors.joining());
        String returnDescriptor = getReturnDescriptor(func.returnTypes());
        String methodSignature = className + "/" + c.call().name() + "(" + paramsDescriptor + ")" + returnDescriptor;
        code.append("    invokestatic ").append(methodSignature).append("\n");

        if (func.returnTypes().size() > 1) {
            int arraySlot = getVarSlot("~return_array" + labelCounter);
            code.append("    astore ").append(arraySlot).append("\n");
            for (int i = 0; i < c.lvalues().size(); i++) {
                code.append("    aload ").append(arraySlot).append("\n");
                pushInt(i);
                code.append("    aaload\n");
                LValue lvalue = c.lvalues().get(i);
                unboxIfPrimitive(lvalue.getType());
                // Atribui o valor desempacotado
                if (lvalue instanceof VarAccessExp vae) {
                    int slot = getVarSlot(vae.name());
                    code.append("    ").append(getStoreInstruction(vae.getType())).append(" ").append(slot).append("\n");
                } // Adicionar lógica para outros lvalues
            }
        } else if (func.returnTypes().size() == 1) {
            LValue lvalue = c.lvalues().get(0);
            if (lvalue instanceof VarAccessExp vae) {
                int slot = getVarSlot(vae.name());
                code.append("    ").append(getStoreInstruction(vae.getType())).append(" ").append(slot).append("\n");
            } // Adicionar lógica para outros lvalues
        }
        return null;
    }

    // --- Expressões ---

    @Override
    public Void visit(BinOpExp e) {
        if (isRelational(e.op())) {
            String trueLabel = newLabel();
            String endLabel = newLabel();
            e.left().accept(this);
            e.right().accept(this);
            String jumpInstruction = getJumpInstruction(e.op(), e.left().getType());
            code.append("    ").append(jumpInstruction).append(" ").append(trueLabel).append("\n");
            code.append("    iconst_0\n"); // false
            code.append("    goto ").append(endLabel).append("\n");
            code.append(trueLabel).append(":\n");
            code.append("    iconst_1\n"); // true
            code.append(endLabel).append(":\n");
        } else {
            e.left().accept(this);
            e.right().accept(this);
            code.append("    ").append(getArithmeticInstruction(e.op(), e.getType())).append("\n");
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
        if (e.size().isPresent()) {
            e.size().get().accept(this);
            if (e.typeNode() instanceof BaseTypeNode btn) {
                code.append("    newarray ").append(getBaseTypeNameForArray(btn.typeName())).append("\n");
            } else {
                code.append("    anewarray ").append(typeToDescriptor(e.typeNode())).append("\n");
            }
        } else {
            String typeName = ((BaseTypeNode)e.typeNode()).typeName();
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

        FunDecl func = dataDeclarations.values().stream()
                .flatMap(d -> d.functions().stream())
                .filter(f -> f.name().equals(e.name()))
                .findFirst()
                .orElse(null);

        String paramsDescriptor = func.params().stream().map(p -> typeToDescriptor(p.type())).collect(Collectors.joining());
        String returnDescriptor = getReturnDescriptor(func.returnTypes());
        String methodSignature = className + "/" + e.name() + "(" + paramsDescriptor + ")" + returnDescriptor;
        code.append("    invokestatic ").append(methodSignature).append("\n");

        if (e.returnIndex().isPresent()) {
            if (func.returnTypes().size() > 1) {
                int index = ((IntLiteralExp) e.returnIndex().get()).value();
                pushInt(index);
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

    @Override public Void visit(DataDecl d) { return null; }
    @Override public Void visit(ReadCmd c) { /* Omitido */ return null; }
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
                // ... etc for Char and Float
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
        return "ireturn"; // Para Int, Bool e Char
    }
}
