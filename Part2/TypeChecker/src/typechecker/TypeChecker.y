%{
  import java.io.*;
%}

%token IDENT, INT, FLOAT, BOOL, NUM, STRING
%token LITERAL, AND, VOID, MAIN, IF
%token STRUCT

%right '='
%nonassoc '>'
%left '+'
%left AND
%left '['

%type <sval> IDENT
%type <ival> NUM
%type <obj> type
%type <obj> exp

%%

prog  : { currScope = ""; currClass = SymbolClass.GlobalVar; } dList main ;

dList : decl dList | ;

decl  : declStruct 
      | type IDENT ';' { Symbol symbol = ts.search($2);
                         if (symbol != null) 
                           yyerror("(sem) variavel >" + $2 + "< jah declarada");
                         else
                           ts.insert(new Symbol($2, (Symbol)$1, currScope, currClass)); }
      | type '[' NUM ']' IDENT  ';' { Symbol symbol = ts.search($5);
                                      if (symbol != null)
                                        yyerror("(sem) variavel >" + $5 + "< jah declarada");
                                      else
                                        ts.insert(new Symbol($5, Tp_ARRAY, $3, (Symbol)$1, currScope, currClass)); }
      ;
              
declStruct : STRUCT IDENT { Symbol symbol = ts.search($2);
                            if (symbol != null)
                              yyerror("(sem) variavel >" + $2 + "< jah declarada");
                            else
                              ts.insert(new Symbol($2, Tp_STRUCT, currScope, SymbolClass.StructName));
                             currScope = $2; currClass = SymbolClass.StructField; }
               
           '{' dList '}'  { currScope = ""; currClass = SymbolClass.GlobalVar; }
           ';'
           ;

//
// faria mais sentido reconhecer todos os tipos como ident! 
// 

type : INT    { $$ = Tp_INT; }
     | FLOAT  { $$ = Tp_FLOAT; }
     | BOOL   { $$ = Tp_BOOL; }
	   | STRING { $$ = Tp_STRING; }
     | IDENT  { Symbol symbol = ts.search($1);
                if (symbol == null ) 
                  yyerror("(sem) Nome de tipo <" + $1 + "> nao declarado ");
                else 
                  $$ = symbol; } 
     ;

main :  VOID MAIN '(' ')' bloco ;

bloco : '{' listacmd '}' ;

listacmd : listacmd cmd | ;

cmd :  exp ';' 
    | IF '(' exp ')' cmd  { if (((Symbol)$3).getType() != Tp_BOOL.getType())
                              yyerror("(sem) expressão (if) deve ser lógica "+((Symbol)$3).getType()); }
    ;


exp : exp '+' exp { $$ = validaTipo('+', (Symbol)$1, (Symbol)$3); }
   	| exp '>' exp { $$ = validaTipo('>', (Symbol)$1, (Symbol)$3);}
 	  | exp AND exp { $$ = validaTipo(AND, (Symbol)$1, (Symbol)$3); } 
    | NUM         { $$ = Tp_INT; }      
    | '(' exp ')' { $$ = $2; }
    | LITERAL     { $$ = Tp_STRING; }      
    | IDENT       { Symbol symbol = ts.search($1);
                    if (symbol == null) 
	                    yyerror("(sem) var <" + $1 + "> nao declarada");                
                    else
			                $$ = symbol.getType();  }   
    | IDENT '[' exp ']' 
                 { Symbol symbol = ts.search($1);
    	             if (symbol == null) 
	                     yyerror("(sem) var <" + $1 + "> nao declarada");                
                   else
                       $$ = validaTipo('[', symbol, (Symbol)$3);
						     }
    | exp '=' exp { $$ = validaTipo(ATRIB, (Symbol)$1, (Symbol)$3); } 
    ;

%%

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
                 { Symbol symbol = ts.search($1);
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

