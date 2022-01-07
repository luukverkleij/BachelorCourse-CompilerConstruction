program expression:

int readint;
readint <- 4;
#read(readint);

const int civar1 <- 5, civar2 <- 13;
print((-civar1 + civar2) * (readint / 2));

const char cvar <- 'a';
char readchar <- 'b';
#read(readchar);

print(readchar != cvar);

const bool btrue <- true;
const bool bfalse <- false;

print((btrue && bfalse) = (btrue || bfalse));
