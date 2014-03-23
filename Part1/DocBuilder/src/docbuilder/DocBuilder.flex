/*
  User Code
*/

package docbuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

%%

/*
  Options and Declarations
*/

%integer
%line
%char

%state CONTRACT
%state RECEIPT
%state CERTIFICATE

%{
  public String textToPrint = "";
%} 

%%

/*
  Lexical Rules
*/

<CONTRACT>
{
  "#data#"
  {
    Calendar c = new GregorianCalendar();
    Date d = c.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    textToPrint = sdf.format(d);
  }

  "#nome#"
  {
    textToPrint = "Guilherme Taschetto";
  }

  "#valor#"
  {
	  textToPrint = "12345.99";
  }

  "#numero#"
  {
	  textToPrint = "10";
  }

  "#juros#"
  {
	  textToPrint = "0.8%";
  }

  "#parcelas#"
  {
    String r = "";
    for (int i = 0; i < 10; i++)
    {
      r += " " + i;
	  }

  	textToPrint = r;
  }

  [^]
  {
    textToPrint = yytext();
  }
}

<RECEIPT>
{
  "#nome"
  {
    textToPrint = yytext();
  }

  "#valor#"
  {
    textToPrint = yytext();
  }

  "#pagador"
  {
    textToPrint = yytext();
  }

  "#item#"
  {
    textToPrint = yytext();
  }

  "#data#"
  {
    textToPrint = yytext();
  }
}

<CERTIFICATE>
{
  "#nome"
  {
    textToPrint = yytext();
  }

  "#curso"
  {
    textToPrint = yytext();
  }

  "#professor#"
  {
    textToPrint = yytext();
  }

  "#cargahoraria#"
  {
    textToPrint = yytext();
  }

  "#local#"
  {
    textToPrint = yytext();
  }

  "#data#"
  {
    textToPrint = yytext();
  }

  "#nomeassinatura1#"
  {
    textToPrint = yytext();
  }

  "#cargoassinatura1#"
  {
    textToPrint = yytext();
  }

  "#nomeassinatura2#"
  {
    textToPrint = yytext();
  }

  "#cargoassinatura2"
  {
    textToPrint = yytext();
  }
}

