package org.example.sintaxe;

import org.example.lang.Exception.ParserException;
import org.example.lang.ast.Program;
import org.example.lang.parser.Parser;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class SintaxeErradoTest {

    @Test
    public void testSintaxeErradoattrADD() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/attrADD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }
    @Test
    public void testSintaxeErradoattrAND() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/attrAND.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }
    @Test
    public void testSintaxeErradoAttrCHAR() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/attrCHAR.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoAttrCHARESCAPE1() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/attrCHARESCAPE1.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoAttrCHARESCAPE2() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/attrCHARESCAPE2.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoAttrCHARESCAPE3() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/attrCHARESCAPE3.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoAttrCMD() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/attrCMD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoAttrDIV() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/attrDIV.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoAttrEQ() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/attrEQ.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoAttrFloat() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/attrFloat.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoAttrLT() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/attrLT.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoAttrMOD() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/attrMOD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoAttrMULT() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/attrMULT.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoAttrNEQ() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/attrNEQ.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoAttrNULL() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/attrNULL.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoAttrSUB() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/attrSUB.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoAttrTRUE() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/attrTRUE.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoChainIf() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/chainIf.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoData() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/data.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoFunction() throws IOException {
        simulateInput("5");
        String filePath = "src/test/resources/sintaxe/errado/function.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoFunctionCall() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/function_call.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoFunctionCallExpr() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/function_call_expr.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoFunctionCallRet() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/function_call_ret.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoFunctionCallRetUse() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/function_call_ret_use.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoFunctionCallRetUse2() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/function_call_ret_use2.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoIfOneCMD() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/if_oneCMD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoIfElseOneCMD() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/ifelse_oneCMD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoInstanciate() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/instanciate.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoIterateOneCMD() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/iterate_oneCMD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoIterateCMD() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/iterateCMD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoNonAssoc() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/nonAssoc.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoParameter() throws IOException {
        simulateInput("8");
        String filePath = "src/test/resources/sintaxe/errado/parameter.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoPrintCMD() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/printCMD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }
    private void simulateInput(String simulatedInput) {
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.setIn(originalIn))); // Restaurar System.in ao final
    }

    @Test
    public void testSintaxeErradoReadCMD() throws IOException {
        simulateInput("9");
        String filePath = "src/test/resources/sintaxe/errado/readCMD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

    @Test
    public void testSintaxeErradoReturnCMD() throws IOException {
        String filePath = "src/test/resources/sintaxe/errado/returnCMD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertThrows(ParserException.class, () -> parser.parseProgram());
    }

}
