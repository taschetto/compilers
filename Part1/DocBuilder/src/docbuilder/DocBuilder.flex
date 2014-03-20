/* 

...

*/

package docbuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

%%

/*
== Options and Declarations
*/

%class DocBuilderLexer
%integer
%line
%column

%unicode
/*%public*/
%standalone

%%

/*
== Lexical Rules
*/

<YYINITIAL> {
"#data#"
{
		Calendar c = new GregorianCalendar();
    		Date d = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println(sdf.format(d));
}

"#nome#"
{
	System.out.println("Guilherme Taschetto");
}

"#valor#"
{
	System.out.println("12345.99");
}

"#numero#"
{
	System.out.println("10");
}

"#juros#"
{
	System.out.println("0.8%");
}

"#parcelas#"
{
	String r = "";
	for (int i = 0; i < 10; i++)
	{
		r += " " + i;
	}
	
	System.out.println(r);
}

}

/* other symbols */
[^]                              { System.out.print(yytext()); }

