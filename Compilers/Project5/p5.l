%{
#include <stdio.h>
#include "p5.tab.h"
extern int yylval;
%}
Digit 		[0 - 9]
%%
RENAME		return RENAME;
AS			return AS;
\(			return LP;
\)			return RP;
WHERE		return WHERE;
\[			return LB;
\]			return RB;
\,			return COM;
UNION 		return UNION;
INTERSECT 	return INTERSECT;
MINUS 		return MINUS;
TIMES		return TIMES; 
JOIN		return JOIN; 
DIVIDEBY	return DIVIDEBY;
\<			return L;
\>			return G;
\<=			return LE;
\>= 		return GE;
\=			return E;
\<>			return LG;
Digit		return NUMBER;
CNO			return CNO;
CITY		return CITY;
CNAME		return CNAME;
SNO			return SNO;
PNO			return PNO;
TQTY		return TQTY; 
SNAME 		return SNAME;
QUOTA		return QUOTA;
PNAME 		return PNAME;
COST		return COST;
AVQTY		return AVQTY;
S# 			return SNUM;
STATUS 		return STATUS;
P# 			return PNUM;
COLOR 		return COLOR;
WEIGHT 		return WEIGHT;
QTY			return QTY;
S			return S;
P 			return P;
SP 			return SP;
PRDCT 		return PRDCT;
CUST 		return CUST;
ORDERS		return ORDERS;

\n            return 0;   /* logical EOF */
%%
