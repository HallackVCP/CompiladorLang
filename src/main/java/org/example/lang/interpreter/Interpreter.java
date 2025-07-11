package org.example.lang.interpreter;

import org.example.lang.Exception.InterpreterException;
import org.example.lang.ast.type.ArrayTypeNode;
import org.example.lang.ast.type.BaseTypeNode;
import org.example.lang.interpreter.value.*;
import org.example.lang.ast.*;
import org.example.lang.ast.cmd.*;
import org.example.lang.ast.decl.*;
import org.example.lang.ast.exp.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

public class Interpreter implements Visitor<Value> {
    private final Environment environment = new Environment();
    private final Scanner inputScanner = new Scanner(System.in);
    private final Map<String, DataDecl> dataDeclarations = new HashMap<>();

    public void interpret(Program program) {
        program.accept(this);
    }

    @Override
    public Value visit(BaseTypeNode n) {
        return null;
    }

    @Override
    public Value visit(ArrayTypeNode n) {
        return null;
    }

    @Override
    public Value visit(Program p) {
        // Primeira passagem: registrar todas as declarações de tipo e função
        for (Decl decl : p.decls()) {
            decl.accept(this);
        }

        Value mainFunc = environment.get("main");
        if (mainFunc instanceof FunctionValue fv) {
            if (fv.decl().params().isEmpty()) {
                executeFunction(fv, new ArrayList<>());
            } else {
                throw new InterpreterException("Erro: A função 'main' não deve ter parâmetros.");
            }
        } else {
            throw new InterpreterException("Erro: Função 'main' não encontrada.");
        }
        return new VoidValue();
    }

    private Value executeFunction(FunctionValue func, List<Value> argValues) {
        if (func.decl().params().size() != argValues.size()) {
            throw new InterpreterException("Erro: número incorreto de argumentos para a função " + func.decl().name());
        }
        environment.pushScope();
        for (int i = 0; i < func.decl().params().size(); i++) {
            environment.define(func.decl().params().get(i).name(), argValues.get(i));
        }

        Value result = func.decl().body().accept(this);
        environment.popScope();

        if (result instanceof ReturnValue rv) {
            // Retorna a lista de valores, que será tratada pelo chamador
            return new ListValue(rv.values());
        }
        // Funções sem 'return' explícito retornam uma lista vazia.
        return new ListValue(Collections.emptyList());
    }

    // --- Visitantes de Declaração ---
    @Override
    public Value visit(DataDecl d) {
        dataDeclarations.put(d.name(), d);
        for(FunDecl fun : d.functions()){
            fun.accept(this); // Registrar funções internas
        }
        return new VoidValue();
    }

    @Override
    public Value visit(FunDecl d) {
        environment.define(d.name(), new FunctionValue(d));
        return new VoidValue();
    }



    // --- Visitantes de Comando ---
    @Override
    public Value visit(BlockCmd c) {
        environment.pushScope();
        for (Cmd cmd : c.cmds()) {
            Value result = cmd.accept(this);
            if (result instanceof ReturnValue) {
                environment.popScope();
                return result;
            }
        }
        environment.popScope();
        return new VoidValue();
    }

    @Override
    public Value visit(IfCmd c) {
        Value condition = c.condition().accept(this);
        if (!(condition instanceof BoolValue)) {
            throw new InterpreterException("Erro: condição do if deve ser booleana.");
        }

        if (((BoolValue) condition).value()) {
            return c.thenBranch().accept(this);
        } else if (c.elseBranch() != null) {
            return c.elseBranch().accept(this);
        }
        return new VoidValue();
    }


    @Override
    public Value visit(ReadCmd c) {
        System.out.print("Entrada para " + ((VarAccessExp)c.lvalue()).name() + ": ");
        int val = inputScanner.nextInt();
        environment.assign(((VarAccessExp)c.lvalue()).name(), new IntValue(val));
        return new VoidValue();
    }

