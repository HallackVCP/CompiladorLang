package org.example.sintaxe;

import org.example.lang.ast.Program;
import org.example.lang.parser.Parser;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class SintaxeCertoTest {


    @Test
    public void testSintaxeCorretaAttrORSugar() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attr_OR_SUGAR.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaAttrAdd() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attrADD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaAttrAND() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attrAND.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaAttrCHAR() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attrCHAR.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaAttrCHARESCAPE1() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attrCHARESCAPE1.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaAttrCHARESCAPE2() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attrCHARESCAPE2.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaAttrCHARESCAPE3() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attrCHARESCAPE3.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaAttrCMD() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attrCMD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaAttrDIV() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attrDIV.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaAttrEQ() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attrEQ.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaAttrFALSE() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attrFALSE.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaAttrFloat() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attrFloat.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }
    @Test
    public void testSintaxeCorretaAttrLT() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attrLT.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaAttrMOD() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attrMOD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaAttrMULT() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attrMULT.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaAttrNEQ() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attrNEQ.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaAttrNOT() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attrNOT.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaAttrNOTAND() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attrNOT_AND.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaAttrNULL() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attrNULL.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaAttrSUB() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attrSUB.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaAttrTRUE() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/attrTRUE.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaChainIf() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/chainIf.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }
    @Test
    public void testSintaxeCorretaData() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/data.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaFunction() throws IOException {
        simulateInput("6");
        String filePath = "src/test/resources/sintaxe/certo/function.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaFunctionCall() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/function_call.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaFunctionCallExpr() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/function_call_expr.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaFunctionCallRet() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/function_call_ret.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaFunctionCallRetUse() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/function_call_ret_use.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaFunctionCallRetUse2() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/function_call_ret_use2.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaIfOneCMD() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/if_oneCMD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaIfElseOneCMD() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/ifelse_oneCMD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaInstanciate() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/instanciate.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }
    private void simulateInput(String simulatedInput) {
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.setIn(originalIn))); // Restaurar System.in ao final
    }

    @Test
    public void testSintaxeCorretaIterateOneCMD() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/iterate_oneCMD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaIterateCMD() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/iterateCMD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaIterateLocalArr() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/iterateLocalArr.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaIterateLocalArrVar() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/iterateLocalArrVar.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaIterateLocalVar() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/iterateLocalVar.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaParameter() throws IOException {
        simulateInput("7");
        String filePath = "src/test/resources/sintaxe/certo/parameter.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaPrintCMD() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/printCMD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaReadCMD() throws IOException {
        simulateInput("3");
        String filePath = "src/test/resources/sintaxe/certo/readCMD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaReturnCMD() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/returnCMD.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }

    @Test
    public void testSintaxeCorretaReturnCMDEXP() throws IOException {
        String filePath = "src/test/resources/sintaxe/certo/returnCMDEXP.lan";
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        Parser parser = new Parser(sourceCode);
        Program program = parser.parseProgram();
        assertDoesNotThrow(() -> parser.parseProgram());
    }





}
