
%%

%{
  private int comment_count = 0;

  private AsdrSample yyparser;

  public Yylex(java.io.Reader r, AsdrSample yyparser) {
    this(r);
    this.yyparser = yyparser;
  }


%} 

%integer
%line
%char

WHITE_SPACE_CHAR=[\n\r\ \t\b\012]

%% 

"LOCATIONS" { return AsdrSample.LOCATIONS; }
"REGIONS"	  { return AsdrSample.REGIONS; }
"RECT"	    { return AsdrSample.RECT; }
"CIRCLE"	 	{ return AsdrSample.CIRCLE; }
"END"		    { return AsdrSample.END; }

[:jletter:][:jletterdigit:]* { return AsdrSample.IDENT; }  
"-"?[0-9]+("."[0-9]+)?       { return AsdrSample.COORD; }

{WHITE_SPACE_CHAR}+ { }
"#".*               { }

. { System.out.println("Erro lexico: caracter invalido: <" + yytext() + ">"); }





