package test.expression;

import iloc.Simulator;

import java.io.IOException;

import grammar.lyParser;

import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.tree.ParseTree;

import compiler.exception.ParseException;
import test.TestAbstract;

public class TestExpression extends TestAbstract {

	@Override
	public void test() throws Exception {
		testDifferentTypeFail();
		success("success", "4\nb", new String[]{"16", ""+Simulator.TRUE, ""+Simulator.FALSE});
		failChecker("fail_assign");
		failChecker("type_error");
		failChecker("empty_ID");
		failChecker("operator_error");
		failChecker("operator_error2");
		failChecker("violatecontext1");
		failChecker("run_time_error1");
		
	}
	
	/** Checks for the all operators if two different types throws an error. 
	 * Types checked: see {@link testFailOperator}
	 * Operators checked: &&, ||, ==, !=, >=, <=, <, >, +, -, <-
	 */
	public void testDifferentTypeFail() throws IOException, ParseException {
		testFailOperator(lyParser.AND);
		testFailOperator(lyParser.OR);
		
		testFailOperator(lyParser.EQ);
		testFailOperator(lyParser.NE);
		testFailOperator(lyParser.GE);
		testFailOperator(lyParser.LE);
		testFailOperator(lyParser.LT);
		testFailOperator(lyParser.GT);
		
		testFailOperator(lyParser.PLUS);
		testFailOperator(lyParser.MINUS);
		
		testFailOperator(lyParser.ASS);
	}
	
	/**
	 * Checks the given operator for the following:
	 * integer and char,
	 * integer and boolean,
	 * char and integer,
	 * char and boolean,
	 * boolean and integer,
	 * boolean and char
	 * 
	 * @param operator see {@link lyParser}
	 */
	public void testFailOperator(int operator) throws IOException, ParseException {
		failChecker(createProgram(new Integer(1), new Character('a'), operator));
		failChecker(createProgram(new Integer(1), new Boolean(true), operator));
		
		failChecker(createProgram(new Character('a'), new Integer(1), operator));
		failChecker(createProgram(new Character('a'), new Boolean(true), operator));
		
		failChecker(createProgram(new Boolean(true), new Integer(1), operator));
		failChecker(createProgram(new Boolean(true), new Character('a'), operator));
	}
	
	/**
	 * Creates the following program in the return string:
	 * 
	 * (char/bool/int) var1;
	 * (char/bool/int) var2;
	 * var1 <- (value of first);
	 * var2 <- (value of second);
	 * print(var1 (op) var2);
	 */
	public ParseTree createProgram(Object first, Object second, int op) throws ParseException {		
		String program = "program test:";
		Vocabulary voc = lyParser.VOCABULARY;
		
		if(first instanceof Character) program += voc.getLiteralName(lyParser.CHAR).replaceAll("'", "");
		if(first instanceof Integer) program += voc.getLiteralName(lyParser.INT).replaceAll("'", "");
		if(first instanceof Boolean) program += voc.getLiteralName(lyParser.BOOL).replaceAll("'", "");
		program += " var1;";
		
		if(second instanceof Character) program += voc.getLiteralName(lyParser.CHAR).replaceAll("'", "");
		if(second instanceof Integer) program += voc.getLiteralName(lyParser.INT).replaceAll("'", "");
		if(second instanceof Boolean) program += voc.getLiteralName(lyParser.BOOL).replaceAll("'", "");
		program += " var2;";
		
		program += "var1 " + voc.getLiteralName(lyParser.ASS).replaceAll("'", "") + " " + objString(first) + ";";
		program += "var2 " + voc.getLiteralName(lyParser.ASS).replaceAll("'", "") + " " + objString(second) + ";";
		
		program += voc.getLiteralName(lyParser.PRINT).replaceAll("'", "") + "(var1 " + voc.getLiteralName(op).replaceAll("'", "") + " var2);";
		
		return this.compiler.parse(program);
	}
	
	/**
	 * @require o != null
	 * @return if char: result = ' + obj.toString() + ', else obj.toString()
	 */
	public String objString(Object o) {
		if(o instanceof Character) {
			return "'" + o.toString() + "'";
		}
		return o.toString();
	}

}
