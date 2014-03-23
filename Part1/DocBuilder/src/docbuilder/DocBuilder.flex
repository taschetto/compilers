/*
  User Code
*/

package docbuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.io.BufferedWriter;

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

  public Yylex(java.io.Reader in, BufferedWriter out)
  {
    this.writer = out;
    this.zzReader = in;
  }
%} 

%%

/*
  Lexical Rules
*/

<CONTRACT>
{
  "#local#"
  {
    writer.write("Porto Alegre");
  }

  "#data#"
  {
    Calendar c = new GregorianCalendar();
    Date d = c.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    writer.write(sdf.format(d));
  }

  "#nome#"
  {
    writer.write("Guilherme Taschetto");
  }

  "#valor#"
  {
	  writer.write("12345.99");
  }

  "#numero#"
  {
	  writer.write("10");
  }

  "#juros#"
  {
	  writer.write("0.8");
  }

  "#parcelas#"
  {
    String r = "";
    Calendar c = GregorianCalendar.getInstance();
    
    for (int i = 0; i < 10; i++)
    {
      c.add(Calendar.MONTH, 1);
      Date d = c.getTime();
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      r += "[" + (i + 1) + "] " + sdf.format(d) + " ";
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
  "#nome#"
  {
    writer.write("Guilherme Taschetto");
  }

  "#valor#"
  {
    writer.write("1.800,00");
  }

  "#pagador#"
  {
    writer.write("Fernando Delazeri");
  }

  "#item#"
  {
    writer.write("consultoria em desenvolvimento de software");
  }

  "#local#"
  {
    writer.write("Porto Alegre");
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
  "#nome#"
  {
    writer.write("Guilherme Taschetto");
  }

  "#curso#"
  {
    writer.write("Compiladores");
  }

  "#professor#"
  {
    writer.write("Alexandre Agustini");
  }

  "#cargahoraria#"
  {
    writer.write("60");
  }

  "#local#"
  {
    writer.write("Porto Alegre");
  }

  "#data#"
  {
    Calendar c = new GregorianCalendar();
    Date d = c.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    writer.write(sdf.format(d));
  }

  "#nomeassinatura1#"
  {
    writer.write("Alexandre Agustini");
  }

  "#cargoassinatura1#"
  {
    writer.write("Professor");
  }

  "#nomeassinatura2#"
  {
    writer.write("Alfio Martini");
  }

  "#cargoassinatura2#"
  {
    writer.write("Diretor da Faculdade de Informática");
  }

  [^]
  {
    writer.write(yytext());
  }
}

