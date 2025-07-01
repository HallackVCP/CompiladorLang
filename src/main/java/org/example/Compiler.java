package org.example;

import org.example.lang.Exception.ParserException;
import org.example.lang.ast.Program;
import org.example.lang.interpreter.Interpreter;
import org.example.lang.parser.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Compiler {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Uso: java lang.Compiler <diretiva> <caminho_arquivo>");
            System.err.println("Diretivas: -syn (análise sintática), -i (interpretação)");
            return;
        }

        String directive = args[0];
        String filePath = args[1];

        try {
            String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
            Parser parser = new Parser(sourceCode);
            Program program = parser.parseProgram();

            switch (directive) {
                case "-syn": // Executa a análise sintática do programa
                    System.out.println("accept"); // Se chegou aqui, o parse foi bem-sucedido
                    break;
                case "-i": // Interpreta o programa
                    Interpreter interpreter = new Interpreter();
                    interpreter.interpret(program);
                    break;
                default:
                    System.err.println("Diretiva desconhecida: " + directive);
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + filePath);
        } catch (ParserException e) {
            // O Parser e o Interpretador lançam RuntimeException em caso de erro.
            System.err.println(e.getMessage());
            // Para a análise sintática, a saída em caso de erro deve ser "reject"
            if (directive.equals("-syn")) {
                System.out.println("reject");
            }
        }
    }
}