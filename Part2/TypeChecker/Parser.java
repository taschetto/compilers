//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "TypeChecker.y"
  import java.io.*;
//#line 19 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short IDENT=257;
public final static short INT=258;
public final static short FLOAT=259;
public final static short BOOL=260;
public final static short NUM=261;
public final static short STRING=262;
public final static short LITERAL=263;
public final static short AND=264;
public final static short VOID=265;
public final static short MAIN=266;
public final static short IF=267;
public final static short STRUCT=268;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    4,    0,    3,    3,    6,    6,    6,    8,    9,    7,
    1,    1,    1,    1,    1,    5,   10,   11,   11,   12,
   12,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,
};
final static short yylen[] = {                            2,
    0,    3,    2,    0,    1,    3,    6,    0,    0,    8,
    1,    1,    1,    1,    1,    5,    3,    2,    0,    2,
    5,    3,    3,    3,    1,    3,    1,    1,    4,    3,
    3,
};
final static short yydefred[] = {                         1,
    0,    0,   15,   11,   12,   13,   14,    0,    0,    0,
    0,    5,    8,    0,    0,    0,    2,    3,    0,    6,
    0,    0,    0,    0,    0,    0,    0,    0,    9,    7,
   19,   16,    0,    0,   10,    0,   25,   27,    0,   17,
    0,    0,   18,    0,    0,    0,    0,    0,    0,    0,
    0,   20,    0,    0,   26,    0,    0,    0,    0,   31,
   29,    0,   21,
};
final static short yydgoto[] = {                          1,
    9,   42,   10,    2,   17,   11,   12,   19,   33,   32,
   34,   43,
};
final static short yysindex[] = {                         0,
    0, -189,    0,    0,    0,    0,    0, -246,  -87, -252,
 -189,    0,    0,  -41, -241, -244,    0,    0,  -84,    0,
  -46,    9, -189, -203,   17,  -65,    3,  -51,    0,    0,
    0,    0,   15,  -40,    0,  -16,    0,    0,   36,    0,
  -23,  -17,    0,  -23,  -23,  -31,  -23,  -23,  -23,  -23,
 -180,    0,  -10,  -27,    0,   32,   -5,   -6,  -44,    0,
    0,  -39,    0,
};
final static short yyrindex[] = {                         0,
    0, -185,    0,    0,    0,    0,    0,    0,    0,    0,
 -119,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -43,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -38,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -34,   -9,    2,    5,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   52,   42,    0,    0,    0,    0,    0,    0,    0,
    0,   19,
};
final static int YYTABLESIZE=259;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         41,
   41,   51,   28,   15,   28,    4,   24,   28,   24,   55,
   13,   50,   16,   62,   51,   50,   41,   20,   51,   21,
   28,   22,   28,   28,   24,   50,   24,   24,   51,   48,
   49,   30,   50,   48,   49,   51,   50,   50,   23,   51,
   51,   52,   23,   48,   49,   22,   24,   22,   25,   30,
   48,   49,   18,   27,   28,   48,   49,   28,   24,   29,
   23,   30,   23,   22,   26,   22,   22,    3,    4,    5,
    6,   31,    7,   35,   44,   45,   60,   51,    8,    4,
   63,    4,   61,   30,   40,    0,    0,    0,    0,    0,
    0,    0,   46,    0,   23,   53,   54,   22,   56,   57,
   58,   59,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    4,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   14,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   36,   36,    0,   47,
   37,   37,   38,   38,    0,   28,   39,   39,    0,   24,
    0,    0,   47,   36,    0,    0,   47,   37,    0,   38,
    0,    0,    0,    0,    0,    0,   47,    0,    0,    0,
    0,    0,    0,   47,    0,    0,    0,   47,   47,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   40,   46,   41,   91,   43,  125,   41,   46,   43,   41,
  257,   43,  265,   41,   46,   43,   40,   59,   46,  261,
   59,  266,   61,   62,   59,   43,   61,   62,   46,   61,
   62,   41,   43,   61,   62,   46,   43,   43,  123,   46,
   46,   59,   41,   61,   62,   41,   93,   43,   40,   59,
   61,   62,   11,  257,   93,   61,   62,   41,   93,  125,
   59,   59,   61,   59,   23,   61,   62,  257,  258,  259,
  260,  123,  262,   59,   91,   40,  257,   46,  268,  265,
   62,  125,   93,   93,  125,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   41,   -1,   93,   44,   45,   93,   47,   48,
   49,   50,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  265,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  257,   -1,  264,
  261,  261,  263,  263,   -1,  264,  267,  267,   -1,  264,
   -1,   -1,  264,  257,   -1,   -1,  264,  261,   -1,  263,
   -1,   -1,   -1,   -1,   -1,   -1,  264,   -1,   -1,   -1,
   -1,   -1,   -1,  264,   -1,   -1,   -1,  264,  264,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=268;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'",null,"'+'",null,
