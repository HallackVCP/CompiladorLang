package org.example.types;

import org.example.lang.ast.Program;
import org.example.lang.interpreter.Interpreter;
import org.example.lang.parser.Parser;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TypesFunctionTest {

    @Test
    public void testBoardFunction() throws IOException {
        String filePath = "src/test/resources/types/function/board.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testFibFunction() throws IOException {
        String filePath = "src/test/resources/types/function/fib.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testOrEquivFunction() throws IOException {
        String filePath = "src/test/resources/types/function/or_equiv.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testReturnFunction() throws IOException {
        String filePath = "src/test/resources/types/function/return.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testSortFunction() throws IOException {
        simulateInput("5\n3\n8\n1\n9\n2\n7\n4\n6\n0\n");
        String filePath = "src/test/resources/types/function/sort.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }
    private void simulateInput(String simulatedInput) {
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.setIn(originalIn))); // Restaurar System.in ao final
    }

    @Test
    public void testTeste1Function() throws IOException {
        String filePath = "src/test/resources/types/function/teste1.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testTeste2Function() throws IOException {
        String filePath = "src/test/resources/types/function/teste2.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }
}
