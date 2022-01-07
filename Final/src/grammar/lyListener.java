// Generated from ly.g4 by ANTLR 4.5
package grammar;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link lyParser}.
 */
public interface lyListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link lyParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(lyParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link lyParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(lyParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link lyParser#body}.
	 * @param ctx the parse tree
	 */
	void enterBody(lyParser.BodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link lyParser#body}.
	 * @param ctx the parse tree
	 */
	void exitBody(lyParser.BodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link lyParser#funcBody}.
	 * @param ctx the parse tree
	 */
	void enterFuncBody(lyParser.FuncBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link lyParser#funcBody}.
	 * @param ctx the parse tree
	 */
	void exitFuncBody(lyParser.FuncBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link lyParser#procBody}.
	 * @param ctx the parse tree
	 */
	void enterProcBody(lyParser.ProcBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link lyParser#procBody}.
	 * @param ctx the parse tree
	 */
	void exitProcBody(lyParser.ProcBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link lyParser#bodypart}.
	 * @param ctx the parse tree
	 */
	void enterBodypart(lyParser.BodypartContext ctx);
	/**
	 * Exit a parse tree produced by {@link lyParser#bodypart}.
	 * @param ctx the parse tree
	 */
	void exitBodypart(lyParser.BodypartContext ctx);
	/**
	 * Enter a parse tree produced by {@link lyParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(lyParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link lyParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(lyParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link lyParser#procedure}.
	 * @param ctx the parse tree
	 */
	void enterProcedure(lyParser.ProcedureContext ctx);
	/**
	 * Exit a parse tree produced by {@link lyParser#procedure}.
	 * @param ctx the parse tree
	 */
	void exitProcedure(lyParser.ProcedureContext ctx);
	/**
	 * Enter a parse tree produced by {@link lyParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(lyParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link lyParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(lyParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link lyParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterDecl(lyParser.DeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link lyParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitDecl(lyParser.DeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link lyParser#declpart}.
	 * @param ctx the parse tree
	 */
	void enterDeclpart(lyParser.DeclpartContext ctx);
	/**
	 * Exit a parse tree produced by {@link lyParser#declpart}.
	 * @param ctx the parse tree
	 */
	void exitDeclpart(lyParser.DeclpartContext ctx);
	/**
	 * Enter a parse tree produced by the {@code charExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCharExpr(lyParser.CharExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code charExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCharExpr(lyParser.CharExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funcExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFuncExpr(lyParser.FuncExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funcExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFuncExpr(lyParser.FuncExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrayExpr(lyParser.ArrayExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrayExpr(lyParser.ArrayExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code compoundExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCompoundExpr(lyParser.CompoundExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compoundExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCompoundExpr(lyParser.CompoundExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code trueExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterTrueExpr(lyParser.TrueExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code trueExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitTrueExpr(lyParser.TrueExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterStatExpr(lyParser.StatExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitStatExpr(lyParser.StatExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMultExpr(lyParser.MultExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMultExpr(lyParser.MultExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNumExpr(lyParser.NumExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNumExpr(lyParser.NumExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code plusExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPlusExpr(lyParser.PlusExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code plusExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPlusExpr(lyParser.PlusExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code indexExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIndexExpr(lyParser.IndexExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code indexExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIndexExpr(lyParser.IndexExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParExpr(lyParser.ParExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParExpr(lyParser.ParExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code compExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCompExpr(lyParser.CompExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCompExpr(lyParser.CompExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code prfExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPrfExpr(lyParser.PrfExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code prfExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPrfExpr(lyParser.PrfExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code falseExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFalseExpr(lyParser.FalseExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code falseExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFalseExpr(lyParser.FalseExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBoolExpr(lyParser.BoolExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBoolExpr(lyParser.BoolExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIdExpr(lyParser.IdExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link lyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIdExpr(lyParser.IdExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assStat}
	 * labeled alternative in {@link lyParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterAssStat(lyParser.AssStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assStat}
	 * labeled alternative in {@link lyParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitAssStat(lyParser.AssStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifStat}
	 * labeled alternative in {@link lyParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterIfStat(lyParser.IfStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifStat}
	 * labeled alternative in {@link lyParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitIfStat(lyParser.IfStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStat}
	 * labeled alternative in {@link lyParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterWhileStat(lyParser.WhileStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStat}
	 * labeled alternative in {@link lyParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitWhileStat(lyParser.WhileStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code readStat}
	 * labeled alternative in {@link lyParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterReadStat(lyParser.ReadStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code readStat}
	 * labeled alternative in {@link lyParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitReadStat(lyParser.ReadStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code printStat}
	 * labeled alternative in {@link lyParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterPrintStat(lyParser.PrintStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code printStat}
	 * labeled alternative in {@link lyParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitPrintStat(lyParser.PrintStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link lyParser#prfOp}.
	 * @param ctx the parse tree
	 */
	void enterPrfOp(lyParser.PrfOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link lyParser#prfOp}.
	 * @param ctx the parse tree
	 */
	void exitPrfOp(lyParser.PrfOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link lyParser#multOp}.
	 * @param ctx the parse tree
	 */
	void enterMultOp(lyParser.MultOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link lyParser#multOp}.
	 * @param ctx the parse tree
	 */
	void exitMultOp(lyParser.MultOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link lyParser#plusOp}.
	 * @param ctx the parse tree
	 */
	void enterPlusOp(lyParser.PlusOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link lyParser#plusOp}.
	 * @param ctx the parse tree
	 */
	void exitPlusOp(lyParser.PlusOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link lyParser#boolOp}.
	 * @param ctx the parse tree
	 */
	void enterBoolOp(lyParser.BoolOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link lyParser#boolOp}.
	 * @param ctx the parse tree
	 */
	void exitBoolOp(lyParser.BoolOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link lyParser#compOp}.
	 * @param ctx the parse tree
	 */
	void enterCompOp(lyParser.CompOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link lyParser#compOp}.
	 * @param ctx the parse tree
	 */
	void exitCompOp(lyParser.CompOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code charType}
	 * labeled alternative in {@link lyParser#type}.
	 * @param ctx the parse tree
	 */
	void enterCharType(lyParser.CharTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code charType}
	 * labeled alternative in {@link lyParser#type}.
	 * @param ctx the parse tree
	 */
	void exitCharType(lyParser.CharTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link lyParser#type}.
	 * @param ctx the parse tree
	 */
	void enterArrayType(lyParser.ArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link lyParser#type}.
	 * @param ctx the parse tree
	 */
	void exitArrayType(lyParser.ArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intType}
	 * labeled alternative in {@link lyParser#type}.
	 * @param ctx the parse tree
	 */
	void enterIntType(lyParser.IntTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link lyParser#type}.
	 * @param ctx the parse tree
	 */
	void exitIntType(lyParser.IntTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code voidType}
	 * labeled alternative in {@link lyParser#type}.
	 * @param ctx the parse tree
	 */
	void enterVoidType(lyParser.VoidTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code voidType}
	 * labeled alternative in {@link lyParser#type}.
	 * @param ctx the parse tree
	 */
	void exitVoidType(lyParser.VoidTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link lyParser#type}.
	 * @param ctx the parse tree
	 */
	void enterBoolType(lyParser.BoolTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link lyParser#type}.
	 * @param ctx the parse tree
	 */
	void exitBoolType(lyParser.BoolTypeContext ctx);
}