null,"'.'",null,null,null,null,null,null,null,null,null,null,null,null,"';'",
null,"'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"IDENT","INT","FLOAT","BOOL","NUM",
"STRING","LITERAL","AND","VOID","MAIN","IF","STRUCT",
};
final static String yyrule[] = {
"$accept : prog",
"$$1 :",
"prog : $$1 dList main",
"dList : decl dList",
"dList :",
"decl : declStruct",
"decl : type IDENT ';'",
"decl : type '[' NUM ']' IDENT ';'",
"$$2 :",
"$$3 :",
"declStruct : STRUCT IDENT $$2 '{' dList '}' $$3 ';'",
"type : INT",
"type : FLOAT",
"type : BOOL",
"type : STRING",
"type : IDENT",
"main : VOID MAIN '(' ')' bloco",
"bloco : '{' listacmd '}'",
"listacmd : listacmd cmd",
"listacmd :",
"cmd : exp ';'",
"cmd : IF '(' exp ')' cmd",
"exp : exp '+' exp",
"exp : exp '>' exp",
"exp : exp AND exp",
"exp : NUM",
"exp : '(' exp ')'",
"exp : LITERAL",
"exp : IDENT",
"exp : IDENT '[' exp ']'",
"exp : exp '=' exp",
"exp : exp '.' IDENT",
};

