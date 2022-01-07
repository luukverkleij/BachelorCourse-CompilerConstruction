package compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

import compiler.result.Result;
import compiler.result.Scope;
import compiler.type.Type;
import compiler.type.TypeKind;
import grammar.lyBaseVisitor;
import grammar.lyParser;
import grammar.lyParser.BodypartContext;
import grammar.lyParser.FuncExprContext;
import grammar.lyParser.ParamContext;
import iloc.Simulator;
import iloc.model.Label;
import iloc.model.Num;
import iloc.model.Op;
import iloc.model.OpCode;
import iloc.model.Operand;
import iloc.model.Program;
import iloc.model.Reg;
import iloc.model.Str;

/** Class to generate ILOC code for ly. */
public class Generator extends lyBaseVisitor<Op> {
	/** The representation of the boolean value <code>false</code>. */
	public final static Num FALSE_VALUE = new iloc.model.Num(Simulator.FALSE);
	/** The representation of the boolean value <code>true</code>. */
	public final static Num TRUE_VALUE = new Num(Simulator.TRUE);

	/** The base register. */
	private Reg arp = new Reg("r_arp");
	/** The outcome of the checker phase. */
	private Result checkResult;
	/** Association of statement nodes to labels. */
	private ParseTreeProperty<Label> labels;
	/** The program being built. */
	private Program prog;
	
	/** The current scope **/
	private Scope scope;
	
	/** Storing functions so they an be done last */
	private List<ParseTree> functions;
	
	/** Registers **/
	
	/** Register count, used to generate fresh registers. */
	private int regCount;	
	/** Association of expression and target nodes to registers. */
	private ParseTreeProperty<Reg> regs;
	/** Stack of generic registers for those who are empty */
	private Stack<Reg> emptyRegs;
	/** List of register used for method calls*/
	private List<Reg> workingRegs;
	/** Storage of special registers, used in functions */
	private Map<String, Reg> varRegs;

	/** Generates a ILOC program based on a ly parsetree and the results of
	 * {@link Checker#check(ParseTree)} using the same parsetree
	 * @require tree != null
	 * @require checkResult != null
	 * @return a working program
	 */
	public Program generate(ParseTree tree, Result checkResult) {
		this.prog = new Program();
		this.checkResult = checkResult;
		
		scope = new Scope();
		
		this.regs = new ParseTreeProperty<>();
		this.emptyRegs = new Stack<Reg>();
		this.varRegs = new HashMap<String, Reg>();
		this.workingRegs = new ArrayList<Reg>();
		
		this.functions = new ArrayList<ParseTree>();
		
		this.labels = new ParseTreeProperty<>();
		this.regCount = 0;
		tree.accept(this);
		return this.prog;
	}	
	
	/* 
	 * Program and body
	 */
	
	@Override public Op visitProgram(lyParser.ProgramContext ctx) {
		visit(ctx.body());
		
		Label end = new Label("endProgram");
		emit(OpCode.jumpI, end);
		
		//Print here all the functions
		for(int i = 0; i < this.functions.size(); i++)
			visit(this.functions.get(i));
		
		emit(end, OpCode.nop);
		
		return null;
	}
	
	@Override public Op visitBody(lyParser.BodyContext ctx) {
		for(BodypartContext part : ctx.bodypart()) {
			if(part.function() != null || part.procedure() != null)
				this.functions.add(part);
			else {
				visit(part);
				
				// If the part is an expression, free the register
				if(part.expr() != null)
					forgetReg(part.expr());
			}
		}
		
		return null;
	}
	
	@Override public Op visitFuncBody(lyParser.FuncBodyContext ctx) {
		for(BodypartContext part : ctx.bodypart()) {
			if(part.function() != null || part.procedure() != null)
				this.functions.add(part);
			else {
				visit(part);
				
				// If the part is an expression, free the register
				if(part.expr() != null)
					forgetReg(part);
			}
		}
		
		visit(ctx.expr());
		this.regs.put(ctx, this.regs.get(ctx.expr()));
		
		return null;
	}
	
