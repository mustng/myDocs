p5: p5.tab.c lex.yy.c
	cc -o p5 p5.tab.c lex.yy.c 

p5.tab.c: p5.y
	bison -d p5.y

lex.yy.c: p5.l
	lex p5.l
