program functionSimple:

foo1() ~~ int {
	foo2() ~~ int {
		int x <- 7;
		return x;
	};
	
	return 5 + foo2();
};

print(foo1());