	/* Function Related */
	/*
	 * METHOD CALL
	 */
	@Override public Op visitFuncExpr(lyParser.FuncExprContext ctx) { 		
		List<Reg> pushList = pushAll();
		
		Label call = createMethodLabel(ctx.ID().getText());
		Label back = createLabel(ctx, "return");
		
		Reg[] regBack = reserveRegs(1);
		Op opBack = emit(OpCode.loadI, new Num(-1), regBack[0]);
		emit(OpCode.push, regBack[0]);
		forgetRegs(regBack);
		
		//String[] params = this.scope.funcParams(id); 
		for(int i = 0; i < ctx.expr().size(); i++) {		
			visit(ctx.expr(i));
			Reg toStack = this.regs.get(ctx.expr(i));
			emit(OpCode.push, toStack);
			forgetReg(ctx.expr(i));
		}
		
		emit(OpCode.jumpI, call);
		
		if(this.checkResult.getType(ctx) != new Type.Void()) 
			emit(back, OpCode.pop, reserveReg(ctx));
		else
			emit(back, OpCode.nop);
		
		popAll(pushList);
		
		opBack.getArgs().remove(0);
		opBack.getArgs().add(0, new Num(prog.getLine(back)));		
		
		return null;
	}
	
	@Override public Op visitFunction(lyParser.FunctionContext ctx) {
		this.scope = checkResult.getScope(ctx);
		System.out.println("Visiting funtion " + ctx.ID().getText());
		
		Label start = createMethodLabel(ctx.ID().getText());
		emit(start, OpCode.nop);
		
		for(ParamContext param : ctx.param())
			visit(param);
		
		visit(ctx.funcBody());	
		
		Reg[] jump = reserveRegs(1);		
		emit(OpCode.pop, jump[0]);
		emit(OpCode.push, regs.get(ctx.funcBody().expr()));
		emit(OpCode.jump, jump[0]);
		forgetRegs(jump);
		
		forgetReg(ctx.funcBody().expr());
		
		this.varRegs.clear();
		
		return null;
	}
	
	@Override public Op visitParam(lyParser.ParamContext ctx) {
		for(TerminalNode var: ctx.ID()) {
			emit(OpCode.pop, reg(var.getText()));
		}
		return null;
	}
	
	/*
	 * Not function related
	 */
	
	/* Compound */
	@Override public Op visitCompoundExpr(lyParser.CompoundExprContext ctx) { 		
		visitChildren(ctx);
		if(ctx.expr() != null) {
			regs.put(ctx, regs.get(ctx.expr()));
		}		
		return null;
	}
	
	/* Declaration */
	@Override public Op visitDecl(lyParser.DeclContext ctx) {	
		if(scope.inFunction()) {
			for(int i = 0; i < ctx.declpart().size(); i++) {
				if(ctx.declpart(i).expr() != null) {
					visit(ctx.declpart(i).expr());
					Reg expr = this.regs.get(ctx.declpart(i).expr());
					emit(OpCode.i2i, expr, reg(ctx.declpart(i).ID().getText()));
					forgetReg(ctx.declpart(i).expr());
				}
				else
					emit(OpCode.loadI, new Num(0), reg(ctx.declpart(i).ID().getText()));
			}
		}
		else {
			for(int i = 0; i < ctx.declpart().size(); i++) {
				if(ctx.declpart(i).expr() != null) {
					visit(ctx.declpart(i).expr());
					Reg expr = this.regs.get(ctx.declpart(i).expr());
					emit(OpCode.storeAI, expr, arp, offset(ctx.declpart(i)));
					forgetReg(ctx.declpart(i).expr());
				}
				else
					emit(OpCode.storeAI, arp, arp, offset(ctx.declpart(i)));
			}
		}
		
		return null;
	}
	
	/* Statement expression */
	@Override public Op visitStatExpr(lyParser.StatExprContext ctx) { 
		visitChildren(ctx);
		this.regs.put(ctx, this.regs.get(ctx.stat()));
		return null;
	}
	
