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

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

public class TypesSimpleTest {

    @Test
    public void testAssocSimple() throws IOException {
        String filePath = "src/test/resources/types/simple/assoc.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testEquivCharSimple() throws IOException {
        String filePath = "src/test/resources/types/simple/equivChar.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testIfChainSimple() throws IOException {
        // Valores simulados para o teste
        simulateInput("5");
        String filePath = "src/test/resources/types/simple/ifchain.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testIterVarSimple() throws IOException {
        String filePath = "src/test/resources/types/simple/iterVar.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testIterVarArrSimple() throws IOException {
        String filePath = "src/test/resources/types/simple/iterVarArr.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testIterVarDecSimple() throws IOException {
        String filePath = "src/test/resources/types/simple/iterVarDec.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testReadSimple() throws IOException {
        // Valores simulados para o teste
        simulateInput("5");

        String filePath = "src/test/resources/types/simple/read.lan";
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

//    @Test
//    public void testScopeSimple() throws IOException {
//        simulateInput("5");
//        String filePath = "src/test/resources/types/simple/scope.lan";
//        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
//        Parser parser = new Parser(sourceCode);
//        Program program = parser.parseProgram();
//        Interpreter interpreter = new Interpreter();
//        assertDoesNotThrow(() -> interpreter.interpret(program));
//    }

    @Test
    public void testTeste0Simple() throws IOException {
        String filePath = "src/test/resources/types/simple/teste0.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }
}
