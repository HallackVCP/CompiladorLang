package org.example.lang.Exception;

public class InterpreterException extends RuntimeException{
    public InterpreterException(String message) {
        super(message);
    }

    public InterpreterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterpreterException(String message, int line) {
        super(String.format("Error at line %d: %s", line, message));
    }
}
