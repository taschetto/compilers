	
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

prog : { currEscopo = ""; currClass = ClasseID.VarGlobal; } dList main ;

dList : decl dList | ;

decl : declStruct 

      | type IDENT ';' {  TS_entry nodo = ts.pesquisa($2);
    	                    if (nodo != null) 
                              yyerror("(sem) variavel >" + $2 + "< jah declarada");
                          else ts.insert(new TS_entry($2, (TS_entry)$1, currEscopo, currClass)); 
                        }
      | type '[' NUM ']' IDENT  ';' 
                    {  TS_entry nodo = ts.pesquisa($5);
    	                  if (nodo != null) 
                            yyerror("(sem) variavel >" + $5 + "< jah declarada");
                    else ts.insert(new TS_entry($5, Tp_ARRAY, $3, (TS_entry)$1, currEscopo, currClass)); 
                     }
      ;
              
declStruct : STRUCT IDENT  {  TS_entry nodo = ts.pesquisa($2);
    	                      if (nodo != null) 
                                 yyerror("(sem) variavel >" + $2 + "< jah declarada");
                               else ts.insert(new TS_entry($2, Tp_STRUCT, currEscopo, ClasseID.NomeStruct));
 currEscopo = $2; currClass = ClasseID.CampoStruct; }
               
               '{' dList '}'
                { currEscopo = ""; currClass = ClasseID.VarGlobal; }
                ';'
             ;
              //
              // faria mais sentido reconhecer todos os tipos como ident! 
              // 
type : INT    { $$ = Tp_INT; }
     | FLOAT  { $$ = Tp_FLOAT; }
     | BOOL   { $$ = Tp_BOOL; }
	   | STRING { $$ = Tp_STRING; }
     | IDENT  { TS_entry nodo = ts.pesquisa($1);
    	                if (nodo == null ) 
                           yyerror("(sem) Nome de tipo <" + $1 + "> nao declarado ");
                        else 
                            $$ = nodo;
                     } 
     ;



main :  VOID MAIN '(' ')' bloco ;

bloco : '{' listacmd '}';

listacmd : listacmd cmd
		|
 		;

cmd :  exp ';' 
      | IF '(' exp ')' cmd 
                 {if ( ((TS_entry)$3).getTipo() != Tp_BOOL.getTipo()) 
                           yyerror("(sem) expressão (if) deve ser lógica "+((TS_entry)$3).getTipo());
                  }     
       ;


exp : exp '+' exp { $$ = validaTipo('+', (TS_entry)$1, (TS_entry)$3); }
   	| exp '>' exp { $$ = validaTipo('>', (TS_entry)$1, (TS_entry)$3);}
 	  | exp AND exp { $$ = validaTipo(AND, (TS_entry)$1, (TS_entry)$3); } 
    | NUM         { $$ = Tp_INT; }      
    | '(' exp ')' { $$ = $2; }
    | LITERAL     { $$ = Tp_STRING; }      
    | IDENT       { TS_entry nodo = ts.pesquisa($1);
    	                 if (nodo == null) 
	                        yyerror("(sem) var <" + $1 + "> nao declarada");                
                      else
			                    $$ = nodo.getTipo();
			            }   
    | IDENT '[' exp ']' 
                 { TS_entry nodo = ts.pesquisa($1);
    	             if (nodo == null) 
	                     yyerror("(sem) var <" + $1 + "> nao declarada");                
                   else
                       $$ = validaTipo('[', nodo, (TS_entry)$3);
						     }
     | exp '=' exp  
                 {  $$ = validaTipo(ATRIB, (TS_entry)$1, (TS_entry)$3);                      
                 } 
    ;

