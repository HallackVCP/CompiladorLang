package org.example.lang.ast.decl;

import org.example.lang.ast.TypeNode;
import org.example.lang.ast.Visitor;

import java.util.List;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

/**
 * Representa a declaração de um tipo de dado (data), que pode ser abstrato.
 * Contém seus campos e uma lista de funções internas.
 */
public record DataDecl(boolean isAbstract, String name, List<Field> fields, List<FunDecl> functions) implements Decl {
    /**
     * Representa um único campo (atributo) dentro de uma declaração de 'data'.
     */
    public record Field(String name, TypeNode type) {}

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}