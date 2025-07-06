package org.example.semantica.certo.full;

import org.example.lang.ast.Program;
import org.example.lang.interpreter.Interpreter;
import org.example.lang.parser.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe de teste para a semântica completa do sistema.
 * Esta classe contém testes que verificam a semântica completa da linguagem,
 * incluindo a análise de comandos, expressões e outros elementos da AST.
 */
public class SemanticaFullTest {


    @Test
    public void testSemanticaFullAfd() throws IOException {
        String filePath = "src/test/resources/semantica/certo/full/AFD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }
    @Test
    public void testLinked() throws IOException {
        String filePath = "src/test/resources/semantica/certo/full/linked.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

//    @Test
//    public void testAFDInst() throws IOException {
//        String filePath = "src/test/resources/types/full/AFD.inst";
//        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
//        Parser parser = new Parser(sourceCode);
//        Program program = parser.parseProgram();
//        Interpreter interpreter = new Interpreter();
//        assertDoesNotThrow(() -> interpreter.interpret(program));
//    }
//
//    @Test
//    public void testLinkedInst() throws IOException {
//        String filePath = "src/test/resources/types/full/linked.inst";
//        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
//        Parser parser = new Parser(sourceCode);
//        Program program = parser.parseProgram();
//        Interpreter interpreter = new Interpreter();
//        assertDoesNotThrow(() -> interpreter.interpret(program));
//    }
}
