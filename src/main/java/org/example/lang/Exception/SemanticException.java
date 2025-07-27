package org.example.lang.Exception;

/**
 * Exceção customizada para ser lançada durante a análise semântica
 * quando uma violação das regras de tipo da linguagem é encontrada.
 */
public class SemanticException extends AnalysisException {
    public SemanticException(String message) {
        super("Erro Semântico: " + message);
    }
}
