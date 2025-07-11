package org.example.semantica.certo.simple;

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

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

public class SemanticaSimpleTest {
//    @Test
//    public void testAssocInst() throws IOException {
//        String filePath = "src/test/resources/semantica/certo/simple/assoc.inst";
//        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
//        Parser parser = new Parser(sourceCode);
//        Program program = parser.parseProgram();
//        Interpreter interpreter = new Interpreter();
//        assertDoesNotThrow(() -> interpreter.interpret(program));
//    }
//
//    @Test
//    public void testEquivCharInst() throws IOException {
//        String filePath = "src/test/resources/semantica/certo/simple/equivChar.inst";
//        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
//        Parser parser = new Parser(sourceCode);
//        Program program = parser.parseProgram();
//        Interpreter interpreter = new Interpreter();
//        assertDoesNotThrow(() -> interpreter.interpret(program));
//    }
//
//    @Test
//    public void testIfchainInst() throws IOException {
//        String filePath = "src/test/resources/semantica/certo/simple/ifchain.inst";
//        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
//        Parser parser = new Parser(sourceCode);
//        Program program = parser.parseProgram();
//        Interpreter interpreter = new Interpreter();
//        assertDoesNotThrow(() -> interpreter.interpret(program));
//    }
//
//    @Test
//    public void testIterVarInst() throws IOException {
//        String filePath = "src/test/resources/semantica/certo/simple/iterVar.inst";
//        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
//        Parser parser = new Parser(sourceCode);
//        Program program = parser.parseProgram();
//        Interpreter interpreter = new Interpreter();
//        assertDoesNotThrow(() -> interpreter.interpret(program));
//    }
//
//    @Test
//    public void testIterVarArrInst() throws IOException {
//        String filePath = "src/test/resources/semantica/certo/simple/iterVarArr.inst";
//        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
//        Parser parser = new Parser(sourceCode);
//        Program program = parser.parseProgram();
//        Interpreter interpreter = new Interpreter();
//        assertDoesNotThrow(() -> interpreter.interpret(program));
//    }
//
//    @Test
//    public void testIterVarDecInst() throws IOException {
//        String filePath = "src/test/resources/semantica/certo/simple/iterVarDec.inst";
//        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
//        Parser parser = new Parser(sourceCode);
//        Program program = parser.parseProgram();
//        Interpreter interpreter = new Interpreter();
//        assertDoesNotThrow(() -> interpreter.interpret(program));
//    }
//
//    @Test
//    public void testReadInst() throws IOException {
//        String filePath = "src/test/resources/semantica/certo/simple/read.inst";
//        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
//        Parser parser = new Parser(sourceCode);
//        Program program = parser.parseProgram();
//        Interpreter interpreter = new Interpreter();
//        assertDoesNotThrow(() -> interpreter.interpret(program));
//    }
//
//    @Test
//    public void testTeste0Inst() throws IOException {
//        String filePath = "src/test/resources/semantica/certo/simple/teste0.inst";
//        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
//        Parser parser = new Parser(sourceCode);
//        Program program = parser.parseProgram();
//        Interpreter interpreter = new Interpreter();
//        assertDoesNotThrow(() -> interpreter.interpret(program));
//    }
    @Test
    public void testAssocLan() throws IOException {
        String filePath = "src/test/resources/semantica/certo/simple/assoc.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testEquivCharLan() throws IOException {
        String filePath = "src/test/resources/semantica/certo/simple/equivChar.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testIfchainLan() throws IOException {
        simulateInput("5");
        String filePath = "src/test/resources/semantica/certo/simple/ifchain.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testIterVarLan() throws IOException {
        String filePath = "src/test/resources/semantica/certo/simple/iterVar.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testIterVarArrLan() throws IOException {
        String filePath = "src/test/resources/semantica/certo/simple/iterVarArr.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testIterVarDecLan() throws IOException {
        String filePath = "src/test/resources/semantica/certo/simple/iterVarDec.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testReadLan() throws IOException {
        simulateInput("5");
        String filePath = "src/test/resources/semantica/certo/simple/read.lan";
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
    public void testTeste0Lan() throws IOException {
        String filePath = "src/test/resources/semantica/certo/simple/teste0.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }
}
