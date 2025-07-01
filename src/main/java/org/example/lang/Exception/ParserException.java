package org.example.lang.Exception;

public class ParserException extends RuntimeException {

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserException(String message, int line) {
        super(String.format("Error at line %d: %s", line, message));
    }
}
