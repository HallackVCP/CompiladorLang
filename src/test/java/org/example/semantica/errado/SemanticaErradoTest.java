package org.example.semantica.errado;

import org.example.lang.Exception.AnalysisException;
import org.example.lang.Exception.InterpreterException;
import org.example.lang.Exception.SemanticException;
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
public class SemanticaErradoTest {

    @Test
    public void testSintaxeErradoabsDataErrado1() throws IOException {
        String filePath = "src/test/resources/semantica/errado/ControleNotas.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
        assertThrows(RuntimeException.class, () -> typeChecker.check(program));
    }
}
