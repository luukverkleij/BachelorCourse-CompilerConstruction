package compiler;

import grammar.lyBaseListener;
import grammar.lyParser.*;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import compiler.exception.ParseException;
import compiler.result.*;
import compiler.type.*;


/** Class to type check and calculate flow entries and variable offsets. */
public class Checker extends lyBaseListener {
	/** Result of the latest call of {@link #check}. */
	private Result result;
	/** Variable scope for the latest call of {@link #check}. */
	private Scope scope;
	/** List of errors collected in the latest call of {@link #check}. */
	private List<String> errors;

	/** Runs this checker on a given parse tree,
	 * and returns the checker result.
	 * @throws ParseException if an error was found during checking.
	 */
	public Result check(ParseTree tree) throws ParseException {
		this.scope = new Scope();
		this.result = new Result();
		this.errors = new ArrayList<>();
		try {
			new ParseTreeWalker().walk(this, tree);
		}
		catch(Exception e) {
			if(hasErrors()) {
				throw new ParseException(getErrors());
			}
			else
				throw e;
		}
		if (hasErrors()) {
			throw new ParseException(getErrors());
		}
		return this.result;
	}
	
	/*
	 * Program and Bodies
	 */
	
	@Override
	public void exitBody(BodyContext ctx) {
		setEntry(ctx, entry(ctx.bodypart(0)));
	}

	@Override
	public void exitFuncBody(FuncBodyContext ctx) {
		if( ctx.bodypart(0) != null )
			setEntry(ctx, entry(ctx.bodypart(0)));
		else
			setEntry(ctx, entry(ctx.expr()));
		
		setType(ctx, getType(ctx.expr()));
	}
	
	@Override
	public void exitProcBody(ProcBodyContext ctx) {
		if( ctx.bodypart(0) != null )
			setEntry(ctx, entry(ctx.bodypart(0)));
		else
			setEntry(ctx, ctx);
		setType(ctx, new Type.Void());
	}

	@Override
	public void exitBodypart(BodypartContext ctx) {
		if( ctx.decl() != null )
			setEntry(ctx, entry(ctx.decl()));
		else if(ctx.expr() != null)
			setEntry(ctx, entry(ctx.expr()));
		else if(ctx.function() != null)
			setEntry(ctx, entry(ctx.function()));
		else if(ctx.procedure() != null)
			setEntry(ctx, entry(ctx.procedure()));
		
	}

	/*
	 * Declaration
	 */
	
	@Override
	public void exitDecl(DeclContext ctx) {
		Type type = getType(ctx.type());
		type.setConst(ctx.CONST() != null);
		
		for(int i = 0; i < ctx.declpart().size(); i++) {
			String id = ctx.declpart(i).ID().getText();
			if(!this.scope.put(id, type)) {
				addError(ctx.declpart(i), "Variable %s was already declared!", id);
			}
			if(ctx.declpart(i).expr() != null)
				checkType(ctx.declpart(i).expr(), type.getKind());
			
			setOffset(ctx.declpart(i), this.scope.offset(id));
		}
		setEntry(ctx, ctx);
	}
	
	/*
	 * MISC
	 * TODO: ORDER
	 */
	
	@Override 
	public void enterProcedure(ProcedureContext ctx) {
		Type type = new Type.Void();
		String id = ctx.ID().getText();
		
		int paramsNum = 0;
		for(ParamContext p : ctx.param())
			paramsNum += p.ID().size();
		
		String[] params = new String[paramsNum];
		int index = 0;
		for(ParamContext p : ctx.param()) {
			for(TerminalNode node : p.ID()) {
				params[index++] = node.getText();
			}
		}	
		
		boolean declared = this.scope.putFunction(id, type, params);
		if(!declared) {
			addError(ctx.ID().getSymbol(), "Function %s was already declared", id);
		}
		
		this.openFunctionScope(ctx, id, params);
	}

	@Override
	public void exitProcedure(ProcedureContext ctx) {
		setEntry(ctx, entry(ctx.procBody()));
	}
	
	@Override
	public void exitStatExpr(StatExprContext ctx) {
		setEntry(ctx, entry(ctx.stat()));
		setType(ctx, getType(ctx.stat()));
	}
 
