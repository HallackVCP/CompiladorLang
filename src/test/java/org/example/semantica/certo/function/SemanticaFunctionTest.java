package org.example.semantica.certo.function;

import org.example.lang.ast.Program;
import org.example.lang.interpreter.Interpreter;
import org.example.lang.parser.Parser;
import org.example.lang.semantica.TypeCheckerVisitor;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

public class SemanticaFunctionTest {
    @Test
    public void testBoardLan() throws IOException {
        String filePath = "src/test/resources/semantica/certo/function/board.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
        typeChecker.check(program);
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testFibLan() throws IOException {
        String filePath = "src/test/resources/semantica/certo/function/fib.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
        typeChecker.check(program);
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testNumericLan() throws IOException {
        String filePath = "src/test/resources/semantica/certo/function/numeric.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
        typeChecker.check(program);
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testOrEquivLan() throws IOException {
        String filePath = "src/test/resources/semantica/certo/function/or_equiv.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
        typeChecker.check(program);
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testReturnLan() throws IOException {
        String filePath = "src/test/resources/semantica/certo/function/return.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
        //typeChecker.check(program);
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testSortLan() throws IOException {
        simulateInput("5\n3\n8\n1\n9\n2\n7\n4\n6\n0\n");
        String filePath = "src/test/resources/semantica/certo/function/sort.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
        //typeChecker.check(program);
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }
    private void simulateInput(String simulatedInput) {
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.setIn(originalIn))); // Restaurar System.in ao final
    }

    @Test
    public void testTeste1Lan() throws IOException {
        String filePath = "src/test/resources/semantica/certo/function/teste1.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
        typeChecker.check(program);
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }
    @Test
    public void testTeste1() throws IOException {
        String filePath = "src/test/resources/semantica/certo/function/#teste1.lan#";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
        typeChecker.check(program);
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testTeste12Lan() throws IOException {
        String filePath = "src/test/resources/semantica/certo/function/teste12.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
        typeChecker.check(program);
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }
}
