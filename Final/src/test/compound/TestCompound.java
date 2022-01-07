package test.compound;

import test.TestAbstract;

public class TestCompound extends TestAbstract {

	@Override
	public void test() throws Exception {
		success("success", "3\n5\n2\n0\n97", new String[]{"3", "5", "0", "-1", "2","0", "0", "97", "0", "4", "0", "98"});
		failChecker("voidfail");
		
	}
	
}
