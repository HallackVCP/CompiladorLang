package org.example;

import org.example.lang.Exception.AnalysisException;
import org.example.lang.ast.Program;
import org.example.lang.interpreter.Interpreter;
import org.example.lang.parser.Parser;
import org.example.lang.semantica.TypeCheckerVisitor;
import org.example.lang.sourcegenerator.SourceGeneratorVisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * @author Vinicius Hallack Cobucci Perobelli da Silva- 202065192A
 *         Guilherme Roldão dos Reis Pimenta - 202435001
 *
 * ${tags}
 */

public class Compiler {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Uso: java org.example.Compiler <diretiva> <caminho_arquivo>");
            System.err.println("Diretivas: -syn, -t, -i, -src");
            return;
        }

        String directive = args[0];
        String filePath = args[1];

        try {
            String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
            Parser parser = new Parser(sourceCode);
            Program program = parser.parseProgram();

            // A análise semântica é um pré-requisito para -t, -i, e -src
            if (directive.equals("-t") || directive.equals("-i") || directive.equals("-src")) {
                TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
                typeChecker.check(program);
            }

            switch (directive) {
                case "-syn":
                    System.out.println("accept");
                    break;

                case "-t":
                    System.out.println("accept");
                    break;

                case "-src": // Nova diretiva para geração source-to-source
                    SourceGeneratorVisitor generator = new SourceGeneratorVisitor();
                    String pythonCode = generator.generate(program);
                    System.out.print(pythonCode); // Usar print para não adicionar nova linha no final
                    break;

                case "-i":
                    Interpreter interpreter = new Interpreter();
                    interpreter.interpret(program);
                    break;

                default:
                    System.err.println("Diretiva desconhecida: " + directive);
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + filePath);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            if (!directive.equals("-i")) { // -i já imprime seus próprios erros de runtime
                System.out.println("reject");
            }
        }
    }
}