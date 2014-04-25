##Makefile
```
make            # flex byacc java
    flex
    byacc
    java
    clean
    reset
```
---
###Geração do analisador léxico
Entrada: `lexico.flex`
```
java -jar jflex.jar lexico.flex
```
Saída: `Yylex.java`

### Geração do analisador sintático
Entrada: `exemplo.y`
```
./yacc.linux -tv -J exemplo.y

  -t Trace
  -v Verbose
  -J Java
```
Saída: `Parser.java`

### Compilação das classes geradas
Entrada: `Yylex.java` ou `Parser.java` (pode ser qualquer um pois ambas possuem referência para a outra)
```
javac Parser.java
```
Saída: `Parser.class`