	@Override
	public void enterFunction(FunctionContext ctx) {
		Type type = typeFromContext(ctx.type());
		String id = ctx.ID().getText();
		
		int paramsNum = 0;
		for(ParamContext p : ctx.param())
			paramsNum += p.ID().size();
		
		String[] params = new String[paramsNum];
		int index = 0;
		for(ParamContext p : ctx.param()) {
			for(TerminalNode node : p.ID()) {
				params[index++] = node.getText();
			}
		}		
		
		boolean declared = this.scope.putFunction(id, type, params);
		if(!declared) {
			addError(ctx.ID().getSymbol(), "Function %s was already declared", id);
		}
		
		this.openFunctionScope(ctx, id, params);
	}
	
	@Override
	public void exitParam(ParamContext ctx) {
		Type type = getType(ctx.type());
		type.setConst(ctx.CONST() != null);
		
		for(int i = 0; i < ctx.ID().size(); i++) {
			String id = ctx.ID(i).getText();
			if(!this.scope.put(id, type)) {
				addError(ctx, "Variable %s was already declared!", id);
			}
		}
		setEntry(ctx, ctx);
	}
	
	@Override
	public void exitFunction(FunctionContext ctx) {	
		this.result.setScope(ctx, this.scope);
		this.closeScope();
		Type funcType = getType(ctx.type());
		String id  = ctx.ID().getText();		

		Type returnType = getType(ctx.funcBody());
		if(getType(ctx.funcBody()).getKind() != funcType.getKind() ) {
			addError(ctx, "Return type %s does not match funtion type %s", returnType.toString(), funcType.toString());
		}
		else {
			setEntry(ctx, ctx.funcBody());
		}
	}

	@Override
	public void exitFuncExpr(FuncExprContext ctx) {
		String id = ctx.ID().getText();
		Type funcType = this.scope.funcType(id);
		
		if(!this.scope.containsFunction(id)) {
			addError(ctx, "Function %s is not declared!", id);
		}
		
		if(this.scope.funcParams(id).length != ctx.expr().size()) {
			addError(ctx, "Function %s requires %d parameter(s), but was called with %d argument(s)",
					id, this.scope.funcParams(id).length, ctx.expr().size());
		}
		
		//System.out.println("Exit Func Expr, Type: " + funcType.toString());
		
		setEntry(ctx, ctx);
		setType(ctx, funcType);
	}
	
	/*
	 * STATMENTS
	 */
	
	/* ASSIGNMENT */
	
	@Override
	public void exitAssStat(AssStatContext ctx) {
		String id = ctx.ID().getText();
		
		if(!this.scope.contains(id)) {
			addError(ctx, "Variable %s was not declared!", id);
		}
		
		Type type = this.scope.type(id);
		int offset = this.scope.offset(id);
		
		checkType(ctx.expr(), type.getKind());
		if(type.isConst()) {
			addError(ctx, "The constant variable %s is constant", ctx.getText());
		}
		
		setType(ctx, type);
		setOffset(ctx, offset);
		
		setEntry(ctx, entry(ctx.expr()));
	}
	
	/* Conditionals (While and If) */
	@Override 
	public void enterWhileStat(WhileStatContext ctx) {
		openScope(ctx);
	}

	@Override
	public void exitWhileStat(WhileStatContext ctx) {
		closeScope();
		
		checkType(ctx.expr(0), TypeKind.BOOL);
		setEntry(ctx, entry(ctx.expr(0)));
		setType(ctx, new Type.Void());
	}
	
	@Override
	public void enterIfStat(IfStatContext ctx) {
		openScope(ctx);
	}
	
	@Override
	public void exitIfStat(IfStatContext ctx) {
		checkType(ctx.expr(0), TypeKind.BOOL);
		setEntry(ctx, entry(ctx.expr(0)));
		
		setType(ctx, new Type.Void());
		if(ctx.expr().size() > 2) {
			Type type1 = getType(ctx.expr(1));
			Type type2 = getType(ctx.expr(2));
			
			if(type1.getKind() == type2.getKind())
				setType(ctx, type1);
		}
		
		closeScope();
	}	
	
	/*
	 * EXPRESSION
	 */
	
