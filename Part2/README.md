## Análise Sintática Ascendente e Verificação Semântica

Parte 2 do trabalho prático da disciplina de Compiladores da PUCRS (semestre 2014/1), ministrada pelo professor Alexandre Agustini.

**Copyright (c) 2014**
**12180247-4 Guilherme Taschetto (gtaschetto@gmail.com)**
**07104168-5 Fernando Delazeri (panchos3t@gmail.com)**

## Dependências

Ambos os projetos foram criados utilizando a IDE Java Netbeans. A verificação do código e compilação pode ser feita utilizando esta IDE.

Alternativamente, um `Makefile` é distribuído com o projeto. Este `Makefile` utiliza a ferramenta [Apache Ant(TM)](http://ant.apache.org/) para compilação por linha de comando. Caso não possua o `ant`, instale-o com `sudo apt-get install ant` ou **utilize a IDE do Netbeans para realizar a compilação**.

## Compilação

Esta distribuição consta com o diretório do projeto `TypeChecker`, contendo, além dos fontes, um `Makefile` com a seguinte sintaxe de uso:

Para gerar o analisador léxico baseado na especificação FLEX, gerar o analisador sintático ascendente baseado na especificação YACC e compilar o projeto Java:

    make
    
Para gerar o analisador léxico baseado na especificação FLEX:

    make flex

Para gerar o analisador sintático ascendente baseado na especificação YACC:

    make yacc
    
Para compilar o projeto Java:

    make project

Para limpar o diretório:

    make clean
    
## Execução

    cd TypeChecker
    make
    java -jar dist/TypeChecker.jar [arquivo de entrada]

O arquivo de entrada é opcional. Ao não informá-lo o analisador utilizará o stream de entrada.
