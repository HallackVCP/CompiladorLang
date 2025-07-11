package org.example.lang.ast.decl;

import org.example.lang.ast.Visitor;
import org.example.lang.ast.cmd.Cmd;

import java.util.List;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

/**
 * Representa a definição de uma função.
 * Inclui seu nome, parâmetros, tipos de retorno e o comando que constitui seu corpo.
 */
public record FunDecl(String name, List<Param> params, List<String> returnTypes, Cmd body) implements Decl {

    /**
     * Representa um único parâmetro de função, com nome e tipo.
     */
    public record Param(String name, String type) {}

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