    @Override
    public Value visit(IterateCmd c) {
        Value collection = c.collection().accept(this);

        if (c.var().isPresent()) { // Forma: iterate(id: collection)
            String varName = c.var().get();
            List<Value> items = new ArrayList<>();
            if (collection instanceof ArrayValue av) {
                items.addAll(av.elements());
            } else if (collection instanceof IntValue iv) { // `iterate(i: 5)` itera de 0 a 4
                for (int i = 0; i < iv.value(); i++) {
                    items.add(new IntValue(i));
                }
            } else { throw new InterpreterException("Iteração com variável só suporta arrays ou inteiros."); }

            for (Value item : items) {
                environment.pushScope();
                environment.define(varName, item);
                Value result = c.body().accept(this);
                environment.popScope();
                if (result instanceof ReturnValue) return result;
            }
        } else { // Forma: iterate(exp)
            if (collection instanceof IntValue iv) {
                for (int i = 0; i < iv.value(); i++) {
                    Value result = c.body().accept(this);
                    if (result instanceof ReturnValue) return result;
                }
            } else { throw new InterpreterException("Iteração sem variável requer um valor inteiro."); }
        }
        return new VoidValue();
    }

    @Override
    public Value visit(ProcCallCmd c) {
        Value func = environment.get(c.name());
        if (!(func instanceof FunctionValue)) {
            throw new InterpreterException("Erro: '" + c.name() + "' não é uma função.");
        }
        List<Value> argValues = new ArrayList<>();
        c.args().forEach(arg -> argValues.add(arg.accept(this)));

        executeFunction((FunctionValue)func, argValues); // O valor de retorno é descartado
        return new VoidValue();
    }

    @Override
    public Value visit(FuncCallAssignCmd c) {
        Value result = c.call().accept(this);
        if (!(result instanceof ListValue)) {
            throw new InterpreterException("O retorno da função não foi uma lista de valores.");
        }
        List<Value> returnedValues = ((ListValue)result).values();
        List<LValue> lvalues = c.lvalues();

        if (returnedValues.size() != lvalues.size()) {
            throw new InterpreterException("Número de valores de retorno (" + returnedValues.size() + ") é diferente do número de variáveis de atribuição (" + lvalues.size() + ").");
        }

        for (int i = 0; i < returnedValues.size(); i++) {
            String varName = ((VarAccessExp)lvalues.get(i)).name();
            environment.assign(varName, returnedValues.get(i));
        }
        return new VoidValue();
    }

    @Override
    public Value visit(PrintCmd c) {
        System.out.println(c.exp().accept(this).toString());
        return new VoidValue();
    }

    @Override
    public Value visit(ReturnCmd c) {
        List<Value> values = new ArrayList<>();
        c.exps().forEach(exp -> values.add(exp.accept(this)));
        return new ReturnValue(values);
    }

    // --- Visitantes de Expressão ---

