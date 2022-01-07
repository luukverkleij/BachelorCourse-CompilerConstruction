package test.conditional;

import test.TestAbstract;

public class TestConditional extends TestAbstract {

	@Override
	public void test() throws Exception {
		success("success_fib", "10", new String[]{"89"});
		success("success_ifelse", "", new String[]{"25", "12", "5", "5", "3", "4", "3", "14"});		
	
		failChecker("fail_if_nobool");
		failChecker("fail_while_nobool");
		failChecker("fail_while_void");
	}
}
