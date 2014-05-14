jflex       = java -jar ../lib/jflex-1.5.0.jar
byacc       = ../lib/./byacc -tv -J

all: flex yacc project

flex:
	$(jflex) src/typechecker/TypeChecker.flex

yacc:
	$(byacc) src/typechecker/TypeChecker.y

project:
	ant

clean:
	ant clean
