## Geração de Código

Parte 3 do trabalho prático da disciplina de Compiladores da PUCRS (semestre 2014/1), ministrada pelo professor Alexandre Agustini.

**Copyright (c) 2014**
* **12180247-4 Guilherme Taschetto (gtaschetto@gmail.com)**
* **07104168-5 Fernando Delazeri (panchos3t@gmail.com)**
* **11100166-5 Tomas Ceia Ramos de Souza (tomas.souza@gmail.com)**

## Compilação

Esta distribuição consta com o diretório do projeto `CodeGen`, contendo, além dos fontes, um `Makefile` com a seguinte sintaxe de uso:

Para gerar o gerador de código baseado nas especificações FLEX/YACC:

    make

Para limpar o diretório, removendo arquivos gerados:

    make clean

## Execução

    cd CodeGen
    make
    ./run.x teste.cmm
    ./teste
