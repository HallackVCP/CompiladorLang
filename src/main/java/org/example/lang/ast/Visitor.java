package org.example.lang.ast;

import org.example.lang.ast.cmd.*;
import org.example.lang.ast.decl.*;
import org.example.lang.ast.exp.*;
import org.example.lang.ast.type.ArrayTypeNode;
import org.example.lang.ast.type.BaseTypeNode;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

public interface Visitor<T> {
    // Nós de Tipo
    T visit(BaseTypeNode n);
    T visit(ArrayTypeNode n);

    // Nó Raiz
    T visit(Program p);

    // Declarações
    T visit(DataDecl d);
    T visit(FunDecl d);

    // Comandos
    T visit(BlockCmd c);
    T visit(IfCmd c);
    T visit(PrintCmd c);
    T visit(ReturnCmd c);
    T visit(AssignCmd c);
    T visit(ReadCmd c);
    T visit(IterateCmd c);
    T visit(ProcCallCmd c);
    T visit(FuncCallAssignCmd c);

    // Expressões
    T visit(BinOpExp e);
    T visit(UnaryExp e);
    T visit(NewExp e);
    T visit(FunCallExp e);
    T visit(IntLiteralExp e);
    T visit(FloatLiteralExp e);
    T visit(CharLiteralExp e);
    T visit(BoolLiteralExp e);
    T visit(NullLiteralExp e);
    T visit(VarAccessExp e);
    T visit(FieldAccessExp e);
    T visit(ArrayAccessExp e);
}
