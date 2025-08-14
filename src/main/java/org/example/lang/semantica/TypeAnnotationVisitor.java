package org.example.lang.semantica;

import org.example.lang.ast.Program;
import org.example.lang.ast.TypeNode;
import org.example.lang.ast.cmd.*;
import org.example.lang.ast.decl.FunDecl;
import org.example.lang.ast.exp.*;

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
//    @Override
//    public TypeNode visit(AssignCmd c) {
//        // 1. Visita os filhos PRIMEIRO para garantir que eles sejam anotados.
//        //    A chamada abaixo irá disparar o visit(VarAccessExp) e o visit(NewExp)
//        //    sobrescritos nesta classe, que irão anotar os nós.
//        c.lvalue().accept(this);
//        c.exp().accept(this);
//
//        // 2. Depois de anotar, chama a lógica da classe pai para realizar a validação de tipo.
//        return super.visit(c);
//    }
//    // Para o comando 'if'
//    @Override
//    public TypeNode visit(IfCmd c) {
//        c.condition().accept(this); // Anota a expressão da condição
//        c.thenBranch().accept(this);
//        if (c.elseBranch() != null) {
//            c.elseBranch().accept(this);
//        }
//        return super.visit(c); // Valida
//    }

    // Para o comando 'print'
//    @Override
//    public TypeNode visit(PrintCmd c) {
//        c.exp().accept(this); // Anota a expressão a ser impressa
//        return super.visit(c); // Valida
//    }
//
//    // Para o comando 'return'
//    @Override
//    public TypeNode visit(ReturnCmd c) {
//        for (Exp exp : c.exps()) {
//            exp.accept(this); // Anota cada expressão de retorno
//        }
//        return super.visit(c); // Valida
//    }

    // Para o comando 'iterate'
//    @Override
//    public TypeNode visit(IterateCmd c) {
//        c.collection().accept(this); // Anota a expressão da coleção
//        c.body().accept(this);
//        return super.visit(c); // Valida
//    }
//
//    // Para o comando 'read'
//    @Override
//    public TypeNode visit(ReadCmd c) {
//        c.lvalue().accept(this); // Anota o lvalue
//        return super.visit(c); // Valida
//    }
//
//    // Para chamadas de procedimento
//    @Override
//    public TypeNode visit(ProcCallCmd c) {
//        for (Exp arg : c.args()) {
//            arg.accept(this); // Anota cada argumento
//        }
//        return super.visit(c); // Valida
//    }

    // Para atribuição de múltiplos retornos
    @Override
    public TypeNode visit(FuncCallAssignCmd c) {
        // 1. Visita a chamada de função para anotar a chamada em si e seus argumentos.
        c.call().accept(this);

        // 2. Anota os lvalues com base nos tipos de retorno da função.
        FunDecl func = functionsContext.get(c.call().name());
        // Garante que a função foi encontrada e os tamanhos correspondem antes de anotar.
        if (func != null && func.returnTypes().size() == c.lvalues().size()) {
            for (int i = 0; i < c.lvalues().size(); i++) {
                LValue lvalue = c.lvalues().get(i);
                TypeNode returnType = func.returnTypes().get(i);
                if (lvalue instanceof VarAccessExp vae) {
                    // Esta é a anotação crucial que estava faltando.
                    vae.setType(returnType);
                } else {
                    // Visita lvalues complexos para anotar suas sub-expressões.
                    lvalue.accept(this);
                }
            }
        }

        // 3. Chama o método da classe pai para realizar a validação de tipo.
        return super.visit(c);
    }

    @Override
    public TypeNode visit(AssignCmd c) {
        // 1. Primeiro, visita o lado direito (a expressão) para que ele seja
        //    calculado e devidamente anotado.
        TypeNode expType = c.exp().accept(this);

        // 2. Agora, anota o lado esquerdo (o lvalue). Esta é a etapa que estava faltando.
        if (c.lvalue() instanceof VarAccessExp vae) {
            // Para uma variável simples como 'v', nós a anotamos com o tipo da expressão.
            vae.setType(expType);
        } else {
            // Para lvalues complexos (como p.x ou a[i]), nós os visitamos para
            // garantir que suas sub-expressões (p, a, i) também sejam anotadas.
            c.lvalue().accept(this);
        }

        // 3. Finalmente, chama o método da classe pai. Isso executará a lógica de
        //    VERIFICAÇÃO de tipo (garantindo que os tipos são compatíveis), o que ainda é importante.
        return super.visit(c);
    }
}