	/* Assigment expression */	
	@Override public Op visitAssStat(lyParser.AssStatContext ctx) { 
		String id = ctx.ID().getText();
		
		visit(ctx.expr());
		forgetReg(ctx.expr());
		
		Reg result = reserveReg(ctx);
		if(this.scope.inFunction()) {			
			if(this.scope.type(id).isRef()) {
				emit(OpCode.store, result, reg(id));
			}
			else {
				Reg var = reg(id);			
				emit(OpCode.i2i, result, var);	
			}					
		}
		else {
			emit(OpCode.storeAI, result, arp, offset(ctx));
		}
					
		return null;
	}
	
	/* If Else expression */
	@Override public Op visitIfStat(lyParser.IfStatContext ctx) {
		Label thenL = createLabel(ctx, "then");
		Label elseL = createLabel(ctx, "else");
		Label end	= createLabel(ctx, "end");
		
		if(ctx.body() != null)
			visit(ctx.body());
		visit(ctx.expr(0));
		
		if(ctx.ELSE() != null)
			emit(OpCode.cbr, this.regs.get(ctx.expr(0)), thenL, elseL);
		else
			emit(OpCode.cbr, this.regs.get(ctx.expr(0)), thenL, end);
		forgetReg(ctx.expr(0));
		
		Reg resultReg = null;
		if(checkResult.getType(ctx).getKind() != TypeKind.VOID)
			resultReg = reserveRegs(1)[0];
		
		int lastInstr = this.prog.getInstr().size();
		visit(ctx.expr(1));
		if(resultReg != null) {
			emit(OpCode.i2i, regs.get(ctx.expr(1)), resultReg);
		}
		forgetReg(ctx.expr(1));
		this.prog.getInstr().get(lastInstr).setLabel(thenL);
		
		emit(OpCode.jumpI, end);
		
		if(ctx.ELSE() != null) {
			lastInstr = this.prog.getInstr().size();
			visit(ctx.expr(2));
			if(resultReg != null)
				emit(OpCode.i2i, regs.get(ctx.expr(2)), resultReg);
			forgetReg(ctx.expr(2));
			this.prog.getInstr().get(lastInstr).setLabel(elseL);
		}

		if(resultReg != null) {
			emit(end, OpCode.i2i, resultReg, reserveReg(ctx));
			forgetRegs(new Reg[]{resultReg});
		}
		else
			emit(end, OpCode.nop);
		
		//return new Op(end, OpCode.nop);
		return null;
	}
	
	/* While Expression */
	@Override public Op visitWhileStat(lyParser.WhileStatContext ctx) {
		Label start = createLabel(ctx, "start");
		Label body = createLabel(ctx, "body");
		Label end = createLabel(ctx, "end");
		int labelInstr = this.prog.getInstr().size();
				
		//emit(start, OpCode.nop);
		visit(ctx.expr(0));	
		this.prog.getInstr().get(labelInstr).setLabel(start);
		emit(OpCode.cbr, this.regs.get(ctx.expr(0)), body, end);
		forgetReg(ctx.expr(0));
		labelInstr = this.prog.getInstr().size();
		
		//emit(body, OpCode.nop);		
		
		visit(ctx.expr(1));
		this.prog.getInstr().get(labelInstr).setLabel(body);
		
		emit(OpCode.jumpI, start);
		emit(end, OpCode.nop);
		
		forgetReg(ctx.expr(1));		
		//return new Op(end, OpCode.nop);
		return null;
	}
	
	/* Print and Read */
	@Override public Op visitPrintStat(lyParser.PrintStatContext ctx) { 
		visitChildren(ctx);
		if(ctx.expr().size() > 1) {
			for(int i = 0; i < ctx.expr().size(); i++) {
				emit(OpCode.out, new Str(""), regs.get(ctx.expr(i)));
				forgetReg(ctx.expr(i));
			}
		}
		else {
			emit(OpCode.out, new Str(""), regs.get(ctx.expr(0)));
			this.regs.put(ctx, this.regs.get(ctx.expr(0)));
		}
		
		System.out.println("In Print, Working regs: ");
		for(Reg r : this.workingRegs) {
			System.out.println(r.getName());
		}
		System.out.println("In Print, End");
		
		return null;
	}
	
