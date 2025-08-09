package org.example.semantica.errado;

import org.example.lang.Exception.AnalysisException;
import org.example.lang.Exception.InterpreterException;
import org.example.lang.ast.Program;
import org.example.lang.interpreter.Interpreter;
import org.example.lang.parser.Parser;
import org.example.lang.semantica.TypeCheckerVisitor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SemanticaErradoTest {

    @Test
    public void testSintaxeErradoabsDataErrado1() throws IOException {
        String filePath = "src/test/resources/semantica/errado/ControleNotas.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
        typeChecker.check(program);
        Interpreter interpreter = new Interpreter();
        assertThrows(RuntimeException.class, () -> interpreter.interpret(parser.parseProgram()));
    }
}
