/*
/*
 * Copyright 2014 (c)
 *   12180247-4 Guilherme Taschetto (gtaschetto@gmail.com)
 *   07104168-5 Fernando Delazeri (panchos3t@gmail.com)
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
  public static final int LBRACE = 308;
  public static final int RBRACE = 309;
  public static final int SEMICOLON = 310;
  public static final int VOID = 311;
  public static final int LPAR = 312;
  public static final int RPAR = 313;
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
                                            "LBRACE",
                                            "RBRACE",
                                            "SEMICOLON",
                                            "VOID",
                                            "LPAR",
                                            "RPAR",
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
    check(LBRACE);
    Declarations();
    check(RBRACE);
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
    if (debug) System.out.println("Constructor -> public IDENT(ParameterList) Block");
    check(PUBLIC);
    check(IDENT);
    check(LPAR);
    ParameterList();
    check(RPAR);
    Block();
  }
  
  private void Block()
  {
    if (debug) System.out.println("Block -> { CommandList }");
    check(LBRACE);
    CommandList();
    check(RBRACE);
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
    if (debug) System.out.println("Method -> public void IDENT(ParameterList) Block");
    check(PUBLIC);
    check(VOID);
    check(IDENT);
    check(LPAR);
    ParameterList();
    check(RPAR);
    Block();
  }

  private void ParameterList()
  {
    if (laToken == RPAR)
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
    if (laToken == RBRACE)
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
      if (debug) System.out.println("Command -> for (;;) Block");
      check(FOR);
      check(LPAR);
      check(SEMICOLON);
      check(SEMICOLON);
      check(RPAR);
      Block();
    }
    else if (laToken == IF)
    {
      if (debug) System.out.println("Command -> if (Expression) Command");
      check(IF);
      check(LPAR);
      Expression();
      check(RPAR);
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
    else if (laToken == LBRACE)
    {
      if (debug) System.out.println("Command -> Block;");
      Block();
    }
    else
    {
      yyerror("Invalid command.");
    }
  }

  private void Expression()
  {
    if (debug) System.out.println("Expression -> SumAux Comp");
    SumAux();
    Comp();
  }

  private void Comp()
  {
    if (laToken == GREATER)
    {
      if (debug) System.out.println("Comp -> > SumAux Comp");
      check(GREATER);
      SumAux();
      Comp();
    }
    else if (laToken == EQ)
    {
      if (debug) System.out.println("Comp -> == SumAux Comp");
      check(EQ);
      SumAux();
      Comp();
    }
    else
    {
      if (debug) System.out.println("Comp -> empty");
    }
  }

  private void SumAux()
  {
    if (debug) System.out.println("SumAux -> MultAux Sum");
    MultAux();
    Sum();
  }

  private void Sum()
  {
    if (laToken == PLUS)
    {
      if (debug) System.out.println("Sum -> + MultAux Sum");
      check(PLUS);
      MultAux();
      Sum();
    }
    else
    {
      if (debug) System.out.println("Sum -> empty");
    }
  }

  private void MultAux()
  {
    if (debug) System.out.println("MultAux -> Value Mult");
    Value();
    Mult();
  }

  private void Value()
  {
    if (laToken == THIS)
    {
      if (debug) System.out.println("Value -> THIS.IDENT");
      check(THIS);
      check(DOT);
      check(IDENT);
    }
    else if (laToken == IDENT)
    {
      if (debug) System.out.println("Value -> IDENT");
      check(IDENT);
    }
    else if (laToken == NUM)
    {
      if (debug) System.out.println("Value -> NUM");
      check(NUM);
    }
    else
    {
      yyerror("Expected IDENT or NUM.");
    }
  }

  private void Mult()
  {
    if (laToken == TIMES)
    {
      if (debug) System.out.println("Mult -> * Value Mult");
      check(TIMES);
      Value();
      Mult();
    }
    else
    {
      if (debug) System.out.println("Mult -> empty");
    }
  }
}
