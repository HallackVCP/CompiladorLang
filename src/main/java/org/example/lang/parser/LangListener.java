// Generated from Lang.g4 by ANTLR 4.13.2

    package org.example.lang.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LangParser}.
 */
public interface LangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LangParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(LangParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(LangParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dataDef}
	 * labeled alternative in {@link LangParser#def}.
	 * @param ctx the parse tree
	 */
	void enterDataDef(LangParser.DataDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dataDef}
	 * labeled alternative in {@link LangParser#def}.
	 * @param ctx the parse tree
	 */
	void exitDataDef(LangParser.DataDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funDef}
	 * labeled alternative in {@link LangParser#def}.
	 * @param ctx the parse tree
	 */
	void enterFunDef(LangParser.FunDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funDef}
	 * labeled alternative in {@link LangParser#def}.
	 * @param ctx the parse tree
	 */
	void exitFunDef(LangParser.FunDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dataBlock}
	 * labeled alternative in {@link LangParser#data}.
	 * @param ctx the parse tree
	 */
	void enterDataBlock(LangParser.DataBlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dataBlock}
	 * labeled alternative in {@link LangParser#data}.
	 * @param ctx the parse tree
	 */
	void exitDataBlock(LangParser.DataBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code declStmt}
	 * labeled alternative in {@link LangParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterDeclStmt(LangParser.DeclStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code declStmt}
	 * labeled alternative in {@link LangParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitDeclStmt(LangParser.DeclStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funDefBody}
	 * labeled alternative in {@link LangParser#fun}.
	 * @param ctx the parse tree
	 */
	void enterFunDefBody(LangParser.FunDefBodyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funDefBody}
	 * labeled alternative in {@link LangParser#fun}.
	 * @param ctx the parse tree
	 */
	void exitFunDefBody(LangParser.FunDefBodyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code paramList}
	 * labeled alternative in {@link LangParser#params}.
	 * @param ctx the parse tree
	 */
	void enterParamList(LangParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by the {@code paramList}
	 * labeled alternative in {@link LangParser#params}.
	 * @param ctx the parse tree
	 */
	void exitParamList(LangParser.ParamListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code paramDecl}
	 * labeled alternative in {@link LangParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParamDecl(LangParser.ParamDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code paramDecl}
	 * labeled alternative in {@link LangParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParamDecl(LangParser.ParamDeclContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link LangParser#type}.
	 * @param ctx the parse tree
	 */
	void enterArrayType(LangParser.ArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link LangParser#type}.
	 * @param ctx the parse tree
	 */
	void exitArrayType(LangParser.ArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code baseType}
	 * labeled alternative in {@link LangParser#type}.
	 * @param ctx the parse tree
	 */
	void enterBaseType(LangParser.BaseTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code baseType}
	 * labeled alternative in {@link LangParser#type}.
	 * @param ctx the parse tree
	 */
	void exitBaseType(LangParser.BaseTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intType}
	 * labeled alternative in {@link LangParser#btype}.
	 * @param ctx the parse tree
	 */
	void enterIntType(LangParser.IntTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link LangParser#btype}.
	 * @param ctx the parse tree
	 */
	void exitIntType(LangParser.IntTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code charType}
	 * labeled alternative in {@link LangParser#btype}.
	 * @param ctx the parse tree
	 */
	void enterCharType(LangParser.CharTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code charType}
	 * labeled alternative in {@link LangParser#btype}.
	 * @param ctx the parse tree
	 */
	void exitCharType(LangParser.CharTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link LangParser#btype}.
	 * @param ctx the parse tree
	 */
	void enterBoolType(LangParser.BoolTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link LangParser#btype}.
	 * @param ctx the parse tree
	 */
	void exitBoolType(LangParser.BoolTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code floatType}
	 * labeled alternative in {@link LangParser#btype}.
	 * @param ctx the parse tree
	 */
	void enterFloatType(LangParser.FloatTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code floatType}
	 * labeled alternative in {@link LangParser#btype}.
	 * @param ctx the parse tree
	 */
	void exitFloatType(LangParser.FloatTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code customType}
	 * labeled alternative in {@link LangParser#btype}.
	 * @param ctx the parse tree
	 */
	void enterCustomType(LangParser.CustomTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code customType}
	 * labeled alternative in {@link LangParser#btype}.
	 * @param ctx the parse tree
	 */
	void exitCustomType(LangParser.CustomTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blockCmd}
	 * labeled alternative in {@link LangParser#cmd}.
	 * @param ctx the parse tree
	 */
	void enterBlockCmd(LangParser.BlockCmdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blockCmd}
	 * labeled alternative in {@link LangParser#cmd}.
	 * @param ctx the parse tree
	 */
	void exitBlockCmd(LangParser.BlockCmdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifCmd}
	 * labeled alternative in {@link LangParser#cmd}.
	 * @param ctx the parse tree
	 */
	void enterIfCmd(LangParser.IfCmdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifCmd}
	 * labeled alternative in {@link LangParser#cmd}.
	 * @param ctx the parse tree
	 */
	void exitIfCmd(LangParser.IfCmdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code iterateCmd}
	 * labeled alternative in {@link LangParser#cmd}.
	 * @param ctx the parse tree
	 */
	void enterIterateCmd(LangParser.IterateCmdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code iterateCmd}
	 * labeled alternative in {@link LangParser#cmd}.
	 * @param ctx the parse tree
	 */
	void exitIterateCmd(LangParser.IterateCmdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code readCmd}
	 * labeled alternative in {@link LangParser#cmd}.
	 * @param ctx the parse tree
	 */
	void enterReadCmd(LangParser.ReadCmdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code readCmd}
	 * labeled alternative in {@link LangParser#cmd}.
	 * @param ctx the parse tree
	 */
	void exitReadCmd(LangParser.ReadCmdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code printCmd}
	 * labeled alternative in {@link LangParser#cmd}.
	 * @param ctx the parse tree
	 */
	void enterPrintCmd(LangParser.PrintCmdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code printCmd}
	 * labeled alternative in {@link LangParser#cmd}.
	 * @param ctx the parse tree
	 */
	void exitPrintCmd(LangParser.PrintCmdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnCmd}
	 * labeled alternative in {@link LangParser#cmd}.
	 * @param ctx the parse tree
	 */
	void enterReturnCmd(LangParser.ReturnCmdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnCmd}
	 * labeled alternative in {@link LangParser#cmd}.
	 * @param ctx the parse tree
	 */
	void exitReturnCmd(LangParser.ReturnCmdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignCmd}
	 * labeled alternative in {@link LangParser#cmd}.
	 * @param ctx the parse tree
	 */
	void enterAssignCmd(LangParser.AssignCmdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignCmd}
	 * labeled alternative in {@link LangParser#cmd}.
	 * @param ctx the parse tree
	 */
	void exitAssignCmd(LangParser.AssignCmdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funCallCmd}
	 * labeled alternative in {@link LangParser#cmd}.
	 * @param ctx the parse tree
	 */
	void enterFunCallCmd(LangParser.FunCallCmdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funCallCmd}
	 * labeled alternative in {@link LangParser#cmd}.
	 * @param ctx the parse tree
	 */
	void exitFunCallCmd(LangParser.FunCallCmdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idItcond}
	 * labeled alternative in {@link LangParser#itcond}.
	 * @param ctx the parse tree
	 */
	void enterIdItcond(LangParser.IdItcondContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idItcond}
	 * labeled alternative in {@link LangParser#itcond}.
	 * @param ctx the parse tree
	 */
	void exitIdItcond(LangParser.IdItcondContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expItcond}
	 * labeled alternative in {@link LangParser#itcond}.
	 * @param ctx the parse tree
	 */
	void enterExpItcond(LangParser.ExpItcondContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expItcond}
	 * labeled alternative in {@link LangParser#itcond}.
	 * @param ctx the parse tree
	 */
	void exitExpItcond(LangParser.ExpItcondContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(LangParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(LangParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExp(LangParser.ExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExp(LangParser.ExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#logicAndExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicAndExpr(LangParser.LogicAndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#logicAndExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicAndExpr(LangParser.LogicAndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(LangParser.EqualityExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(LangParser.EqualityExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#relationalExpr}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpr(LangParser.RelationalExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#relationalExpr}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpr(LangParser.RelationalExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpr(LangParser.AdditiveExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpr(LangParser.AdditiveExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpr(LangParser.MultiplicativeExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpr(LangParser.MultiplicativeExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryMinus}
	 * labeled alternative in {@link LangParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMinus(LangParser.UnaryMinusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryMinus}
	 * labeled alternative in {@link LangParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMinus(LangParser.UnaryMinusContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalNot}
	 * labeled alternative in {@link LangParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicalNot(LangParser.LogicalNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalNot}
	 * labeled alternative in {@link LangParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicalNot(LangParser.LogicalNotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code primaryUnary}
	 * labeled alternative in {@link LangParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryUnary(LangParser.PrimaryUnaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code primaryUnary}
	 * labeled alternative in {@link LangParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryUnary(LangParser.PrimaryUnaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lvalueExp}
	 * labeled alternative in {@link LangParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterLvalueExp(LangParser.LvalueExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lvalueExp}
	 * labeled alternative in {@link LangParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitLvalueExp(LangParser.LvalueExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parens}
	 * labeled alternative in {@link LangParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterParens(LangParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link LangParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitParens(LangParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newExp}
	 * labeled alternative in {@link LangParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterNewExp(LangParser.NewExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newExp}
	 * labeled alternative in {@link LangParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitNewExp(LangParser.NewExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funCallIndexed}
	 * labeled alternative in {@link LangParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterFunCallIndexed(LangParser.FunCallIndexedContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funCallIndexed}
	 * labeled alternative in {@link LangParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitFunCallIndexed(LangParser.FunCallIndexedContext ctx);
	/**
	 * Enter a parse tree produced by the {@code trueLiteral}
	 * labeled alternative in {@link LangParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterTrueLiteral(LangParser.TrueLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code trueLiteral}
	 * labeled alternative in {@link LangParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitTrueLiteral(LangParser.TrueLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code falseLiteral}
	 * labeled alternative in {@link LangParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterFalseLiteral(LangParser.FalseLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code falseLiteral}
	 * labeled alternative in {@link LangParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitFalseLiteral(LangParser.FalseLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullLiteral}
	 * labeled alternative in {@link LangParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterNullLiteral(LangParser.NullLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullLiteral}
	 * labeled alternative in {@link LangParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitNullLiteral(LangParser.NullLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intLiteral}
	 * labeled alternative in {@link LangParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterIntLiteral(LangParser.IntLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intLiteral}
	 * labeled alternative in {@link LangParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitIntLiteral(LangParser.IntLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code floatLiteral}
	 * labeled alternative in {@link LangParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterFloatLiteral(LangParser.FloatLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code floatLiteral}
	 * labeled alternative in {@link LangParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitFloatLiteral(LangParser.FloatLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code charLiteral}
	 * labeled alternative in {@link LangParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterCharLiteral(LangParser.CharLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code charLiteral}
	 * labeled alternative in {@link LangParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitCharLiteral(LangParser.CharLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleLvalue}
	 * labeled alternative in {@link LangParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void enterSimpleLvalue(LangParser.SimpleLvalueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleLvalue}
	 * labeled alternative in {@link LangParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void exitSimpleLvalue(LangParser.SimpleLvalueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code fieldAccess}
	 * labeled alternative in {@link LangParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void enterFieldAccess(LangParser.FieldAccessContext ctx);
	/**
	 * Exit a parse tree produced by the {@code fieldAccess}
	 * labeled alternative in {@link LangParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void exitFieldAccess(LangParser.FieldAccessContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayAccess}
	 * labeled alternative in {@link LangParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccess(LangParser.ArrayAccessContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayAccess}
	 * labeled alternative in {@link LangParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccess(LangParser.ArrayAccessContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expList}
	 * labeled alternative in {@link LangParser#exps}.
	 * @param ctx the parse tree
	 */
	void enterExpList(LangParser.ExpListContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expList}
	 * labeled alternative in {@link LangParser#exps}.
	 * @param ctx the parse tree
	 */
	void exitExpList(LangParser.ExpListContext ctx);
}