package org.example.semantica.certo.full;

import org.example.lang.ast.Program;
import org.example.lang.interpreter.Interpreter;
import org.example.lang.parser.Parser;
import org.example.lang.semantica.TypeCheckerVisitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

public class SemanticaFullTest {


    @Test
    public void testSemanticaFullAfd() throws IOException {
        String filePath = "src/test/resources/semantica/certo/full/AFD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
        typeChecker.check(program);
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }
    @Test
    public void testLinked() throws IOException {
        String filePath = "src/test/resources/semantica/certo/full/linked.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
        typeChecker.check(program);
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

    @Test
    public void testControleNotas() throws IOException {
        String filePath = "src/test/resources/semantica/certo/full/ControleNotas.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
        typeChecker.check(program);
        Interpreter interpreter = new Interpreter();
        assertDoesNotThrow(() -> interpreter.interpret(program));
    }

}