    @Override
    public Value visit(BinOpExp e) {
        Value left = e.left().accept(this);
        Value right = e.right().accept(this);
        if (left instanceof NullValue || right instanceof NullValue) {
            if (e.op().equals("+") || e.op().equals("-") || e.op().equals("*") || e.op().equals("/") || e.op().equals("%")) {
                throw new InterpreterException("Operações aritméticas não podem ser realizadas com null.");
            }
            if (e.op().equals("<")) {
                throw new InterpreterException("Comparação com null não é suportada.");
            }
        }
        if (e.op().equals("==")) {
            boolean leftIsNull = left instanceof NullValue;
            boolean rightIsNull = right instanceof NullValue;
            if (leftIsNull || rightIsNull) {
                return new BoolValue(leftIsNull == rightIsNull);
            }
        } else if (e.op().equals("!=")) {
            boolean leftIsNull = left instanceof NullValue;
            boolean rightIsNull = right instanceof NullValue;
            if (leftIsNull || rightIsNull) {
                return new BoolValue(leftIsNull != rightIsNull);
            }
        }

        if (left instanceof IntValue && right instanceof IntValue) {
            int l = ((IntValue) left).value();
            int r = ((IntValue) right).value();
            return switch (e.op()) {
                case "+" -> new IntValue(l + r);
                case "-" -> new IntValue(l - r);
                case "*" -> new IntValue(l * r);
                case "/" -> new IntValue(l / r);
                case "%" -> new IntValue(l % r);
                case "<" -> new BoolValue(l < r);
                case "==" -> new BoolValue(l == r);
                case "!=" -> new BoolValue(l != r);
                default -> throw new InterpreterException("Operador binário '" + e.op() + "' inválido para inteiros.");
            };
        }
        if ((left instanceof IntValue || left instanceof FloatValue) && (right instanceof IntValue || right instanceof FloatValue)) {
            float l = (left instanceof IntValue) ? ((IntValue) left).value() : ((FloatValue) left).value();
            float r = (right instanceof IntValue) ? ((IntValue) right).value() : ((FloatValue) right).value();
            return switch (e.op()) {
                case "+" -> new FloatValue(l + r);
                case "-" -> new FloatValue(l - r);
                case "*" -> new FloatValue(l * r);
                case "/" -> new FloatValue(l / r);
                case "<" -> new BoolValue(l < r);
                case "==" -> new BoolValue(l == r);
                case "!=" -> new BoolValue(l != r);
                default -> throw new InterpreterException("Operador binário '" + e.op() + "' inválido para números.");
            };
        }
        else if (left instanceof CharValue && right instanceof CharValue) {
            char l = ((CharValue) left).value();
            char r = ((CharValue) right).value();
            return switch (e.op()) {
                case "==" -> new BoolValue(l == r);
                case "!=" -> new BoolValue(l != r);
                case "<" -> new BoolValue(l < r); // Compara os valores ASCII/Unicode
                default -> throw new InterpreterException("Operador binário '" + e.op() + "' inválido para caracteres.");
            };
        }
        if (left instanceof BoolValue && right instanceof BoolValue) {
            boolean l = ((BoolValue) left).value();
            boolean r = ((BoolValue) right).value();
            return switch (e.op()) {
                case "&&" -> new BoolValue(l && r);
                case "==" -> new BoolValue(l == r);
                case "!=" -> new BoolValue(l != r);
                default -> throw new InterpreterException("Operador binário '" + e.op() + "' inválido para booleanos.");
            };
        }

        throw new InterpreterException("Operação binária não suportada para os tipos dados: " + left.getClass().getSimpleName() + " " + e.op() + " " + right.getClass().getSimpleName());
    }

    @Override
    public Value visit(FunCallExp e) {
        Value func = environment.get(e.name());
        if (!(func instanceof FunctionValue)) { throw new InterpreterException("'" + e.name() + "' não é uma função."); }

        List<Value> argValues = new ArrayList<>();
        e.args().forEach(arg -> argValues.add(arg.accept(this)));

        Value result = executeFunction((FunctionValue)func, argValues);

        if (e.returnIndex().isPresent()) {
            if (!(result instanceof ListValue)) {
                throw new InterpreterException("Retorno de função não é uma lista, não pode ser indexado.");
            }
            List<Value> values = ((ListValue)result).values();
            Value indexVal = e.returnIndex().get().accept(this);
            int index = ((IntValue)indexVal).value();
            if (index < 0 || index >= values.size()) {
                throw new InterpreterException("Índice de retorno fora dos limites: " + index);
            }
            return values.get(index);
        } else {
            // Se não há índice, é porque a chamada é parte de um FuncCallAssignCmd, retornamos a lista.
            return result;
        }
    }

    @Override public Value visit(IntLiteralExp e) { return new IntValue(e.value()); }
    @Override public Value visit(BoolLiteralExp e) { return new BoolValue(e.value()); }
    @Override public Value visit(NullLiteralExp e) { return new NullValue(); }
    @Override public Value visit(VarAccessExp e) { return environment.get(e.name()); }

