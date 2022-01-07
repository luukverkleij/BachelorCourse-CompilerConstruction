package test.function;

import java.io.IOException;

import org.junit.Test;

import test.TestAbstract;
import compiler.compiler.LyCompiler;
import compiler.exception.ParseException;

public class TestFunction extends TestAbstract {
	
	@Override 
	public void test() throws ParseException, IOException {
		success("success_simple", "", new String[]{"15"});
		success("success_inline", "", new String[]{"12"});
		success("success_fib", "11", new String[]{"89"});
	
		failChecker("fail_parameters");
		failChecker("fail_procedure_void");
	}
	
}
