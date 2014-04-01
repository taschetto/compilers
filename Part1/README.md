## Análise Léxica e Analisador Sintático Descendente Recursivo

Parte 1 do trabalho prático da disciplina de Compiladores da PUCRS (semestre 2014/1), ministrada pelo professor Alexandre Agustini.

**Copyright (c) 2014 Guilherme Taschetto (gtaschetto@gmail.com) Fernando Delazeri (fernando.delazeri@acad.pucrs.br)**

## Dependências

O Makefile distribuído com os projetos utiliza a ferramenta Apache Ant(TM) para compilação simplificada por linha de comando. Caso não possua, instale-o com `sudo apt-get install ant` ou **utilize a IDE do Netbeans para realizar a compilação**.

## Compilação

Esta distribuição consta com dois diretórios, `AsdrClass` e `DocBuilder`, cada um contendo um exercício do projeto. Além disto, cada um destes diretórios possui um `Makefile` com a seguinte sintaxe de uso:

Para gerar o analisador léxico baseado na especificação FLEX e compilar o projeto Java:

    make
    
Para gerar o analisador léxico baseado na especificação FLEX:

    make flex
    
Para compilar o projeto Java:

    make project

Para limpar o diretório:

    make clean
    
## Execução

### DocBuilder

    cd DocBuilder
    make
    java -jar dist/DocBuilder.jar

### AsdrClass

    cd AsdrClass
    make
    java -jar dist/AsdrClass.jar [arquivo de entrada]

O arquivo de entrada é opcional. Ao não informá-lo o ASDR utilizará o stream de entrada.

**Observação importante**: O analisador sintático exige um construtor na classe. Esta implementação foi além da definição inicial do projeto, onde o reconhecimento do construtor foi dado como opcional.

Exemplo de entrada aceita pelo analisador:

    public class Pessoa
    {
      private int idade;
      private String nome;
      private double salario;
      
      public Pessoa (String nome, int idade )
      {
        this.nome = nome;
        this.idade= idade + 5 * 3 ;
      }
      
      private void dummy(double param)
      {
        for(;;) { 
          // processamento
          if (a>b) break;
        }
        return;
      }
    }
