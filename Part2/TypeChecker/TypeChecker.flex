/*
 * Copyright (c) 2014
 *
 * 12180247-4 Guilherme Taschetto (gtaschetto@gmail.com)
 * 07104168-5 Fernando Delazeri (panchos3t@gmail.com)
 * 11100166-5 Tomas Ceia Ramos de Souza (tomas.souza@gmail.com)
 */

%%

%byaccj

%{
  private Parser yyparser;

  public Yylex(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
    yyline = 1;
  }

  public int getLine() {
    return yyline;
  }
%}

NUM = [0-9]+
NL  = \n|\r|\r\n

%%

"$TRACE_ON"  { yyparser.setDebug(true);  }
"$TRACE_OFF" { yyparser.setDebug(false); }
"$MOSTRA_TS" { yyparser.printTable(); }

/* operators */
"."   |
"+"   | 
"="   |
">"   |
";"   |
"("   |
")"   |
"\{"  |
"\}"  |
"\["  | 
"\]"  { return (int) yycharat(0); }

"&&"  { return Parser.AND; }

{NUM}   { yyparser.yylval = new ParserVal(Integer.parseInt(yytext()));
          return Parser.NUM; }

int     { return Parser.INT; }
float   { return Parser.FLOAT; }
bool    { return Parser.BOOL; }
string  { return Parser.STRING; }
void    { return Parser.VOID; }
main    { return Parser.MAIN; }
if      { return Parser.IF; }
struct  { return Parser.STRUCT; }

[a-zA-Z][a-zA-Z_0-9]* { yyparser.yylval = new ParserVal(yytext());
                        return Parser.IDENT; }

\"[^\"]*\"  { yyparser.yylval = new ParserVal(yytext());
              return Parser.LITERAL; }

{NL}   { yyline++; }
[ \t]+ { }

.    { System.err.println("Error: unexpected character '" + yytext() + "' on line " + yyline + "."); return YYEOF; }
