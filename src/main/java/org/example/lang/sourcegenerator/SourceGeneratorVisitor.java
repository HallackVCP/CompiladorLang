package org.example.lang.sourcegenerator;

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
import org.example.lang.ast.exp.NewExp;
import org.example.lang.ast.exp.NullLiteralExp;
import org.example.lang.ast.exp.UnaryExp;
import org.example.lang.ast.exp.VarAccessExp;
import org.example.lang.ast.type.ArrayTypeNode;
import org.example.lang.ast.type.BaseTypeNode;
import org.example.lang.ast.type.NullTypeNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

/**
 * Visitor que percorre a AST e gera código Python equivalente.
 * Implementa a diretiva -src (source-to-source).
 */
public class SourceGeneratorVisitor implements Visitor<String> {

    private int indentationLevel = 0;
    private final String indentationUnit = "    "; // 4 espaços para indentação

    private final Map<String, FunDecl> functionsContext = new HashMap<>();

    private static final Set<String> PYTHON_KEYWORDS = Set.of(
            "and", "or", "not", "if", "else", "elif", "for", "while", "break",
            "continue", "return", "in", "is", "def", "class", "try", "except",
            "finally", "with", "as", "import", "from", "pass", "None", "True", "False"
    );

    private String mangle(String name) {
        if (PYTHON_KEYWORDS.contains(name)) {
            return name + "_"; // Adiciona um underscore
        }
        return name;
    }

    public String generate(Program program) {
        collectSymbols(program);
        return program.accept(this);
    }

    private String indent() {
        return indentationUnit.repeat(indentationLevel);
    }

    private void increaseIndent() {
        indentationLevel++;
    }

    private void decreaseIndent() {
        indentationLevel--;
    }

    private void collectSymbols(Program program) {
        for (Decl decl : program.decls()) {
            if (decl instanceof FunDecl f) {
                functionsContext.put(f.name(), f);
            } else if (decl instanceof DataDecl d) {
                // Coleta funções aninhadas em 'data'
                for (FunDecl fun : d.functions()) {
                    functionsContext.put(fun.name(), fun);
                }
            }
        }
    }

    // --- Métodos de Visita ---

    @Override
    public String visit(Program p) {
        StringBuilder sb = new StringBuilder();
        for (Decl decl : p.decls()) {
            sb.append(decl.accept(this));
            sb.append("\n\n"); // Duas linhas em branco entre declarações de topo
        }
        sb.append("if __name__ == \"__main__\":\n");
        sb.append(indentationUnit).append("main()\n");
        return sb.toString();
    }

