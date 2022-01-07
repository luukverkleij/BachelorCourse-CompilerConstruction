program fib:

int x,y,z,n;
x <- y <- z <- 1;
read(n);

while(n > 1) {
	z <- x + y;
	x <- y;
	y <- z;
	n <- n-1;
};

print(z);