package org.example;

import org.antlr.v4.runtime.*;
import org.example.lang.parser.*;
import org.example.lang.Exception.ParserException;
import org.example.lang.ast.Program;
import org.example.lang.interpreter.Interpreter;

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
            CharStream sourceCodeCharStream = CharStreams.fromString(sourceCode);
            LangLexer lexer = new LangLexer(sourceCodeCharStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LangParser parser = new LangParser(tokens);
            parser.removeErrorListeners();
            // Cria um listener para capturar erros
            final boolean[] hasError = { false };
            parser.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer,
                                        Object offendingSymbol,
                                        int line, int charPositionInLine,
                                        String msg,
                                        RecognitionException e) {
                    System.err.printf("Error at line %d:%d - %s%n", line, charPositionInLine, msg);
                    hasError[0] = true;
                }
            });


            // Tenta analisar a regra inicial (exemplo: prog)
            parser.prog();

            if(hasError[0]){
                throw new ParserException("Parser Excepiotn");
            }

            switch (directive) {
                case "-syn": // Executa a análise sintática do programa
                    System.out.println("accept"); // Se chegou aqui, o parse foi bem-sucedido
                    break;
                case "-i": // Interpreta o programa
                    Interpreter interpreter = new Interpreter();
                    //interpreter.interpret(program);
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