	@Override public Op visitReadStat(lyParser.ReadStatContext ctx) {
		Reg regIn = reserveReg(ctx);
		for(int i = 0; i < ctx.ID().size(); i++) {
			emit(OpCode.in, new Str(""), regIn);
			emit(OpCode.storeAI, regIn, arp, offset(ctx.ID(i)));
		}
		return null;
	}
	
	/* Expressions which do literally nothing (generator wise) */
	@Override public Op visitParExpr(lyParser.ParExprContext ctx) { 
		visit(ctx.expr());
		regs.put(ctx, regs.get(ctx.expr()));
		return null;
	}
	
	/* Expression that require two other expressions */
	@Override public Op visitPlusExpr(lyParser.PlusExprContext ctx) {
		visitChildren(ctx);
		
		Reg resultReg = reserveReg(ctx);
		Reg expr1 = regs.get(ctx.expr(0));
		Reg expr2 = regs.get(ctx.expr(1));
		Op result = visit(ctx.plusOp());
		
		emit(result.getOpCode(), expr1, expr2, resultReg);
		
		forgetReg(ctx.expr(0));
		forgetReg(ctx.expr(1));
		return null;
	}
	
	@Override public Op visitMultExpr(lyParser.MultExprContext ctx) { 
		visitChildren(ctx);
		
		Reg resultReg = reserveReg(ctx);
		Reg expr1 = regs.get(ctx.expr(0));
		Reg expr2 = regs.get(ctx.expr(1));
		Op result = visit(ctx.multOp());
		
		emit(result.getOpCode(), expr1, expr2, resultReg);	
		
		forgetReg(ctx.expr(0));
		forgetReg(ctx.expr(1));
		
		return null;
	}
	
	@Override public Op visitCompExpr(lyParser.CompExprContext ctx) { 
		visitChildren(ctx);
		
		Reg resultReg = reserveReg(ctx);
		Reg expr1 = regs.get(ctx.expr(0));
		Reg expr2 = regs.get(ctx.expr(1));
		Op result = visit(ctx.compOp());

		emit(result.getOpCode(), expr1, expr2, resultReg);	
		
		forgetReg(ctx.expr(0));
		forgetReg(ctx.expr(1));
		
		return null;
	}
	
	@Override public Op visitPrfExpr(lyParser.PrfExprContext ctx) { 
		visitChildren(ctx);
		
		Reg resultReg = reserveReg(ctx);
		Reg expr = regs.get(ctx.expr());
		Op result = visit(ctx.prfOp());
		
		emit(result.getOpCode(), expr, result.getArgs().get(1), resultReg);
		
		forgetReg(ctx.expr());
		return null; 
	}
	
	@Override public Op visitBoolExpr(lyParser.BoolExprContext ctx) { 
		visitChildren(ctx);
		
		Reg resultReg = reserveReg(ctx);
		Reg expr1 = regs.get(ctx.expr(0));
		Reg expr2 = regs.get(ctx.expr(1));
		Op result = visit(ctx.boolOp());
		
		emit(result.getOpCode(), expr1, expr2, resultReg);
		
		forgetReg(ctx.expr(0));
		forgetReg(ctx.expr(1));
		return null;
	}	
	
	/* Expressions that finish */
	@Override public Op visitNumExpr(lyParser.NumExprContext ctx) { 
		emit(OpCode.loadI, new Num(Integer.parseInt(ctx.getText())), reserveReg(ctx));		
		return null;
	}
	
	@Override public Op visitCharExpr(lyParser.CharExprContext ctx) { 
		emit(OpCode.loadI, new Num((int)(ctx.CHR().getText().charAt(1))), reserveReg(ctx));		
		return null;
	}
	
	@Override public Op visitIdExpr(lyParser.IdExprContext ctx) { 	
		String id = ctx.ID().getText();
		if(this.scope.inFunction()) {
			if(this.scope.type(id).isRef()) {
				this.emit(OpCode.load, reg(id), reserveReg(ctx));
			}
			else {
				this.regs.put(ctx, reg(id));
			}
		}
		else
			this.emit(OpCode.loadAI, arp, offset(ctx), reserveReg(ctx));
		return null;
	}
	
