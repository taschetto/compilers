/*
 * Copyright 2014 (c) Guilherme Taschetto & Fernando Delazeri
 */

package asdrclass;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

public class AsdrClass {
  
  private static final int BASE_TOKEN_NUM = 301;
  
  public static final int PUBLIC = 301;
  public static final int PRIVATE = 302;
  public static final int CLASS = 303;
  public static final int INT = 304;
  public static final int DOUBLE = 305;
  public static final int STRING = 306;
  public static final int IDENT = 307;
  public static final int OPENBRACE = 308;
  public static final int CLOSEBRACE = 309;
  public static final int SEMICOLON = 310;
  public static final int VOID = 311;
  public static final int OPENPAR = 312;
  public static final int CLOSEPAR = 313;
  public static final int COLON = 314;
  public static final int THIS = 315;
  public static final int DOT = 316;
  public static final int ASSIGN = 317;
  public static final int EQ = 318;
  public static final int PLUS = 319;
  public static final int TIMES = 320;
  public static final int GREATER = 321;
  public static final int FOR = 322;
  public static final int RETURN = 323;
  public static final int IF = 324;
  public static final int BREAK = 325;
  public static final int NUM = 326;

  public static final String tokenList[] = {"PUBLIC",
                                            "PRIVATE",
                                            "CLASS",
                                            "INT",
                                            "DOUBLE",
                                            "STRING",
                                            "IDENT",
                                            "OPENBRACE",
                                            "CLOSEBRACE",
                                            "SEMICOLON",
                                            "VOID",
                                            "OPENPAR",
                                            "CLOSEPAR",
                                            "COLON",
                                            "THIS",
                                            "DOT",
                                            "ASSIGN",
                                            "EQ",
                                            "PLUS",
                                            "TIMES",
                                            "GREATER",
                                            "FOR",
                                            "RETURN",
                                            "IF",
                                            "BREAK",
                                            "NUM",
                                             };
  
  /* referencia ao objeto Scanner gerado pelo JFLEX */
  private Yylex lexer;
  private static int laToken;
  private boolean debug = true;
  
  public AsdrClass(Reader r, Map<String, Integer> identMap)
  {
    lexer = new Yylex (r, this, identMap);
  }
  
  /* metodo de acesso ao Scanner gerado pelo JFLEX */
  private int yylex() {
    int retVal = -1;
    try {
      retVal = lexer.yylex(); //le a entrada do arquivo e retorna um token
    } catch (IOException e) {
      System.err.println("IO Error:" + e);
    }
    return retVal; //retorna o token para o Parser 
   }

  /* metodo de manipulacao de erros de sintaxe */
  public void yyerror (String error) {
    System.err.println("Error: " + error);
    System.err.println("Invalid input!");
    System.out.println("### Syntax ERROR ###");               
    System.exit(1);
  }
  
  private void check(int expected) {
    if (laToken == expected)
      laToken = this.yylex();
    else {
      String expStr, laStr;

      expStr = ((expected < BASE_TOKEN_NUM ) ? ""+(char)expected : tokenList[expected-BASE_TOKEN_NUM]);
      laStr = ((laToken < BASE_TOKEN_NUM ) ? new Character((char)laToken).toString() : tokenList[laToken-BASE_TOKEN_NUM]);

      yyerror("Expected token '" + expStr + "' but found '" + laStr + "' instead.");
    }
  }
  
/**
   * Runs the scanner on input files.
   *
   * This main method is the debugging routine for the scanner.
   * It prints debugging information about each returned token to
   * System.out until the end of file is reached, or an error occured.
   *
   * @param args   the command line, contains the filenames to run
   *               the scanner on.
   */
  public static void main(String[] args) {
    AsdrClass parser = null;
    try {
      Map<String, Integer> identMap = new HashMap<String, Integer>();    

      if (args.length == 0)
      {
        parser = new AsdrClass(new InputStreamReader(System.in), identMap);
      }
      else
      {
        parser = new AsdrClass(new java.io.FileReader(args[0]), identMap);
      }

      laToken = parser.yylex();          
      parser.Class();

      if (laToken== Yylex.YYEOF)
      {
        System.out.println("\n*** Syntax OK  ***");
        System.out.println("\n*** Identifier Table ***\nIdentifier\tLine #");

        for (Entry<String, Integer> entry : identMap.entrySet())
        {
          String ident = entry.getKey();
          int line = entry.getValue();

          System.out.println(ident + "\t\t" + line);
        }
      }
      else     
      {
        System.out.println("### Syntax ERROR ###");               
      }
    }
    catch (java.io.IOException e) {
      System.out.println("IO error scanning file \"" + args[0] + "\"");
      System.out.println(e);
    }
    catch (Exception e) {
      System.out.println("Unexpected exception:");
      e.printStackTrace();
    }
  }

  private void Class()
  {
    if (debug) System.out.println("Class -> public class IDENT { Declarations }");
    check(PUBLIC);
    check(CLASS);
    check(IDENT);
    check(OPENBRACE);
    Declarations();
    check(CLOSEBRACE);
  }

  private void Declarations()
  {
    if (debug) System.out.println("Declarations -> AtributeList Constructor MethodList");
    AtributeList();
    Constructor();
    MethodList();
  }

  private void AtributeList()
  {
    if (laToken == PRIVATE)
    {
      if (debug) System.out.println("AtributeList -> Atribute AtributeList");
      Atribute();
      AtributeList();
    }
    else
    {
      if (debug) System.out.println("AtributeList -> empty");
    }
  }

