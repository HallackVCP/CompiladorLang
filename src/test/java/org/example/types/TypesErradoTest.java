package org.example.types;

import org.example.lang.Exception.ParserException;
import org.example.lang.ast.Program;
import org.example.lang.interpreter.Interpreter;
import org.example.lang.parser.Parser;
import org.example.lang.semantica.TypeCheckerVisitor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

public class TypesErradoTest {

    @Test
    public void testErrado1() throws IOException {
        String filePath = "src/test/resources/types/errado/errado1.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
        assertThrows(Exception.class, () -> typeChecker.check(program));
    }
    @Test
    public void testErrado2() throws IOException {
        String filePath = "src/test/resources/types/errado/errado2.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertThrows(Exception.class, () -> interpreter.interpret(program));
    }
    @Test
    public void testErrado3() throws IOException {
        String filePath = "src/test/resources/types/errado/errado3.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
        assertThrows(Exception.class, () -> typeChecker.check(program));
    }
    @Test
    public void testErrado4() throws IOException {
        String filePath = "src/test/resources/types/errado/errado4.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        assertThrows(Exception.class, () -> interpreter.interpret(program));
    }
    @Test
    public void testErrado5() throws IOException {
        String filePath = "src/test/resources/types/errado/errado5.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
        assertThrows(Exception.class, () -> typeChecker.check(program));
    }
    @Test
    public void testErrado6() throws IOException {
        String filePath = "src/test/resources/types/errado/errado6.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
        assertThrows(Exception.class, () -> typeChecker.check(program));
    }
    @Test
    public void testErrado7() throws IOException {
        String filePath = "src/test/resources/types/errado/errado7.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
        assertThrows(Exception.class, () -> typeChecker.check(program));
    }
}