	@Override public Op visitFalseExpr(lyParser.FalseExprContext ctx) { 
		emit(OpCode.loadI, FALSE_VALUE, reserveReg(ctx));		
		return null;
	}
	
	@Override public Op visitTrueExpr(lyParser.TrueExprContext ctx) { 
		emit(OpCode.loadI, TRUE_VALUE, reserveReg(ctx));		
		return null;
	}
	
	/*
	 * OPERATORS
	 */
	
	@Override public Op visitPrfOp(lyParser.PrfOpContext ctx) { 
		Op result = null;
		
		if(ctx.MINUS() != null) 	result = new Op(OpCode.multI, Reg.empty, new Num(-1), Reg.empty);
		else if(ctx.NOT() != null) 	result = new Op(OpCode.xorI, Reg.empty, new Num(0), Reg.empty);
		
		return result;
	}

	@Override public Op visitMultOp(lyParser.MultOpContext ctx) { 
		Op result = null;	
		
		if(ctx.SLASH() != null) 	result = new Op(OpCode.div, Reg.empty, Reg.empty, Reg.empty);
		else if(ctx.STAR() != null)	result = new Op(OpCode.mult, Reg.empty, Reg.empty, Reg.empty);
		
		return result;
	}

	@Override public Op visitPlusOp(lyParser.PlusOpContext ctx) { 
		Op result = null;
		
		if(ctx.PLUS() != null) 			result = new Op(OpCode.add, Reg.empty, Reg.empty, Reg.empty);
		else if(ctx.MINUS() != null) 	result = new Op(OpCode.sub, Reg.empty, Reg.empty, Reg.empty);
		
		return result;
	}
	
	@Override public Op visitBoolOp(lyParser.BoolOpContext ctx) { 
		Op result = null;
		
		if(ctx.OR() != null) 		result = new Op(OpCode.or, Reg.empty, Reg.empty, Reg.empty);
		else if(ctx.AND() != null) 	result = new Op(OpCode.and, Reg.empty, Reg.empty, Reg.empty);
		
		return result;
	}
	
	@Override public Op visitCompOp(lyParser.CompOpContext ctx) {
		Op result = null;
		
		if(ctx.EQ() != null) 		result = new Op(OpCode.cmp_EQ, Reg.empty, Reg.empty, Reg.empty);
		else if(ctx.GE() != null) 	result = new Op(OpCode.cmp_GE, Reg.empty, Reg.empty, Reg.empty);
		else if(ctx.GT() != null) 	result = new Op(OpCode.cmp_GT, Reg.empty, Reg.empty, Reg.empty);
		else if(ctx.LE() != null) 	result = new Op(OpCode.cmp_GE, Reg.empty, Reg.empty, Reg.empty);
		else if(ctx.LT() != null) 	result = new Op(OpCode.cmp_LT, Reg.empty, Reg.empty, Reg.empty);
		else if(ctx.NE() != null) 	result = new Op(OpCode.cmp_NE, Reg.empty, Reg.empty, Reg.empty);
			
		return result;
	}

	// Override the visitor methods
	/** Constructs an operation from the parameters 
	 * and adds it to the program under construction. */
	private Op emit(Label label, OpCode opCode, Operand... args) {
		Op result = new Op(label, opCode, args);
		this.prog.addInstr(result);
		return result;
	}

	/** Constructs an operation from the parameters 
	 * and adds it to the program under construction. */
	private Op emit(OpCode opCode, Operand... args) {
		return emit((Label) null, opCode, args);
	}

	/** 
	 * Looks up the label for a given parse tree node,
	 * creating it if none has been created before.
	 * The label is actually constructed from the entry node
	 * in the flow graph, as stored in the checker result.
	 */
	private Label label(ParserRuleContext node) {
		Label result = this.labels.get(node);
		if (result == null) {
			ParserRuleContext entry = this.checkResult.getEntry(node);
			result = createLabel(entry, "n");
			this.labels.put(node, result);
		}
		return result;
	}

