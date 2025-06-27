package org.example.lang.ast;

import org.example.lang.ast.cmd.*;
import org.example.lang.ast.decl.*;
import org.example.lang.ast.exp.*;

public interface Visitor<T> {
    T visit(Program p);
    T visit(FunDecl d);
    T visit(BlockCmd c);
    T visit(IfCmd c);
    T visit(PrintCmd c);
    T visit(ReturnCmd c);
    T visit(BinOpExp e);
    T visit(FunCallExp e);
    T visit(IntLiteralExp e);
    T visit(VarAccessExp e);
}
