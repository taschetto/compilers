/* 

THIS IS A RESUME OF http://jflex.de/manual.html

== User Code Section

  The text up to the first line starting with %% is copied verbatim to the top of
the generated lexer class (before the actual class declaration). Beside package
and import statements there is usually not much to do here. If the code ends
with a javadoc class comment, the generated class will get this comment, if not,
JFlex will generate one automatically.

*/

package docmodel;

%%

/*

== Options and declarations

  This section is a set of options, code that is included inside the generated
scanner class, lexical states and macro declarations. Each JFlex option must
begin a line of the specification and starts with a %.

  A few available options:

  %class Lexer      tells JFlex to give the generated class the name
                    ``Lexer'' and to write the code to a file ``Lexer.java''.

  %unicode          defines the set of characters the scanner will work on. For
                    scanning text files, %unicode should always be used. The
                    Unicode version may be specified, e.g. %unicode 4.1. If no
                    version is specified, the most recent supported Unicode
                    version will be used - in JFlex 1.5.0, this is Unicode 6.3. 

  %line             switches line counting on (the current line number can be
                    accessed via the variable yyline)

  %column           switches column counting on (current column is accessed via
                    yycolumn)

  The code included in %{...%} is copied verbatim into the generated lexer class
source. Here you can declare member variables and functions that are used inside
scanner actions. As JFlex options, both %{ and \%} must begin a line.

  The specification continues with macro declarations. Macros are abbreviations
for regular expressions, used to make lexical specifications easier to read and
understand. A macro declaration consists of a macro identifier followed by =,
then followed by the regular expression it represents. This regular expression
may itself contain macro usages. Although this allows a grammar like
specification style, macros are still just abbreviations and not non terminals -
they cannot be recursive or mutually recursive. Cycles in macro definitions are
detected and reported at generation time by JFlex.

  Here some of the example macros in more detail:

    LineTerminator = = \r|\n|\r\n
    
      LineTerminator stands for the regular expression that matches an ASCII CR,
      an ASCII LF or an CR followed by LF.

    InputCharacter = [^\r\n]
  
      InputCharacter stands for all characters that are not a CR or LF.

    WhiteSpace = {LineTerminator} | [ \t\f]

      WhiteSpace stands for any LineTerminator, white spaces, an ASCII TAB or
      an ASCII FF character.

    Identifier = [:jletter:] [:jletterdigit:]*

      Identifier matches each string that starts with a character of class
      jletter followed by zero or more characters of class jletterdigit. jletter
      and jletterdigit are predefined character classes. jletter includes all
      characters for which the Java function Character.isJavaIdentifierStart
      returns true and jletterdigit all characters for that
      Character.isJavaIdentifierPart returns true.

  The last part of the second section in our lexical specification is a lexical
state declaration: %state STRING declares a lexical state STRING that can be
used in the ``lexical rules'' part of the specification. A state declaration is
a line starting with %state followed by a space or comma separated list of state
identifiers. There can be more than one line starting with %state.
*/

%class DocModelLexer
%integer
%line
%column

%unicode
%public
%debug
%standalone

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}
TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

Identifier = [:jletter:] [:jletterdigit:]*
DecIntegerLiteral = 0 | [1-9][0-9]*

%%

/*

== Lexical Rules Section

  This section contains regular expressions and actions (Java code) that are
executed when the scanner matches the associated regular expression. As the
scanner reads its input, it keeps track of all regular expressions and
activates the action of the expression that has the longest match.

  If two regular expressions both have the longest match for a certain input,
the scanner chooses the action of the expression that appears first in the
specification.

  The last lexical rule in the example specification is used as an error
fallback. It matches any character in any state that has not been matched by
another rule. It doesn't conflict with any other rule because it has the least
priority (because it's the last rule) and because it matches only one character
(so it can't have longest match precedence over any other rule).

*/

<YYINITIAL> "abstract"           { System.out.println("ABSTRACT"); }
<YYINITIAL> "boolean"            { System.out.println("BOOLEAN"); }
<YYINITIAL> "break"              { System.out.println("BREAK"); }
<YYINITIAL> {
  {Identifier}                   { System.out.println("IDENTIFIER"); }
  {DecIntegerLiteral}            { System.out.println("INTEGER_LITERAL"); }
  \"                             { System.out.println("DOUBLE QUOTE"); }
  "="                            { System.out.println("EQ"); }
  "=="                           { System.out.println("EQEQ"); }
  "+"                            { System.out.println("PLUS"); }
  {Comment}                      { /* ignore */ }
  {WhiteSpace}                   { /* ignore */ }
}

/* error fallback */
[^]                              { System.out.println("Illegal character <" + yytext() + ">"); }

