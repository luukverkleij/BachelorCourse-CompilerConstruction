program fib:

fib(int n) ~~ int {
	int x;
	if(n = 0)
		x <- 0
	else {
		if(n = 1)
			x <- 1
		else {
			#int fib1 <- fib(n-1);
			#int fib2 <- fib(n-2);
			x <- fib(n-1) + fib(n-2);
		};
	};
	
	return x;
};

int x;
read(x);
print(fib(x));

