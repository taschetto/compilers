## Análise Léxica e Analisador Sintático Descendente Recursivo

Parte 1 do trabalho prático da disciplina de Compiladores da PUCRS (semestre 2014/1), ministrada pelo professor Alexandre Agustini.

**Copyright (c) 2014 Guilherme Taschetto & Fernando Delazeri**

## Compilação

Esta distribuição consta com dois diretórios, `AsdrClass` e `DocBuilders`, cada um contendo um exercício do projeto. Além disto, cada um destes diretórios possui um [Makefile*](#dependencias) com a seguinte sintaxe de uso:

Para gerar o analisador léxico baseado na especificação FLEX e compilar o projeto Java:

    make
    
Para gerar o analisador léxico baseado na especificação FLEX:

    make flex
    
Para compilar o projeto Java:

    make project

Para limpar o diretório:

    make clean
    
<a name="dependencias"></a>**Dependências**: O Makefile dos projetos depende da ferramenta Apache Ant(TM) para compilar os projetos. Caso não possua, instale-o com `sudo apt-get install ant` ou utilize a IDE do Netbeans para realizar a compilação.    

## Execução

### DocBuilder

    cd DocBuilder
    make
    java -jar dist/DocBuilder.jar

### AsdrClass

    cd AsdrClass
    make
    java -jar dist/AsdrClass.jar [arquivo de entrada]

Sendo o arquivo de entrada opcional (ao não informá-lo o ASDR utilizará o stream de entrada).
