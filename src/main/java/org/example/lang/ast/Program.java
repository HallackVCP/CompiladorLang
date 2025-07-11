package org.example.lang.ast;

import org.example.lang.ast.decl.Decl;
import java.util.List;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

/**
 * Representa a raiz de um programa 'lang', que é constituído por um conjunto de definições.
 */
public record Program(List<Decl> decls) implements Node {
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
