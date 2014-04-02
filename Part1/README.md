## Análise Léxica e Analisador Sintático Descendente Recursivo

Parte 1 do trabalho prático da disciplina de Compiladores da PUCRS (semestre 2014/1), ministrada pelo professor Alexandre Agustini.

**Copyright (c) 2014**
**12180247-4 Guilherme Taschetto (gtaschetto@gmail.com)**
**07104168-5 Fernando Delazeri (panchos3t@gmail.com)**

## Dependências

Ambos os projetos foram criados utilizando a IDE Java Netbeans. A verificação do código e compilação pode ser feita utilizando esta IDE.

Alternativamente, um `Makefile` é distribuído com os projetos. Este `Makefile` utiliza a ferramenta [Apache Ant(TM)](http://ant.apache.org/) para compilação por linha de comando. Caso não possua o `ant`, instale-o com `sudo apt-get install ant` ou **utilize a IDE do Netbeans para realizar a compilação**.

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

#### Gramática verificada pelo ASDR

            Class : public class IDENT { Declarations }
      
     Declarations : AtributeList Constructor MethodList
      
     AtributeList : Atribute AtributeList
                  | empty
      
         Atribute : private Type IDENT;
      
             Type : INT | DOUBLE | STRING;
             
      Constructor : public IDENT(ParameterList) Block
      
            Block : { CommandList }
      
       MethodList : Method MethodList
                  | empty
                  
           Method : public void IDENT(ParameterList) Block
      
    ParameterList : Type IDENT Parameter
                  | empty
      
        Parameter : , Type IDENT Parameter
                  | empty
      
      CommandList : Command CommandList
                  | empty
      
          Command : Block
                  | IDENT = Expression;
                  | THIS.IDENT = Expression;
                  | for (;;) Block
                  | if (Expression) Command
                  | break;
                  | return;
      
       Expression : SumAux Comp
      
             Comp : > SumAux Comp
                  | == SumAux Comp
                  | empty
      
           SumAux : MultAux Sum
      
              Sum : + MultAux Sum
                  | empty
      
          MultAux : Value Mult
      
             Mult : * Value Mult
                  | empty
                
            Value : THIS.IDENT
                  | IDENT
                  | NUM

#### Observações

* O analisador sintático **exige** um construtor na classe. Esta implementação foi além da definição inicial do projeto, onde o reconhecimento de um construtor não era necessário.
* O analisador sintático permite o aninhamento de blocos em qualquer nível, do tipo `{ { { { { Comando } } } } }`.

#### Exemplos de entradas aceitas pelo analisador

Exemplo 1:

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

Exemplo 2:

    public class Car
    {
      private String manufacturer;
      private String model;
      private int year;
      private int odometer;
      
      public Car()
      {
      }
      
      public void Drive(int kilometers)
      {
        this.odometer = this.odometer + kilometers;
      }
    }

Exemplo 3:

    public class Foo 
    {
      private int bar;
      
      public Foo(int bar)
      {
        {
          {
            {
              {
                {
                  this.bar = bar;
                }
              }
            }
          }
        }
      }
      
      public void foobar() {}
    }