    @Override
    public String visit(DataDecl d) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent()).append("class ").append(d.name()).append(":\n");
        increaseIndent();
        sb.append(indent()).append("def __init__(self):\n");
        increaseIndent();
        if (d.fields().isEmpty()) {
            sb.append(indent()).append("pass\n");
        } else {
            for (DataDecl.Field field : d.fields()) {
                sb.append(indent()).append("self.").append(field.name()).append(" = None\n");
            }
        }
        decreaseIndent();
        decreaseIndent();
        for (FunDecl fun : d.functions()) {
            sb.append("\n").append(fun.accept(this));
        }
        return sb.toString();
    }

    @Override
    public String visit(FunDecl d) {
        StringBuilder sb = new StringBuilder();
        String params = d.params().stream()
                .map(FunDecl.Param::name)
                .collect(Collectors.joining(", "));
        sb.append(indent()).append("def ").append(mangle(d.name())).append("(").append(params).append("):\n");
        increaseIndent();
        sb.append(d.body().accept(this));
        decreaseIndent();
        return sb.toString();
    }

    @Override
    public String visit(BlockCmd c) {
        StringBuilder sb = new StringBuilder();
        if (c.cmds().isEmpty()) {
            sb.append(indent()).append("pass\n");
        } else {
            for (Cmd cmd : c.cmds()) {
                sb.append(cmd.accept(this));
            }
        }
        return sb.toString();
    }

    @Override
    public String visit(IfCmd c) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent()).append("if ").append(c.condition().accept(this)).append(":\n");
        increaseIndent();
        sb.append(c.thenBranch().accept(this));
        decreaseIndent();
        if (c.elseBranch() != null) {
            sb.append(indent()).append("else:\n");
            increaseIndent();
            sb.append(c.elseBranch().accept(this));
            decreaseIndent();
        }
        return sb.toString();
    }

    @Override
    public String visit(AssignCmd c) {
        return indent() + c.lvalue().accept(this) + " = " + c.exp().accept(this) + "\n";
    }

    @Override
    public String visit(PrintCmd c) {
        return indent() + "print(" + c.exp().accept(this) + ", end='')\n";
    }

    @Override
    public String visit(ReadCmd c) {
        String varName = c.lvalue().accept(this);
        return indent() + varName + " = int(input(\"Entrada para " + varName + ": \"))\n";
    }

    @Override
    public String visit(ReturnCmd c) {
        String exps = c.exps().stream()
                .map(exp -> exp.accept(this))
                .collect(Collectors.joining(", "));
        return indent() + "return " + exps + "\n";
    }

    @Override
    public String visit(IterateCmd c) {
        StringBuilder sb = new StringBuilder();
        String varName = mangle(c.var().orElse("_"));
        Exp collection = c.collection();
        String collectionStr = collection.accept(this);

        TypeNode collectionType = collection.getType();

        if (collectionType instanceof ArrayTypeNode) {

            sb.append(indent()).append("for ").append(varName).append(" in ").append(collectionStr).append(":\n");
        } else {

            sb.append(indent()).append("for ").append(varName).append(" in range(").append(collectionStr).append("):\n");
        }

        increaseIndent();
        sb.append(c.body().accept(this));
        decreaseIndent();
        return sb.toString();
    }

    @Override
    public String visit(ProcCallCmd c) {
        String args = c.args().stream()
                .map(arg -> arg.accept(this))
                .collect(Collectors.joining(", "));
        return indent() + mangle(c.name()) + "(" + args + ")\n";
    }

    @Override
    public String visit(FuncCallAssignCmd c) {
        String lvalues = c.lvalues().stream()
                .map(lval -> lval.accept(this))
                .collect(Collectors.joining(", "));
        return indent() + lvalues + " = " + c.call().accept(this) + "\n";
    }

    // --- Expressões ---

    @Override
    public String visit(BinOpExp e) {
        String op = e.op();
        if (op.equals("&&")) op = "and";
        return "(" + e.left().accept(this) + " " + op + " " + e.right().accept(this) + ")";
    }

    @Override
    public String visit(UnaryExp e) {
        String op = e.op();
        if (op.equals("!")) op = "not ";
        return "(" + op + e.exp().accept(this) + ")";
    }

    @Override
    public String visit(FunCallExp e) {
        FunDecl funcDecl = functionsContext.get(e.name());
        if (funcDecl == null) {
            String args = e.args().stream().map(arg -> arg.accept(this)).collect(Collectors.joining(", "));
            String baseCall = mangle(e.name()) + "(" + args + ")";
            if (e.returnIndex().isPresent()) {
                return baseCall + "[" + e.returnIndex().get().accept(this) + "]";
            }
            return baseCall;
        }
        String args = e.args().stream().map(arg -> arg.accept(this)).collect(Collectors.joining(", "));
        String baseCall =  mangle(e.name()) + "(" + args + ")";
        if (funcDecl.returnTypes().size() > 1) {
            if (e.returnIndex().isPresent()) {
                return baseCall + "[" + e.returnIndex().get().accept(this) + "]";
            }
            return baseCall;
        } else {
            return baseCall;
        }
    }

    @Override
    public String visit(NewExp e) {
        if (e.size().isPresent()) { // Array: new Int[10] -> [None] * 10
            return "[None] * (" + e.size().get().accept(this) + ")";
        } else { // Registro: new Ponto -> Ponto()
            return ((BaseTypeNode) e.getType()).typeName() + "()";
        }
    }

    @Override
    public String visit(VarAccessExp e) { return mangle(e.name()); }
    @Override
    public String visit(FieldAccessExp e) { return e.recordExp().accept(this) + "." + e.fieldName(); }
    @Override
    public String visit(ArrayAccessExp e) { return e.arrayExp().accept(this) + "[" + e.indexExp().accept(this) + "]"; }

    // --- Literais ---
    @Override
    public String visit(IntLiteralExp e) { return String.valueOf(e.value()); }
    @Override
    public String visit(FloatLiteralExp e) { return String.valueOf(e.value()); }
    @Override
    public String visit(CharLiteralExp e) {
        // Trata caracteres de escape para Python
        return switch (e.value()) {
            case '\n' -> "'\\n'";
            case '\t' -> "'\\t'";
            case '\'' -> "'\\''";
            case '\\' -> "'\\\\'";
            default -> "'" + e.value() + "'";
        };
    }
    @Override
    public String visit(BoolLiteralExp e) { return e.value() ? "True" : "False"; }
    @Override
    public String visit(NullLiteralExp e) { return "None"; }

    // Métodos não utilizados na geração de código, mas necessários para a interface Visitor
    @Override public String visit(BaseTypeNode n) { return n.typeName(); }
    @Override public String visit(ArrayTypeNode n) { return n.elementType().accept(this) + "[]"; }
    @Override public String visit(NullTypeNode n) { return "None"; }
}