//#line 115 "TypeChecker.y"

  private Yylex lexer;
  private SymbolTable ts;

  public static Symbol Tp_INT =  new Symbol("int", null, "", SymbolClass.BaseType);
  public static Symbol Tp_FLOAT = new Symbol("float", null, "", SymbolClass.BaseType);
  public static Symbol Tp_BOOL = new Symbol("bool", null, "", SymbolClass.BaseType);
  public static Symbol Tp_STRING = new Symbol("string", null, "", SymbolClass.BaseType);
  public static Symbol Tp_ARRAY = new Symbol("array", null, "", SymbolClass.BaseType);
  public static Symbol Tp_STRUCT = new Symbol("struct", null, "", SymbolClass.BaseType);
  public static Symbol Tp_ERRO = new Symbol("_erro_", null, "", SymbolClass.BaseType);

  public static final int ARRAY = 1500;
  public static final int ATRIB = 1600;

  private String currScope;
  private SymbolClass currClass;

  private int yylex () {
    int yyretval = -1;
    try {
      yylval = new ParserVal(0);
      yyretval = lexer.yylex();
    } catch (IOException ex) {
      System.out.println("IO error: " + ex);
    }
    return yyretval;
  }

  public void yyerror (String error)
  {
    System.err.println ("Erro (linha: "+ lexer.getLine() + ")\tMensagem: "+error);
  }


  public Parser(Reader r) {
    lexer = new Yylex(r, this);
    ts = new SymbolTable();
    ts.insert(Tp_ERRO);
    ts.insert(Tp_INT);
    ts.insert(Tp_FLOAT);
    ts.insert(Tp_BOOL);
    ts.insert(Tp_STRING);
    ts.insert(Tp_ARRAY);
    ts.insert(Tp_STRUCT);
  }  

  public void setDebug(boolean debug) {
    yydebug = debug;
  }

  public void printTable() { ts.print();}

  public static void main(String args[]) throws IOException {
    System.out.println("\n\nVerificador semantico simples\n");
    
    Parser yyparser;
    if ( args.length > 0 ) {
      yyparser = new Parser(new FileReader(args[0]));
    }
    else {
      System.out.println("[Quit with CTRL-D]");
      System.out.print("Programa de entrada:\n");
	    yyparser = new Parser(new InputStreamReader(System.in));
    }

    yyparser.yyparse();
  	yyparser.printTable();
    System.out.print("\n\nFeito!\n");
  }

  Symbol validaTipo(int operator, Symbol A, Symbol B)
  {
    switch (operator)
    {
      case ATRIB:
        if ((A == Tp_INT && B == Tp_INT)                       ||
           ((A == Tp_FLOAT && (B == Tp_INT || B == Tp_FLOAT))) ||
            (A == Tp_STRING)                                   ||
            (A == B))
          return A;
        else
          yyerror("(sem) tipos incomp. para atribuicao: "+ A.getTypeStr() + " = "+B.getTypeStr());
        break;

      case '+':
        if (A == Tp_INT && B == Tp_INT)
          return Tp_INT;
        else if ((A == Tp_FLOAT && (B == Tp_INT || B == Tp_FLOAT)) || (B == Tp_FLOAT && (A == Tp_INT || A == Tp_FLOAT)))
          return Tp_FLOAT;
        else if (A==Tp_STRING || B==Tp_STRING)
          return Tp_STRING;
        else
          yyerror("(sem) tipos incomp. para soma: "+ A.getTypeStr() + " + "+B.getTypeStr());
        break;

      case '>':
        if ((A == Tp_INT || A == Tp_FLOAT) && (B == Tp_INT || B == Tp_FLOAT))
          return Tp_BOOL;
        else
          yyerror("(sem) tipos incomp. para op relacional: "+ A.getTypeStr() + " > "+B.getTypeStr());
        break;
      
      case AND:
        if (A == Tp_BOOL && B == Tp_BOOL)
          return Tp_BOOL;
        else
          yyerror("(sem) tipos incomp. para op lógica: "+ A.getTypeStr() + " && "+B.getTypeStr());
        break;

      case '[':
        if (B != Tp_INT)
          yyerror("(sem) expressão indexadora deve ser inteira: " + B.getTypeStr());                
        else if (A.getType() != Tp_ARRAY)
          yyerror("(sem) var <" + A.getTypeStr() + "> nao é do tipo array");                
        else 
          return A.getBaseType();
        break;
    }
    return Tp_ERRO;
  }

/*
  | IDENT '=' exp  
                 { Symbol symbol = ts.search($1, currScope);
    	             if (symbol == null) 
                       yyerror("(sem) variavel >" + $1 + "< nao declarada");
                   else 
                       $$ = validaTipo(ATRIB, symbol.getType(), (Symbol)$3);                      
                   } 
*/




/*

ISTO VALE PRAS FUNÇÕES

Regra X: o yacc só executa ações semanticas quando faz uma redução

D -> type IDENT ';' ACTION1
   | type IDENT ACTION2 '(' lparam ')' bloco ACTION3

   ACTION1 = { insere TS }
   ACTION2 = { scope = $1 }
   ACTION3 = { ... }

ACTION2 deve ser colocada depois do '(' devido a regra X. No local original não funciona por que ainda não foi feita redução.

*/

//#line 423 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 23 "TypeChecker.y"
{ currScope = ""; currClass = SymbolClass.GlobalVar; }
break;
case 6:
//#line 28 "TypeChecker.y"
{ Symbol symbol = ts.search(val_peek(1).sval, currScope);
                         if (symbol != null) 
                           yyerror("(sem) variavel >" + val_peek(1).sval + "< jah declarada");
                         else
                           ts.insert(new Symbol(val_peek(1).sval, (Symbol)val_peek(2).obj, currScope, currClass)); }
break;
case 7:
//#line 33 "TypeChecker.y"
{ Symbol symbol = ts.search(val_peek(1).sval, currScope);
                                      if (symbol != null)
                                        yyerror("(sem) variavel >" + val_peek(1).sval + "< jah declarada");
                                      else
                                        ts.insert(new Symbol(val_peek(1).sval, Tp_ARRAY, val_peek(3).ival, (Symbol)val_peek(5).obj, currScope, currClass)); }
