/*
 * Copyright (c) 2014
 *   Guilherme Taschetto (gtaschetto@gmail.com)
 *   Fernando Delazeri (fernando.delazeri@acad.pucrs.br)
 */

package docbuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.io.BufferedWriter;
import java.util.Map;

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
  private BufferedWriter writer = null;
  private Map<String, String> values = null;

  public Yylex(java.io.Reader in, BufferedWriter out, Map<String, String> values)
  {
    this.zzReader = in;
    this.writer = out;
    this.values = values;
  }
%} 

%%

/*
  Lexical Rules
*/

<CONTRACT>
{
  "#local#" | "#nome#" | "#valor#" | "#numero#" | "#juros#"
  {
    String text = values.get(yytext());
    if (text == null) text = "#campo nao informado#";
    writer.write(text);
  }

  "#data#"
  {
    Calendar c = new GregorianCalendar();
    Date d = c.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    writer.write(sdf.format(d));
  }

  "#parcelas#"
  {
    String r = "";
    Calendar c = GregorianCalendar.getInstance();
    
    String text = values.get("#numero#");

    if (text != null)
    {
      int num = Integer.parseInt(text);
      for (int i = 0; i < num; i++)
      {
        c.add(Calendar.MONTH, 1);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        r += " " + (i + 1) + ". " + sdf.format(d) + "\\par";
  	  }
    }

  	writer.write(r);
  }

  [^]
  {
    writer.write(yytext());
  }
}

<RECEIPT>
{
  "#nome#" | "#valor#" | "#pagador#" | "#item#" | "#local#"
  {
    String text = values.get(yytext());
    if (text == null) text = "#campo nao informado#";
    writer.write(text);
  }

  "#data#"
  {
    Calendar c = new GregorianCalendar();
    Date d = c.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    writer.write(sdf.format(d));
  }

  [^]
  {
    writer.write(yytext());
  }
}

<CERTIFICATE>
{
  "#nome#" | "#curso#" | "#professor#" | "#cargahoraria#" | "#local#" |
  "#nomeassinatura1#" | "#cargoassinatura1#" | "#nomeassinatura2#" | "#cargoassinatura2#"
  {
    String text = values.get(yytext());
    if (text == null) text = "#campo nao informado#";
    writer.write(text);
  }

  "#data#"
  {
    Calendar c = new GregorianCalendar();
    Date d = c.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    writer.write(sdf.format(d));
  }

  [^]
  {
    writer.write(yytext());
  }
}

