jflex = java -jar ../../lib/jflex-1.5.0.jar
byacc = ../../lib/./byaccj -tv -J -Jpackage=typechecker
src   = src/typechecker/

all: flex yacc project

flex:
	$(jflex) $(src)TypeChecker.flex

yacc:
	$(byacc) $(src)TypeChecker.y
	mv Parser.java ParserVal.java y.output $(src)

project:
	ant

clean:
	ant clean
	rm -f $(src)Yylex.java
	rm -f $(src)Parser.java $(src)ParserVal.java $(src)y.output
