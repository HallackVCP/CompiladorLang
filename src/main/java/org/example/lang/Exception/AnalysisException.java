package org.example.lang.Exception;

public class AnalysisException extends RuntimeException {

    public AnalysisException(String message) {
        super(message);
    }

    public AnalysisException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnalysisException(String message, int line) {
        super(String.format("Error at line %d: %s", line, message));
    }
}
