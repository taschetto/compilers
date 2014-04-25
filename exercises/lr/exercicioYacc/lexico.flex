%%

%byaccj

%{
  private Parser yyparser;

  public Yylex(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }
%}

NL  = \n | \r | \r\n

%%

[:jletter:][:jletterdigit:]* { return Parser.IDENT; }
[0-9]+(.[0-9]+)?  { return Parser.NUM; }

"do"      { return Parser.DO; }
"to"      { return Parser.TO; }
"by"      { return Parser.BY; }
"if"      { return Parser.IF; }
"else"    { return Parser.ELSE; }
"endif"   { return Parser.ENDIF; }
"*"       { return Parser.TIMES; }
"+"       { return Parser.PLUS; }
"("       { return Parser.LPAR; }
")"       { return Parser.RPAR; }
"="       { return Parser.EQ; }

[ \t]+    { }
{NL}+     { } 

.    { System.err.println("Error: unexpected character '"+yytext()+"' na linha "+yyline); return YYEOF; }
