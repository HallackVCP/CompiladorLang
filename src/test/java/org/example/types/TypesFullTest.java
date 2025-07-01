package org.example.types;

import org.example.lang.ast.Program;
import org.example.lang.interpreter.Interpreter;
import org.example.lang.parser.Parser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TypesFullTest {

    @Test
    public void testAFDFull() throws IOException {
        String filePath = "src/test/resources/types/full/AFD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testLinkedFull() throws IOException {
        String filePath = "src/test/resources/types/full/linked.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }
}
