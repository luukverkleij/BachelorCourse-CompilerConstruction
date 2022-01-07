package test;

import test.compound.TestCompound;
import test.conditional.TestConditional;
import test.declaration.TestDeclaration;
import test.expression.TestExpression;
import test.function.TestFunction;

/**
 * This is where all the test can be run from
 */
public class TestComplete extends TestAbstract {

	private TestCompound cmp 		= new TestCompound();
	private TestConditional cond 	= new TestConditional();
	private TestDeclaration decl 	= new TestDeclaration();
	private TestExpression expr 	= new TestExpression();
	private TestFunction fnc 		= new TestFunction();
	
	@Override
	public void test() throws Exception {
		decl.test();
		expr.test();
		cmp.test();
		cond.test();
		fnc.test();
	}

}
