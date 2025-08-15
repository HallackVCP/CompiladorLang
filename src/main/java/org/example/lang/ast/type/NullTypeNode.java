package org.example.lang.ast.type;

import org.example.lang.ast.TypeNode;
import org.example.lang.ast.Visitor;

/**
 * Representa o tipo do literal 'null'.
 * É um tipo especial usado na verificação de tipos para compatibilidade
 * com tipos de referência (arrays e registros).
 */
public class NullTypeNode implements TypeNode {
    @Override
    public <T> T accept(Visitor<T> v) {
        // O visitor principal não precisa de um método específico para NullTypeNode,
        // pois ele é tratado nos locais onde é usado (ex: BinOpExp, AssignCmd).
        // Retornamos 'this' para consistência.
        return (T) this;
    }

    @Override
    public String toString() {
        return "NullType";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NullTypeNode;
    }
}