    @Override
    public Value visit(FloatLiteralExp e) {
        return new FloatValue(e.value());
    }

    @Override
    public Value visit(CharLiteralExp e) {
        return new CharValue(e.value());
    }

    // --- Visitantes de Declarações ---


    // --- Visitantes de Comandos ---

    @Override
    public Value visit(AssignCmd c) {
        LValue lvalue = c.lvalue();
        Value valueToAssign = c.exp().accept(this);

        if (lvalue instanceof VarAccessExp va) {
            environment.assign(va.name(), valueToAssign);
        } else if (lvalue instanceof FieldAccessExp fa) {
            Value record = fa.recordExp().accept(this);
            if (record instanceof RecordValue rv) {
                rv.fields().put(fa.fieldName(), valueToAssign);
            } else { throw new InterpreterException("Atribuição a campo de um não-registro."); }
        } else if (lvalue instanceof ArrayAccessExp aa) {
            Value array = aa.arrayExp().accept(this);
            Value index = aa.indexExp().accept(this);
            if (array instanceof ArrayValue av && index instanceof IntValue iv) {
                av.elements().set(iv.value(), valueToAssign);
            } else { throw new InterpreterException("Atribuição a elemento de array inválida."); }
        }
        return new VoidValue();
    }


    // --- Visitantes de Expressões ---

    @Override
    public Value visit(UnaryExp e) {
        Value val = e.exp().accept(this);
        if (e.op().equals("-")) {
            if (val instanceof IntValue iv) return new IntValue(-iv.value());
            if (val instanceof FloatValue fv) return new FloatValue(-fv.value());
        }
        if (e.op().equals("!") && val instanceof BoolValue bv) {
            return new BoolValue(!bv.value());
        }
        throw new InterpreterException("Operador unário '" + e.op() + "' não aplicável a " + val.getClass().getSimpleName());
    }

    @Override
    public Value visit(NewExp e) {
        if (e.type() instanceof BaseTypeNode btn) { // Ex: new Racional
            DataDecl decl = dataDeclarations.get(btn.typeName());
            if (decl == null) throw new InterpreterException("Tipo '" + btn.typeName() + "' não definido.");

            Map<String, Value> fields = new HashMap<>();
            for (DataDecl.Field field : decl.fields()) {
                // Inicializa campos com valores padrão (null)
                fields.put(field.name(), new NullValue());
            }
            return new RecordValue(btn.typeName(), fields);

        } else if (e.type() instanceof ArrayTypeNode) { // Ex: new Int[10]
            if (e.size().isEmpty()) throw new InterpreterException("Tamanho do array não especificado.");

            Value sizeVal = e.size().get().accept(this);
            if (!(sizeVal instanceof IntValue iv)) throw new InterpreterException("Tamanho do array deve ser inteiro.");

            List<Value> elements = new ArrayList<>(Collections.nCopies(iv.value(), new NullValue()));
            return new ArrayValue(elements);
        }
        throw new InterpreterException("Tipo inválido para 'new'.");
    }

    @Override
    public Value visit(FieldAccessExp e) {
        Value record = e.recordExp().accept(this);
        if (record instanceof RecordValue rv) {
            return rv.fields().get(e.fieldName());
        }
        throw new InterpreterException("Tentativa de acessar campo em um não-registro.");
    }

    @Override
    public Value visit(ArrayAccessExp e) {
        Value array = e.arrayExp().accept(this);
        Value index = e.indexExp().accept(this);
        if (array instanceof ArrayValue av && index instanceof IntValue iv) {
            if (iv.value() < 0 || iv.value() >= av.elements().size()) {
                throw new InterpreterException("Acesso a array fora dos limites.");
            }
            return av.elements().get(iv.value());
        }
        throw new InterpreterException("Acesso a array inválido.");
    }
}