package org.example.lang.interpreter;

import org.example.interpreter.value.*;
import org.example.lang.ast.*;
import org.example.lang.ast.cmd.*;
import org.example.lang.ast.decl.*;
import org.example.lang.ast.exp.*;
import org.example.lang.interpreter.value.*;

import java.util.ArrayList;
import java.util.List;

public class Interpreter implements Visitor<Value> {
    private final Environment environment = new Environment();

    public void interpret(Program program) {
        program.accept(this);
    }

    @Override
    public Value visit(Program p) {
        // Primeiro, declarar todas as funções no ambiente
        for (Decl decl : p.decls()) {
            decl.accept(this);
        }

        // Em seguida, encontrar e executar a função 'main'
        Value mainFunc = environment.get("main");
        if (mainFunc instanceof FunctionValue) {
            // A execução de um programa se dá pela avaliação da função main
            if (((FunctionValue) mainFunc).decl().params().isEmpty()) { // main não tem argumentos
                executeFunction((FunctionValue) mainFunc, new ArrayList<>());
            } else {
                throw new RuntimeException("Erro: A função 'main' não deve ter parâmetros.");
            }
        } else {
            throw new RuntimeException("Erro: Função 'main' não encontrada.");
        }
        return new VoidValue();
    }

    @Override
    public Value visit(FunDecl d) {
        // Armazena a declaração da função no ambiente para chamadas futuras
        environment.define(d.name(), new FunctionValue(d));
        return new VoidValue();
    }

    private Value executeFunction(FunctionValue func, List<Value> argValues) {
        environment.pushScope();
        List<FunDecl.Param> params = func.decl().params();
        for (int i = 0; i < params.size(); i++) {
            environment.define(params.get(i).name(), argValues.get(i));
        }

        Value result = func.decl().body().accept(this);

        environment.popScope();

        // Tratar o 'return' que interrompe o fluxo
        if (result instanceof ReturnValue) {
            return ((ReturnValue) result).value();
        }
        return new VoidValue();
    }

    @Override
    public Value visit(BlockCmd c) {
        environment.pushScope();
        for (Cmd cmd : c.cmds()) {
            Value result = cmd.accept(this);
            // Se um comando de retorno for encontrado, interrompa a execução do bloco
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
            throw new RuntimeException("Erro: condição do if deve ser booleana.");
        }

        if (((BoolValue) condition).value()) {
            return c.thenBranch().accept(this);
        } else if (c.elseBranch() != null) {
            return c.elseBranch().accept(this);
        }
        return new VoidValue();
    }

    @Override
    public Value visit(PrintCmd c) {
        Value val = c.exp().accept(this);
        // O comando print é seguido por uma expressão
        System.out.println(val.toString());
        return new VoidValue();
    }

    @Override
    public Value visit(ReturnCmd c) {
        return new ReturnValue(c.exp().accept(this));
    }

    @Override
    public Value visit(BinOpExp e) {
        Value left = e.left().accept(this);
        Value right = e.right().accept(this);

        if (left instanceof IntValue && right instanceof IntValue) {
            int l = ((IntValue) left).value();
            int r = ((IntValue) right).value();
            return switch (e.op()) {
                case "+" -> new IntValue(l + r);
                case "-" -> new IntValue(l - r);
                case "*" -> new IntValue(l * r);
                case "/" -> new IntValue(l / r);
                case "<" -> new BoolValue(l < r);
                default -> throw new RuntimeException("Operador binário desconhecido para inteiros: " + e.op());
            };
        }
        throw new RuntimeException("Operação binária não suportada para os tipos dados.");
    }

    @Override
    public Value visit(FunCallExp e) {
        Value func = environment.get(e.name());
        if (!(func instanceof FunctionValue)) {
            throw new RuntimeException("Erro: '" + e.name() + "' não é uma função.");
        }

        List<Value> argValues = new ArrayList<>();
        for (Exp arg : e.args()) {
            argValues.add(arg.accept(this));
        }

        Value result = executeFunction((FunctionValue) func, argValues);

        // Tratar o índice de retorno.
        Value indexVal = e.returnIndex().accept(this);
        if (!(indexVal instanceof IntValue)) {
            throw new RuntimeException("Índice de retorno da função deve ser um inteiro.");
        }
        int index = ((IntValue) indexVal).value();

        // Simplificação: o exemplo só tem um valor de retorno, no índice 0.
        if (index == 0) {
            return result;
        } else {
            throw new RuntimeException("Índice de retorno inválido: " + index);
        }
    }

    @Override
    public Value visit(IntLiteralExp e) {
        return new IntValue(e.value());
    }

    @Override
    public Value visit(VarAccessExp e) {
        return environment.get(e.name());
    }
}
