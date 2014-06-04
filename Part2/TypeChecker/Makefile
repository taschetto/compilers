jflex = java -jar ../../lib/jflex-1.5.0.jar
byacc = ../../lib/./byaccj -tv -J
src   = 

all: flex yacc java

flex:
	$(jflex) $(src)TypeChecker.flex

yacc:
	$(byacc) $(src)TypeChecker.y

java:
	javac Parser.java

run:
	java Parser test.txt

clean:
	rm -f $(src)Yylex.java
	rm -f $(src)Parser.java $(src)ParserVal.java $(src)y.output
