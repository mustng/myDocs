%start Program
%token RENAME AS LP RP WHERE LB RB COM UNION INTERSECT MINUS TIMES JOIN DIVIDEBY
%token L G LE GE E LG NUMBER CNO CITY CNAME SNO PNO TQTY SNAME QUOTA PNAME COST AVQTY SNUM
%token STATUS PNUM COLOR WEIGHT QTY S P SP PRDCT CUST ORDERS

%%
Program    : Expression         { 
                                    printf("ACCEPT\n");
                                    };
Expression : onerelationexpression      {
                                    };
           | tworelationexpression     {
                                    };
onerelationexpression       : renaming          {
                                    };
           | restriction          {
                                    };
           | projection                 {
                                    };
renaming      : term RENAME attribute AS attribute       {
                                    };
term  : relation       {
                                    };
           | LP Expression RP     {
                                    };
restriction      : term WHERE comparison      {
                                    };
projection   : term        {
                                    };
           | term LB attributecommalist RB     {
                                    };
attributecommalist  : attribute        {
                                    };
           | attribute COM attributecommalist     {
                                    };	
tworelationexpression      : projection binaryoperation Expression       {
                                    };	
binaryoperation       : UNION           {
                                    };
           | INTERSECT           {
                                    };
           | MINUS                  {
                                    };
           | TIMES            {
                                    };
           | JOIN                   {
                                    };
           | DIVIDEBY           {
                                    };
comparison      : attribute compare number       {
                                    };
compare       : L           {
                                    };
           | G           {
                                    };
           | LE                  {
                                    };
           | GE            {
                                    };
           | E                   {
                                    };
           | LG           {
                                    };	
number   : val         {
                                    };
           | val number    {
                                    };
val       : NUMBER       {
                                    };
attribute        : CNO            {
                                    };
           | CITY            {
                                    };
           | CNAME                   {
                                    };
           | SNO             {
                                    };
           | PNO                    {
                                    };
           | TQTY            {
                                    };	
           | SNAME              {
                                    };
           | QUOTA                     {
                                    };
           | COST              {
                                    };	
           | AVQTY                     {
                                    };
           | SNUM             {
                                    };	
           | STATUS               {
                                    };
           | PNUM                     {
                                    };
           | COLOR              {
                                    };
           | WEIGHT                      {
                                    };
           | QTY              {
                                    };
relation         : S            {
                                    };
           | P            {
                                    };
           | SP                    {
                                    };
           | PRDCT              {
                                    };
           | CUST                     {
                                    };
           | ORDERS            {
                                    };									
%%
main()
{
   yyparse();
}
yyerror()
{
   printf("REJECT\n");
   exit(0);
}
yywrap()
{
   exit(0);
}

