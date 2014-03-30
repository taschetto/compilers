/*
  User Code
*/

package asdrclass;

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

  public Yylex(java.io.Reader r, AsdrClass yyparser) {
    this(r);
    this.yyparser = yyparser;
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

  {Identifier}  { return AsdrClass.IDENT; }

  "{"           { return AsdrClass.OPENBRACE; }
  "}"           { return AsdrClass.CLOSEBRACE; }
  ";"           { return AsdrClass.SEMICOLON; }
  "("           { return AsdrClass.OPENPAR; }
  ")"           { return AsdrClass.CLOSEPAR; }
  ","           { return AsdrClass.COLON; }
  "."           { return AsdrClass.DOT; }
  "="           { return AsdrClass.EQ; }
  "+"           { return AsdrClass.PLUS; }
  "-"           { return AsdrClass.MINUS; }
  "*"           { return AsdrClass.TIMES; }
  "/"           { return AsdrClass.DIVIDED; }
  ">"           { return AsdrClass.GREATER; }
  "<"           { return AsdrClass.SMALLER; }

  {WHITE_SPACE_CHAR}+ { }
  .             { System.out.println("Lexical error - invalid character: <" + yytext() + ">"); }
}