	/* Compound and Par */
	public void enterCompoundExpr(CompoundExprContext ctx) {
		openScope(ctx);
	}
	
	@Override
	public void exitCompoundExpr(CompoundExprContext ctx) {
		closeScope();
		setEntry(ctx, entry(ctx.expr()));
		setType(ctx, getType(ctx.expr()));
	}
	
	@Override
	public void exitParExpr(ParExprContext ctx) {
		setType(ctx, getType(ctx.expr()));
		setEntry(ctx, entry(ctx.expr()));
	}
	
	/* Read and Write */
	
	@Override
	public void exitReadStat(ReadStatContext ctx) {
		setEntry(ctx, ctx);
		if(ctx.ID().size() > 1)
			setType(ctx, new Type.Void());
		else
			setType(ctx, this.scope.type(ctx.ID(0).getText()));
		
		for(TerminalNode node : ctx.ID()) {
			setOffset(node, this.scope.offset(node.getText()));
		}
	}
	
	@Override
	public void exitPrintStat(PrintStatContext ctx) {
		for(ExprContext expr : ctx.expr()) {
			checkNotVoid(expr);
		}
		
		setEntry(ctx, entry(ctx.expr(0)));
		if(ctx.expr().size() > 1)
			setType(ctx, new Type.Void());
		else
			setType(ctx, getType(ctx.expr(0)));
	}
	
	/* Algoratmic Expressions */
	@Override
	public void exitMultExpr(MultExprContext ctx) {
		checkType(ctx.expr(0), TypeKind.INT);
		checkType(ctx.expr(1), TypeKind.INT);
		setType(ctx, new Type.Int());
		setEntry(ctx, entry(ctx.expr(0)));
	}
	
	@Override
	public void exitPlusExpr(PlusExprContext ctx) {
		checkType(ctx.expr(0), TypeKind.INT);
		checkType(ctx.expr(1), TypeKind.INT);
		
		setType(ctx, new Type.Int());
		setEntry(ctx, entry(ctx.expr(0)));
	}
	
	@Override
	public void exitCompExpr(CompExprContext ctx) {
		if(ctx.compOp().EQ() != null || ctx.compOp().NE() != null) {
			Type type = getType(ctx.expr(0));
			checkType(ctx.expr(1), type.getKind());
			checkNotVoid(ctx.expr(0));
		}
		else {
			checkType(ctx.expr(0), TypeKind.INT);
			checkType(ctx.expr(1), TypeKind.INT);
		}
		
		
		setType(ctx, new Type.Bool());
		setEntry(ctx, entry(ctx.expr(0)));
	}

	@Override
	public void exitPrfExpr(PrfExprContext ctx) {
		if (ctx.prfOp().MINUS() != null) {
			checkType(ctx.expr(), TypeKind.INT);
		} else {
			assert ctx.prfOp().NOT() != null;
			checkType(ctx.expr(), TypeKind.BOOL);
		}
		
		setType(ctx, getType(ctx.expr()));
		setEntry(ctx, entry(ctx.expr()));
	}
	
	@Override
	public void exitBoolExpr(BoolExprContext ctx) {
		checkType(ctx.expr(0), TypeKind.BOOL);
		checkType(ctx.expr(1), TypeKind.BOOL);
		setType(ctx, new Type.Bool());
		setEntry(ctx, entry(ctx.expr(0)));
	}

	
	/* Expression that finish*/

	@Override
	public void exitTrueExpr(TrueExprContext ctx) {
		setType(ctx, new Type.Bool());
		setEntry(ctx, ctx);
	}

	@Override
	public void exitNumExpr(NumExprContext ctx) {
		setType(ctx, new Type.Int());
		setEntry(ctx, ctx);
	}
	
	@Override
	public void exitCharExpr(CharExprContext ctx) {
		setType(ctx, new Type.Char());
		setEntry(ctx, ctx);
	}

	@Override
	public void exitFalseExpr(FalseExprContext ctx) {
		setType(ctx, new Type.Bool());
		setEntry(ctx, ctx);
	}


	/* ID Expression */

