package org.example;

import org.example.lang.Exception.AnalysisException;
import org.example.lang.ast.Program;
import org.example.lang.interpreter.Interpreter;
import org.example.lang.parser.Parser;
import org.example.lang.semantica.TypeCheckerVisitor;

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
            System.err.println("Diretivas: -syn, -i, -t");
            return;
        }

        String directive = args[0];
        String filePath = args[1];

        try {
            String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
            Parser parser = new Parser(sourceCode);
            Program program = parser.parseProgram();

            switch (directive) {
                case "-syn":
                    System.out.println("accept");
                    break;

                case "-t": // Nova diretiva para verificação de tipos
                    TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
                    typeChecker.check(program);
                    System.out.println("accept"); // Se chegou aqui, a verificação foi bem-sucedida
                    break;

                case "-i":
                    // É uma boa prática rodar a verificação de tipos antes de interpretar
                    TypeCheckerVisitor preInterpreterChecker = new TypeCheckerVisitor();
                    preInterpreterChecker.check(program);

                    Interpreter interpreter = new Interpreter();
                    interpreter.interpret(program);
                    break;

                default:
                    System.err.println("Diretiva desconhecida: " + directive);
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + filePath);
        } catch (AnalysisException e) {
            System.err.println(e.getMessage());
            if (directive.equals("-syn") || directive.equals("-t")) {
                System.out.println("reject");
            }
        }
    }
}