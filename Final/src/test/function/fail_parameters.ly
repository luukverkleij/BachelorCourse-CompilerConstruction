program fib:

int q, w, e, r;
q <- 5;
w <- q*q;
e <- w / 15;
r <- q*w*e;

foo1() ~~ int {
	int x <- 5;
	return x;
};

foo2() ~~ int {
	int x <- 10;
	return x;
};

print(foo1(x) + foo2(y, z));