	@Override
	public void exitIdExpr(IdExprContext ctx) {
		String id = ctx.ID().getText();
		Type type = this.scope.type(id);
		
		if (!this.scope.contains(id)) {
			System.out.println("Wub!");
			addError(ctx, "Variable '%s' not declared", id);
		} else {
			setType(ctx, type);
			setOffset(ctx, this.scope.offset(id));
			setEntry(ctx, ctx);
		}
	}
	
	/* 
	 * Types
	 */

	@Override
	public void exitCharType(CharTypeContext ctx) {
		setType(ctx, new Type.Char());
	}

	@Override
	public void exitIntType(IntTypeContext ctx) {
		setType(ctx, new Type.Int());
	}

	@Override
	public void exitBoolType(BoolTypeContext ctx) {
		setType(ctx, new Type.Bool());
	}
	
	public Type typeFromContext(TypeContext ctx) {
		if(ctx instanceof CharTypeContext)
			return new Type.Char();
		else if(ctx instanceof IntTypeContext)
			return new Type.Int();
		else if(ctx instanceof BoolTypeContext)
			return new Type.Bool();
		return null;
	}

	/** Indicates if any errors were encountered in this tree listener. */
	public boolean hasErrors() {
		return !getErrors().isEmpty();
	}

	/** Returns the list of errors collected in this tree listener. */
	public List<String> getErrors() {
		return this.errors;
	}

	/** Checks the inferred type of a given parse tree,
	 * and adds an error if it does not correspond to the expected type.
	 */
	private void checkType(ParserRuleContext node, TypeKind expected) {
		Type actual = getType(node);
		if (actual == null) {
			throw new IllegalArgumentException("Missing inferred type of "
					+ node.getText());
		}
		if (!(actual.getKind() == expected)) {
			addError(node, "Expected type '%s' but found '%s'", expected,
					actual);
		}
	}
	
	/** Checks the inferred type of a given parse tree,
	 * and adds an error if it does not correspond to the expected type.
	 */
	private void checkNotVoid(ParserRuleContext node) {
		Type actual = getType(node);
		if (actual == null) {
			throw new IllegalArgumentException("Missing inferred type of "
					+ node.getText());
		}
		if (actual.getKind() == TypeKind.VOID) {
			addError(node, "Expected Int, Char or Boolean, but got Void");
		}
	}
	
	/** Records an error at a given parse tree node.
	 * @param ctx the parse tree node at which the error occurred
	 * @param message the error message
	 * @param args arguments for the message, see {@link String#format}
	 */
	private void addError(ParserRuleContext node, String message,
			Object... args) {
		addError(node.getStart(), message, args);
	}

	/** Records an error at a given token.
	 * @param token the token at which the error occurred
	 * @param message the error message
	 * @param args arguments for the message, see {@link String#format}
	 */
	private void addError(Token token, String message, Object... args) {
		int line = token.getLine();
		int column = token.getCharPositionInLine();
		message = String.format(message, args);
		message = String.format("Line %d:%d - %s", line, column, message);
		this.errors.add(message);
	}

	/** Convenience method to add an offset to the result. */
	private void setOffset(ParseTree node, Integer offset) {
		this.result.setOffset(node, offset);
	}

	/** Convenience method to add a type to the result. */
	private void setType(ParseTree node, Type type) {
		this.result.setType(node, type);
	}

	/** Returns the type of a given expression or type node. */
	private Type getType(ParseTree node) {
		return this.result.getType(node);
	}

	/** Convenience method to add a flow graph entry to the result. */
	private void setEntry(ParseTree node, ParserRuleContext entry) {
		if (entry == null) {
			throw new IllegalArgumentException("Null flow graph entry");
		}
		this.result.setEntry(node, entry);
	}

	/** Returns the flow graph entry of a given expression or statement. */
	private ParserRuleContext entry(ParseTree node) {
		return this.result.getEntry(node);
	}
	
	/** Opens a new scope */
	private void openScope(ParseTree node) {
		this.scope = this.scope.openScope();
		this.result.setScope(node, this.scope);
	}
	
	private void openFunctionScope(ParseTree node, String id, String[] params) {
		this.scope = this.scope.openFunctionScope(id, params);
		this.result.setScope(node, this.scope);
	}
	
	/** Closes this scopes and opens the previous one **/
	private void closeScope() {
		this.scope = this.scope.closeScope();
	}
}
