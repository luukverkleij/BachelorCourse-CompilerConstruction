grammar ly;

program : PROGRAM ID COLON body;

body : (bodypart SEMI)+;
funcBody : LBRACE (bodypart SEMI)* RETURN expr SEMI RBRACE;
procBody: LBRACE (bodypart SEMI)* RBRACE;
bodypart : decl | expr | function | procedure;
	 
function : ID LPAR (param (COMMA param)*)? RPAR FNCTYPE type funcBody;		
procedure : ID LPAR (param (COMMA param)*)? RPAR procBody;
param: CONST? type REF? ID (COMMA ID)*;

decl: CONST? type declpart (COMMA declpart)*;	
declpart: ID (ASS expr)?;

/** Expression. */
expr: prfOp expr        					#prfExpr
    | expr plusOp expr  					#plusExpr
    | expr multOp expr  					#multExpr
    | expr compOp expr  					#compExpr
    | expr boolOp expr  					#boolExpr
    | LPAR expr RPAR    					#parExpr
    | ID LPAR (expr (COMMA expr)*)? RPAR   	#funcExpr
    | LBRACE body? expr SEMI RBRACE 		#compoundExpr
    | ID LBLOCK expr RBLOCK					#indexExpr
    | LBLOCK (expr (COMMA expr)*)? RBLOCK	#arrayExpr
    | stat									#statExpr
    | ID                					#idExpr
    | NUM               					#numExpr
    | CHR									#charExpr
    | TRUE              					#trueExpr
    | FALSE             					#falseExpr
    ;
    
/** Statement. */
stat: ID ASS expr                				#assStat
    | IF LPAR body? expr RPAR expr (ELSE expr)? #ifStat
    | WHILE LPAR body? expr RPAR expr	    	#whileStat
    | READ LPAR ID (COMMA ID)* RPAR 			#readStat 
    | PRINT LPAR expr (COMMA expr)* RPAR   		#printStat
    ;

/** Prefix operator. */
prfOp: MINUS | NOT;

/** Multiplicative operator. */
multOp: STAR | SLASH;

/** Additive operator. */
plusOp: PLUS | MINUS;

/** Boolean operator. */
boolOp: AND | OR;

/** Comparison operator. */
compOp: LE | LT | GE | GT | EQ | NE;

/** Data type. */
type: INT  					#intType
    | BOOL  				#boolType
    | CHAR 					#charType
    | VOID					#voidType
    | type LBLOCK RBLOCK 	#arrayType
    ;
    
FNCTYPE: '~~';
PROGRAM: 'program';   
    
TRUE: 'true';
FALSE: 'false';

READ: 'read';
PRINT: 'print';

IF: 'if';
ELSE: 'else';
WHILE: 'while';
RETURN: 'return';
    
CONST:	'const';
VOID: 	'void';
INT:	'int';
BOOL:	'bool';
CHAR:	'char';


ASS:    '<-';
AND:	'&&';
COLON:  ':';
COMMA:  ',';
DOT:    '.';
DQUOTE: '"';
EQ:     '=';
GE:     '>=';
GT:     '>';
LE:     '<=';
LBRACE: '{';
LPAR:   '(';
LBLOCK:	'[';
LT:     '<';
MINUS:  '-';
NE:     '!=';
NOT:	'!';
PLUS:   '+';
RBLOCK:	']';
RBRACE: '}';
RPAR:   ')';
SEMI:   ';';
SLASH:  '/';
REF:	'@';
STAR:   '*';
QUOTE:	'\'';
OR:		'||';
HT:		'#';

// Content-bearing token types
ID: LETTER (LETTER | DIGIT | '_')*;
NUM: DIGIT (DIGIT)*;
STR: DQUOTE .*? DQUOTE;
CHR: QUOTE .? QUOTE;

fragment LETTER: [a-zA-Z];
fragment DIGIT: [0-9];

// Skipped token types
COMMENT: HT .*? '\n'  -> skip;
MULTICOMMENT: HT HT .*? HT HT -> skip;
WS: [ \t\r\n]+ -> skip;