package org.example.lang.ast.exp;

import org.example.lang.ast.Node;
import org.example.lang.ast.TypeNode;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

/**
 * Interface base para todos os nós de expressão na AST.
 */
public interface Exp extends Node {
    void setType(TypeNode type);
    TypeNode getType();
}