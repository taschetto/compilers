jflex = java -jar ../../../lib/jflex-1.5.0.jar
byacc = ./../../../lib/byaccj

all: flex yacc

flex:
	$(jflex) calc.flex

yacc:
	$(byacc) calc.y