break;
case 8:
//#line 40 "TypeChecker.y"
{ Symbol symbol = ts.search(val_peek(0).sval, currScope);
                            if (symbol != null)
                              yyerror("(sem) variavel >" + val_peek(0).sval + "< jah declarada");
                            else
                              ts.insert(new Symbol(val_peek(0).sval, Tp_STRUCT, currScope, SymbolClass.StructName));
                             currScope = val_peek(0).sval; currClass = SymbolClass.StructField; }
break;
case 9:
//#line 47 "TypeChecker.y"
{ currScope = ""; currClass = SymbolClass.GlobalVar; }
break;
case 11:
//#line 55 "TypeChecker.y"
{ yyval.obj = Tp_INT; }
break;
case 12:
//#line 56 "TypeChecker.y"
{ yyval.obj = Tp_FLOAT; }
break;
case 13:
//#line 57 "TypeChecker.y"
{ yyval.obj = Tp_BOOL; }
break;
case 14:
//#line 58 "TypeChecker.y"
{ yyval.obj = Tp_STRING; }
break;
case 15:
//#line 59 "TypeChecker.y"
{ Symbol symbol = ts.search(val_peek(0).sval);
                if (symbol == null) 
                  yyerror("(sem) Nome de tipo <" + val_peek(0).sval + "> nao declarado no escopo <" + currScope  + ">");
                else 
                  yyval.obj = symbol; }
break;
case 21:
//#line 73 "TypeChecker.y"
{ if (((Symbol)val_peek(2).obj).getType() != Tp_BOOL.getType())
                              yyerror("(sem) expressão (if) deve ser lógica "+((Symbol)val_peek(2).obj).getType()); }
break;
case 22:
//#line 78 "TypeChecker.y"
{ yyval.obj = validaTipo('+', (Symbol)val_peek(2).obj, (Symbol)val_peek(0).obj); }
break;
case 23:
//#line 79 "TypeChecker.y"
{ yyval.obj = validaTipo('>', (Symbol)val_peek(2).obj, (Symbol)val_peek(0).obj);}
break;
case 24:
//#line 80 "TypeChecker.y"
{ yyval.obj = validaTipo(AND, (Symbol)val_peek(2).obj, (Symbol)val_peek(0).obj); }
break;
case 25:
//#line 81 "TypeChecker.y"
{ yyval.obj = Tp_INT; }
break;
case 26:
//#line 82 "TypeChecker.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 27:
//#line 83 "TypeChecker.y"
{ yyval.obj = Tp_STRING; }
break;
case 28:
//#line 84 "TypeChecker.y"
{ Symbol symbol = ts.search(val_peek(0).sval, currScope);
                    if (symbol == null) 
	                    yyerror("(sem) var <" + val_peek(0).sval + "> nao declarada");                
                    else
			                yyval.obj = symbol.getType();  }
break;
case 29:
//#line 90 "TypeChecker.y"
{ Symbol symbol = ts.search(val_peek(3).sval, currScope);
    	             if (symbol == null) 
	                     yyerror("(sem) var <" + val_peek(3).sval + "> nao declarada");                
                   else
                       yyval.obj = validaTipo('[', symbol, (Symbol)val_peek(1).obj);
						     }
break;
case 30:
//#line 96 "TypeChecker.y"
{ yyval.obj = validaTipo(ATRIB, (Symbol)val_peek(2).obj, (Symbol)val_peek(0).obj); }
break;
case 31:
//#line 97 "TypeChecker.y"
{
                      Symbol exp = (Symbol)val_peek(2).obj;
                      if (exp.getSymbolClass() == SymbolClass.StructName)
                      {
                        Symbol ident = ts.search(val_peek(0).sval, exp.getId());
                        if (ident == null)
                          yyerror("(sem) var <" + val_peek(0).sval  + "> nao declarada na struct " + exp.getId());
                        else
                        {
                          yyval.obj = ident.getType();
                        }
                      }
                      else
                        yyerror("(sem) var <" + exp.getId()  + "> não é um tipo struct.");
                    }
break;
//#line 697 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
