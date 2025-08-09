package org.example.lang.semantica;

import org.example.lang.ast.Program;
import org.example.lang.ast.TypeNode;
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

/**
 * Visitor que percorre a AST após a verificação de tipos
 * e anota cada nó de expressão com seu tipo correspondente.
 * Esta informação é crucial para a fase de geração de código.
 */
public class TypeAnnotationVisitor extends TypeCheckerVisitor {

    public void annotate(Program program) {
        super.collectSymbols(program);
        program.accept(this);
    }

    // Sobrescreve todos os métodos que visitam uma expressão para
    // chamar a lógica do TypeChecker e depois anotar o nó com o tipo retornado.

    @Override
    public TypeNode visit(BinOpExp e) {
        // Chama a implementação do TypeCheckerVisitor para calcular o tipo
        TypeNode type = super.visit(e);
        // Anota o nó da AST com o tipo calculado
        e.setType(type);
        return type;
    }

    @Override
    public TypeNode visit(UnaryExp e) {
        TypeNode type = super.visit(e);
        e.setType(type);
        return type;
    }

    @Override
    public TypeNode visit(ArrayAccessExp e) {
        TypeNode type = super.visit(e);
        e.setType(type);
        return type;
    }

    @Override
    public TypeNode visit(FieldAccessExp e) {
        TypeNode type = super.visit(e);
        e.setType(type);
        return type;
    }

    @Override
    public TypeNode visit(FunCallExp e) {
        TypeNode type = super.visit(e);
        e.setType(type);
        return type;
    }

    @Override
    public TypeNode visit(NewExp e) {
        TypeNode type = super.visit(e);
        e.setType(type);
        return type;
    }

    @Override
    public TypeNode visit(VarAccessExp e) {
        TypeNode type = super.visit(e);
        e.setType(type);
        return type;
    }

    @Override
    public TypeNode visit(IntLiteralExp e) {
        TypeNode type = super.visit(e);
        e.setType(type);
        return type;
    }

    @Override
    public TypeNode visit(FloatLiteralExp e) {
        TypeNode type = super.visit(e);
        e.setType(type);
        return type;
    }

    @Override
    public TypeNode visit(CharLiteralExp e) {
        TypeNode type = super.visit(e);
        e.setType(type);
        return type;
    }

    @Override
    public TypeNode visit(BoolLiteralExp e) {
        TypeNode type = super.visit(e);
        e.setType(type);
        return type;
    }

    @Override
    public TypeNode visit(NullLiteralExp e) {
        TypeNode type = super.visit(e);
        e.setType(type);
        return type;
    }
}