  private void Atribute()
  {
    if (debug) System.out.println("Atribute -> private Type IDENT;");
    check(PRIVATE);
    Type();
    check(IDENT);
    check(SEMICOLON);
  }

  private void Type()
  {
    if (laToken == INT)
    {
      if (debug) System.out.println("Type -> INT");
      check(INT);
    }
    else if (laToken == DOUBLE)
    {
      if (debug) System.out.println("Type -> DOUBLE");
      check(DOUBLE);
    }
    else if (laToken == STRING)
    {
      if (debug) System.out.println("Type -> STRING");
      check(STRING);
    }
    else
    {
      yyerror("Expected INT, DOUBLE or STRING.");
    }
  }

  private void Constructor()
  {
    if (debug) System.out.println("Constructor -> public IDENT(ParameterList) { CommandList }");
    check(PUBLIC);
    check(IDENT);
    check(OPENPAR);
    ParameterList();
    check(CLOSEPAR);
    check(OPENBRACE);
    CommandList();
    check(CLOSEBRACE);
  }

  private void MethodList()
  {
    if (laToken == PUBLIC)
    {
      if (debug) System.out.println("MethodList -> Method MethodList");
      Method();
      MethodList();
    }
    else
    {
      if (debug) System.out.println("MethodList -> empty");
    }
  }

  private void Method()
  {
    if (debug) System.out.println("Method -> public void IDENT(ParameterList) { CommandList }");
    check(PUBLIC);
    check(VOID);
    check(IDENT);
    check(OPENPAR);
    ParameterList();
    check(CLOSEPAR);
    check(OPENBRACE);
    CommandList();
    check(CLOSEBRACE);
  }

  private void ParameterList()
  {
    if (laToken == CLOSEPAR)
    {
      if (debug) System.out.println("ParameterList -> empty");
    }
    else
    {
      if (debug) System.out.println("ParameterList -> Type IDENT Parameter");
      Type();
      check(IDENT);
      Parameter();    
    }
  }

  private void Parameter()
  {
    if (laToken == COLON)
    {
      if (debug) System.out.println("Parameter -> , Type IDENT Parameter");
      check(COLON);
      Type();
      check(IDENT);
      Parameter();
    }
    else
    {
      if (debug) System.out.println("Parameter -> empty");
    }
  }

  private void CommandList()
  {
    if (laToken == CLOSEBRACE)
    {
      if (debug) System.out.println("CommandList -> empty");
    }
    else
    {
      if (debug) System.out.println("CommandList -> Command CommandList");
      Command();
      CommandList();
    }
  }

  private void Command()
  {
    if (laToken == THIS)
    {
      if (debug) System.out.println("Command -> THIS.IDENT = Expression;");
      check(THIS);
      check(DOT);
      check(IDENT);
      check(ASSIGN);
      Expression();
      check(SEMICOLON);
    }
    else if (laToken == IDENT)
    {
      if (debug) System.out.println("Command -> IDENT = Expression;");
      check(IDENT);
      check(ASSIGN);
      Expression();
      check(SEMICOLON);
    }
    else if (laToken == FOR)
    {
      if (debug) System.out.println("Command -> for (;;) { CommandList }");
      check(FOR);
      check(OPENPAR);
      check(SEMICOLON);
      check(SEMICOLON);
      check(CLOSEPAR);
      check(OPENBRACE);
      CommandList();
      check(CLOSEBRACE);
    }
    else if (laToken == IF)
    {
      if (debug) System.out.println("Command -> if (Expression) Command");
      check(IF);
      check(OPENPAR);
      Expression();
      check(CLOSEPAR);
      Command();
    }
    else if (laToken == BREAK)
    {
      if (debug) System.out.println("Command -> break;");
      check(BREAK);
      check(SEMICOLON);      
    }
    else if (laToken == RETURN)
    {
      if (debug) System.out.println("Command -> return;");
      check(RETURN);
      check(SEMICOLON);
    }
    else
    {
      yyerror("Invalid command.");
    }
  }

  private void Expression()
  {
    if (debug) System.out.println("Expression -> T R");
    T();
    R();
  }

  private void R()
  {
    if (laToken == GREATER)
    {
      if (debug) System.out.println("R -> > T R");
      check(GREATER);
      T();
      R();
    }
    else if (laToken == EQ)
    {
      if (debug) System.out.println("R -> == T R");
      check(EQ);
      T();
      R();
    }
    else
    {
      if (debug) System.out.println("R -> empty");
    }
  }

  private void T()
  {
    if (debug) System.out.println("T -> F S");
    F();
    S();
  }

  private void S()
  {
    if (laToken == PLUS)
    {
      if (debug) System.out.println("S -> + F S");
      check(PLUS);
      F();
      S();
    }
    else
    {
      if (debug) System.out.println("S -> empty");
    }
  }

  private void F()
  {
    if (debug) System.out.println("F -> G U");
    G();
    U();
  }

  private void G()
  {
    if (laToken == IDENT)
    {
      if (debug) System.out.println("G -> IDENT");
      check(IDENT);
    }
    else if (laToken == NUM)
    {
      if (debug) System.out.println("G -> NUM");
      check(NUM);
    }
    else
    {
      yyerror("Expected IDENT or NUM.");
    }
  }

  private void U()
  {
    if (laToken == TIMES)
    {
      if (debug) System.out.println("U -> * G U");
      check(TIMES);
      G();
      U();
    }
    else
    {
      if (debug) System.out.println("U -> empty");
    }
  }
}
