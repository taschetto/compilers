/*
 * Copyright 2014 (c)
 *   12180247-4 Guilherme Taschetto (gtaschetto@gmail.com)
 *   07104168-5 Fernando Delazeri (panchos3t@gmail.com)
 */

package asdrclass;
import java.util.Map;

%%

/*
  Options and Declarations
*/

%integer
%line
%char

%{
  private int comment_count = 0;
  private AsdrClass yyparser;
  private Map<String, Integer> identMap;

  public Yylex(java.io.Reader r, AsdrClass yyparser, Map<String, Integer> identMap) {
    this(r);
    this.yyparser = yyparser;
    this.identMap = identMap;
  }

  private void RegisterIdentifier()
  {
    String ident = yytext();
    if (this.identMap.get(ident) == null)
    {
      this.identMap.put(ident, yyline);
    }
  }
%}

Identifier=[:jletter:][:jletterdigit:]*
WHITE_SPACE_CHAR=[\n\r\ \t\b\012]

%%

/*
  Lexical Rules
*/

<YYINITIAL>
{
  "public"      { return AsdrClass.PUBLIC; }
  "private"     { return AsdrClass.PRIVATE; }
  "class"       { return AsdrClass.CLASS; }
  "int"         { return AsdrClass.INT; }
  "double"      { return AsdrClass.DOUBLE; }
  "String"      { return AsdrClass.STRING; }
  "void"        { return AsdrClass.VOID; }
  "this"        { return AsdrClass.THIS; }
  "for"         { return AsdrClass.FOR; }
  "return"      { return AsdrClass.RETURN; }
  "if"          { return AsdrClass.IF; }
  "break"       { return AsdrClass.BREAK; }
  "{"           { return AsdrClass.LBRACE; }
  "}"           { return AsdrClass.RBRACE; }
  ";"           { return AsdrClass.SEMICOLON; }
  "("           { return AsdrClass.LPAR; }
  ")"           { return AsdrClass.RPAR; }
  ","           { return AsdrClass.COLON; }
  "."           { return AsdrClass.DOT; }
  "="           { return AsdrClass.ASSIGN; }
  "=="          { return AsdrClass.EQ; }
  "+"           { return AsdrClass.PLUS; }
  ">"           { return AsdrClass.GREATER; }
  "*"           { return AsdrClass.TIMES; }
  {Identifier}  {
                  this.RegisterIdentifier();
                  return AsdrClass.IDENT;
                }
  [0-9]+        { return AsdrClass.NUM; }

  {WHITE_SPACE_CHAR}+ { }
  "//".*        { }
  .             { System.out.println("Lexical error - invalid character: <" + yytext() + ">"); }
}
