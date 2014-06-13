## Análise Sintática Ascendente e Verificação Semântica

Parte 2 do trabalho prático da disciplina de Compiladores da PUCRS (semestre 2014/1), ministrada pelo professor Alexandre Agustini.

**Copyright (c) 2014**
* **12180247-4 Guilherme Taschetto (gtaschetto@gmail.com)**
* **07104168-5 Fernando Delazeri (panchos3t@gmail.com)**
* **11100166-5 Tomas Ceia Ramos de Souza (tomas.souza@gmail.com)**

## Compilação

Esta distribuição consta com o diretório do projeto `TypeChecker`, contendo, além dos fontes, um `Makefile` com a seguinte sintaxe de uso:

Para gerar o analisador léxico baseado na especificação FLEX, gerar o analisador sintático ascendente baseado na especificação YACC e compilar o projeto Java:

    make
    
Para gerar o analisador léxico baseado na especificação FLEX:

    make flex

Para gerar o analisador sintático ascendente baseado na especificação YACC:

    make yacc
    
Para compilar o projeto Java:

    make java

Para limpar o diretório, removendo arquivos gerados nas etapas anteriores:

    make clean
    
## Execução

    cd TypeChecker
    make
    java Parser [arquivo de entrada]

O arquivo de entrada é opcional. Ao não informá-lo o analisador utilizará o stream de entrada.
