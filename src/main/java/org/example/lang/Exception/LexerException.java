package org.example.lang.Exception;

public class LexerException extends AnalysisException{

    public LexerException(String message) {
        super(message);
    }

    public LexerException(String message, Throwable cause) {
        super(message, cause);
    }

    public LexerException(String message, int line) {
        super(String.format("Error at line %d: %s", line, message));
    }
}