%%

  private Yylex lexer;

  private TabSimb ts;

  public static TS_entry Tp_INT =  new TS_entry("int", null, "", ClasseID.TipoBase);
  public static TS_entry Tp_FLOAT = new TS_entry("float", null, "", ClasseID.TipoBase);
  public static TS_entry Tp_BOOL = new TS_entry("bool", null, "", ClasseID.TipoBase);
  public static TS_entry Tp_STRING = new TS_entry("string", null, "", ClasseID.TipoBase);
  public static TS_entry Tp_ARRAY = new TS_entry("array", null, "", ClasseID.TipoBase);
  public static TS_entry Tp_STRUCT = new TS_entry("struct", null, "", ClasseID.TipoBase);
  public static TS_entry Tp_ERRO = new TS_entry("_erro_", null, "", ClasseID.TipoBase);

  public static final int ARRAY = 1500;
  public static final int ATRIB = 1600;

  private String currEscopo;
  private ClasseID currClass;

  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new ParserVal(0);
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }
    return yyl_return;
  }


  public void yyerror (String error) {
    System.err.println ("Erro (linha: "+ lexer.getLine() + ")\tMensagem: "+error);
  }


  public Parser(Reader r) {
    lexer = new Yylex(r, this);

    ts = new TabSimb();

    //
    // não me parece que necessitem estar na TS
    // já que criei todas como public static...
    //
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

  public void listarTS() { ts.listar();}

  public static void main(String args[]) throws IOException {
    System.out.println("\n\nVerificador semantico simples\n");
    

    Parser yyparser;
    if ( args.length > 0 ) {
      // parse a file
      yyparser = new Parser(new FileReader(args[0]));
    }
    else {
      // interactive mode
      System.out.println("[Quit with CTRL-D]");
      System.out.print("Programa de entrada:\n");
	    yyparser = new Parser(new InputStreamReader(System.in));
    }

    yyparser.yyparse();

  	yyparser.listarTS();

	  System.out.print("\n\nFeito!\n");
    
  }


   TS_entry validaTipo(int operador, TS_entry A, TS_entry B) {
       
         switch ( operador ) {
              case ATRIB:
                    if ( (A == Tp_INT && B == Tp_INT)                        ||
                         ((A == Tp_FLOAT && (B == Tp_INT || B == Tp_FLOAT))) ||
                         (A ==Tp_STRING)                                     ||
                         (A == B) )
                         return A;
                     else
                         yyerror("(sem) tipos incomp. para atribuicao: "+ A.getTipoStr() + " = "+B.getTipoStr());
                    break;


              case '+' :
                    if ( A == Tp_INT && B == Tp_INT)
                          return Tp_INT;
                    else if ( (A == Tp_FLOAT && (B == Tp_INT || B == Tp_FLOAT)) ||
				              		      (B == Tp_FLOAT && (A == Tp_INT || A == Tp_FLOAT)) ) 
                         return Tp_FLOAT;
                    else if (A==Tp_STRING || B==Tp_STRING)
                        return Tp_STRING;
                    else
                        yyerror("(sem) tipos incomp. para soma: "+ A.getTipoStr() + " + "+B.getTipoStr());
                    break;
             case '>' :
   	              if ((A == Tp_INT || A == Tp_FLOAT) && (B == Tp_INT || B == Tp_FLOAT))
                         return Tp_BOOL;
					        else
                        yyerror("(sem) tipos incomp. para op relacional: "+ A.getTipoStr() + " > "+B.getTipoStr());
			            break;
             case AND:
 	                if (A == Tp_BOOL && B == Tp_BOOL)
                         return Tp_BOOL;
					       else
                        yyerror("(sem) tipos incomp. para op lógica: "+ A.getTipoStr() + " && "+B.getTipoStr());
                 break;
             case '[':
                  if (B != Tp_INT)
                    	yyerror("(sem) expressão indexadora deve ser inteira: " + B.getTipoStr());                
                  else if (A.getTipo() != Tp_ARRAY)
	                          yyerror("(sem) var <" + A.getTipoStr() + "> nao é do tipo array");                
								  else 
									   return A.getTipoBase();
                  break;
						}
            return Tp_ERRO;
				}

/*
  | IDENT '=' exp  
                 { TS_entry nodo = ts.pesquisa($1);
    	             if (nodo == null) 
                       yyerror("(sem) variavel >" + $1 + "< nao declarada");
                   else 
                       $$ = validaTipo(ATRIB, nodo.getTipo(), (TS_entry)$3);                      
                   } 
*/


