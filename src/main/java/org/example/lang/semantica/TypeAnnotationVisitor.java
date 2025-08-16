package org.example.lang.semantica;

import org.example.lang.ast.Program;
import org.example.lang.ast.TypeNode;
import org.example.lang.ast.cmd.*;
import org.example.lang.ast.decl.FunDecl;
import org.example.lang.ast.exp.*;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */


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

    @Override
    public TypeNode visit(FuncCallAssignCmd c) {
        c.call().accept(this);
        FunDecl func = functionsContext.get(c.call().name());
        if (func != null && func.returnTypes().size() == c.lvalues().size()) {
            for (int i = 0; i < c.lvalues().size(); i++) {
                LValue lvalue = c.lvalues().get(i);
                TypeNode returnType = func.returnTypes().get(i);
                if (lvalue instanceof VarAccessExp vae) {
                    vae.setType(returnType);
                } else {
                    lvalue.accept(this);
                }
            }
        }
        return super.visit(c);
    }

    @Override
    public TypeNode visit(AssignCmd c) {
        TypeNode expType = c.exp().accept(this);
        if (c.lvalue() instanceof VarAccessExp vae) {
            vae.setType(expType);
        } else {
            c.lvalue().accept(this);
        }
        return super.visit(c);
    }
}

