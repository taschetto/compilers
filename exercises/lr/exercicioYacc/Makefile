jflex = java -jar ../../../lib/jflex-1.5.0.jar
byacc = ./../../../lib/byaccj -tv -J

all: flex yacc java

flex:
	$(jflex) lexico.flex

yacc:
	$(byacc) exercicio.y

java:
	javac Parser.java

clean:
	rm -f *.class

reset: clean
	rm -f *.java
	rm -f *.java~