	/** Creates a label for a given parse tree node and prefix. */
	private Label createLabel(ParserRuleContext node, String prefix) {
		Token token = node.getStart();
		int line = token.getLine();
		int column = token.getCharPositionInLine();
		String result = prefix + "_" + line + "_" + column;
		return new Label(result);
	}

	/** Retrieves the offset of a variable node from the checker result,
	 * wrapped in a {@link Num} operand. */
	private Num offset(ParseTree node) {
		return new Num(this.checkResult.getOffset(node));
	}

	/** Returns a register for a given parse tree node,
	 * creating a fresh register if there is none for that node. */	
	private Reg reserveReg(ParseTree node) {		
		Reg result = this.regs.get(node);
		if (result == null) {
			if(emptyRegs.empty()) {
				result = new Reg("r_" + this.regCount++);
				this.regs.put(node, result);
			}
			else {
				result = emptyRegs.pop();
				this.regs.put(node, result);
			}
		}	
		
		if(result.getName().equals("r_0") && node instanceof FuncExprContext) {
			System.out.println("Reserved r_0 for: " + node.getText() + ", " + node.getClass().getName());
		}
		
		this.workingRegs.add(result);
		return result;
	}
	
	/**
	 * Reservers a number of regs based on {@link Generator#emptyRegs} and {@link Generator#regCount}
	 * @require num > 0
	 * @return result.length = num
	 */
	private Reg[] reserveRegs(int num) {
		Reg[] result = new Reg[num];
		for(int i = 0; i < result.length; i++) {
			if(emptyRegs.empty()) {
				result[i] = new Reg("r_" + this.regCount++);
			}
			else {
				result[i] = emptyRegs.pop();
			}
		}
		return result;
	}

	/**
	 * Free the registers gotten from {@link Generator#reserveReg(ParseTree)} here
	 * @require node != null
	 */
	private void forgetReg(ParseTree node) {
		Reg reg = this.regs.get(node);
		
		if(reg != null && !this.varRegs.containsValue(reg)) {
			if(reg.getName().equals("r_0")) {
				System.out.println("Forgot r_0 for: " + node.getText());
			}
			
			this.regs.removeFrom(node);
			this.emptyRegs.push(reg);
			this.workingRegs.remove(reg);
		}
	}
	
	/**
	 * Free the registers gotten from {@link Generator#reserveRegs(int)} here
	 * @require regs != null
	 */
	private void forgetRegs(Reg[] regs) {
		for(int i = 0; i < regs.length; i++) {
			this.emptyRegs.push(regs[i]);
		}
	}
	
	/**
	 * Used in functions, registers variables to there own special register
	 * @require id != null
	 */
	private Reg reg(String id) {
		String name = this.scope.getName() + "_" + id;
		if(!this.varRegs.containsKey(name))
			this.varRegs.put(name, new Reg("r_" + name));
		return this.varRegs.get(name);
		
	}
	
	/**
	 * Creates a label for methods
	 */
	private Label createMethodLabel(String id) {
		return new Label("m_" + id);
	}
	
	/**
	 * Used for functions. Pushes all declared variables, parameteres and registers that are in use, so they
	 * can be overwritten by another function
	 * Used when a function is called within a function
	 * @return a list of registers that has been pushed in the stack
	 */
	private List<Reg> pushAll() {
		ArrayList<Reg> result = new ArrayList<Reg>();
		
		for(Reg r : varRegs.values()) {
			emit(OpCode.push, r);
			result.add(r);
		}
		for(Reg r : workingRegs) {
			emit(OpCode.push, r);
			result.add(r);
		}
		
		
		return result;
	}
	/**
	 * Uses this in combination with {@link Generator#pushAll()}. Does the same thing as pushAll, only with a given list
	 * and popping, instead of pushing.
	 * @param list The list gotten from {@link Generator#pushAll()}
	 */
	private void popAll(List<Reg> list) {
		for(int i = list.size()-1; i >= 0; i--) {
			emit(OpCode.pop, list.get(i));
		}
	}
}
