current_dir = $(shell pwd)

all: flex byacc java

flex:
	java -jar $(current_dir)/../lib/jflex-1.5.0.jar lexico.flex

byacc:
	./../lib/yacc.linux -tv -J exemplo1.y

java:
	javac Parser.java

clean:
	rm -f *.class

reset: clean
	rm -f *.java
	rm -f *.java~
