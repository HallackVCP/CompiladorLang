package org.example;

import org.example.lang.Exception.AnalysisException;
import org.example.lang.ast.Program;
import org.example.lang.interpreter.Interpreter;
import org.example.lang.jasmingenerator.JasminGeneratorVisitor;
import org.example.lang.parser.Parser;
import org.example.lang.semantica.TypeAnnotationVisitor;
import org.example.lang.semantica.TypeCheckerVisitor;
import org.example.lang.sourcegenerator.SourceGeneratorVisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;


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

            // Fases de análise semântica e anotação (pré-requisitos)
            if (directive.equals("-t") || directive.equals("-i") || directive.equals("-src") || directive.equals("-gen")) {
                TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
                typeChecker.check(program);

                // Anota a AST com os tipos para o gerador de código
                TypeAnnotationVisitor typeAnnotator = new TypeAnnotationVisitor();
                typeAnnotator.annotate(program);
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

                case "-gen":
                    String outputClassName = (args.length > 2) ? args[2] : "LangClass";
                    JasminGeneratorVisitor jasminGen = new JasminGeneratorVisitor(outputClassName);
                    Map<String, String> generatedFiles = jasminGen.generate(program);

                    for (Map.Entry<String, String> entry : generatedFiles.entrySet()) {
                        String fileName = entry.getKey() + ".j";
                        Files.writeString(Paths.get(fileName), entry.getValue(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                        System.out.println("Arquivo gerado: " + fileName);
                    }
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