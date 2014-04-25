import java.io.*;

public class AsdrSample {

  private static final int BASE_TOKEN_NUM = 301;
  
  public static final int LOCATIONS = 301;
  public static final int REGIONS   = 302;
  public static final int RECT      = 303;
  public static final int CIRCLE    = 304;
  public static final int END       = 305;
  public static final int IDENT	    = 306;
  public static final int COORD     = 307;

  public static final String tokenList[] = {"LOCATIONS",
                                            "REGIONS",
                                            "RECT",
                                            "CIRCLE",
                                            "END",
                                            "IDENT",
                                            "COORD" };
                                      
  /* referencia ao objeto Scanner gerado pelo JFLEX */
  private Yylex lexer;

  public ParserVal yylval;

  private static int laToken;
  private boolean debug = true;
  
  /* construtor da classe */
  public AsdrSample (Reader r) {
      lexer = new Yylex (r, this);
  }

  private void G() {
    if (debug) System.out.println("G --> Locations Regions END");
    Locations();
    Regions();
    check(END);
  }

  private void Locations() {
	  if (debug) System.out.println("Locations --> LOCATIONS LocationList");
	  check(LOCATIONS);
    LocationList();
  }

  private void LocationList()
  {
    if (laToken == IDENT)
    {
    if (debug) System.out.println("LocationList --> IDENT COORD COORD LocationList");
      check(IDENT);
      check(COORD);
      check(COORD);
      LocationList();
    }
    else
    {
      if (debug) System.out.println("LocationList --> vazio");
    }
  }

  private void Regions()
  {
    if (debug) System.out.println("Regions --> REGIONS RegionList");
    check(REGIONS);
    RegionList();
  }

  private void RegionList()
  {
    if (laToken == RECT)
    {
      if (debug) System.out.println("RegionList --> RECT RectList RegionList");
      check(RECT);
      RectList();
      RegionList();
    }
    else if (laToken == CIRCLE)
    {
      if (debug) System.out.println("RegionList --> CIRCLE CircleList RegionList");
      check(CIRCLE);
      CircleList();
      RegionList();
    }
    else
    {
      if (debug) System.out.println("RegionList --> vazio");
    }
  }

  private void RectList()
  {
    if (laToken == IDENT)
    {
      if (debug) System.out.println("RectList --> IDENT COORD COORD COORD COORD RectList");
      check(IDENT);
      check(COORD);
      check(COORD);
      check(COORD);
      check(COORD);
      RectList();
    }
    else
    {
      if (debug) System.out.println("RectList --> vazio");
    }
  }

  private void CircleList()
  {
    if (laToken == IDENT)
    {
      if (debug) System.out.println("CircleList --> IDENT COORD COORD COORD CircleList");
      check(IDENT);
      check(COORD);
      check(COORD);
      check(COORD);
      CircleList();
    }
    else
    {
      if (debug) System.out.println("CircleList --> vazio");
    }
  }

  private void check(int expected) {
      if (laToken == expected)
         laToken = this.yylex();
      else {
         String expStr, laStr;       

		expStr = ((expected < BASE_TOKEN_NUM )
                ? ""+(char)expected
			     : tokenList[expected-BASE_TOKEN_NUM]);
         
		laStr = ((laToken < BASE_TOKEN_NUM )
                ? new Character((char)laToken).toString()
                : tokenList[laToken-BASE_TOKEN_NUM]);

          yyerror( "esperado token '" + expStr + "' mas encontrado '" + laStr + "'");
     }
   }

   /* metodo de acesso ao Scanner gerado pelo JFLEX */
   private int yylex() {
       int retVal = -1;
       try {
           yylval = new ParserVal(0); //zera o valor do token
           retVal = lexer.yylex(); //le a entrada do arquivo e retorna um token
       } catch (IOException e) {
           System.err.println("IO Error:" + e);
          }
       return retVal; //retorna o token para o Parser 
   }

  /* metodo de manipulacao de erros de sintaxe */
  public void yyerror (String error) {
     System.err.println("Erro: " + error);
     System.err.println("Entrada rejeitada");
     System.out.println("\n\nFalhou!!!");
     System.exit(1);
     
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
     AsdrSample parser = null;
     try {
         if (args.length == 0)
            parser = new AsdrSample(new InputStreamReader(System.in));
         else 
            parser = new  AsdrSample( new java.io.FileReader(args[0]));

          laToken = parser.yylex();          

          parser.G();
     
          if (laToken== Yylex.YYEOF)
             System.out.println("\n\nSucesso!");
          else     
             System.out.println("\n\nFalhou - esperado EOF.");               

        }
        catch (java.io.FileNotFoundException e) {
          System.out.println("File not found : \""+args[0]+"\"");
        }
//        catch (java.io.IOException e) {
//          System.out.println("IO error scanning file \""+args[0]+"\"");
//          System.out.println(e);
//        }
//        catch (Exception e) {
//          System.out.println("Unexpected exception:");
//          e.printStackTrace();
//      }
    
  }
  
}

