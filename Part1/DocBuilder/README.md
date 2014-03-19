## Parte 1: Analise Léxica e Análise Sintática Descendente Recursiva

### Considerações

* Entrega até o dia 01/04 (data da prova P1) até as 23:55h
* Trabalhos que não compilarem/executarem serão desconsiderados
* As entregas de trabalhos são feitas exclusivamente pelo envio de arquivo zipado (zip ou rar) na sala de entrega disponibilizada no sistema moodle. Não serão aceitas quaisquer outras formas de entrega
* Os trabalhos que configurarem algum tipo de fraude (cópia) serão anulados e atribuída a nota zero a todos os alunos envolvidos
* Quaisquer questões, sobre a execução ou entendimento do trabalho, devem ser submetidas (e serão acordadas) no forum criado para discussão do trabalho prático

### Sobre a entrega

* Especificações JFlex implementadas e todos os arquivos fontes necessários à compilação e execução do exercício (o grupo pode optar por utilizar a versão C++, ou C, dos geradores)
* Todos os arquivos devem estar claramente identificados com o nome, numero de matrícula e e-mail de cada um dos componentes do grupo
* Relatório, também identificado, contendo instruções de compilação, plataforma utilizada para o desenvolvimento do trabalho, itens da descrição que não foram implementados e o porquê, e se for o caso, o que foi implementado além do que foi solicitado, além da gramática implementada no ASDR, Este relatório deverá estar em formato texto ou html.

### Execução

A partir da sua resolução do exercício sobre o uso do JFlex (http://moodle.pucrs.br/mod/page/view.php?id=324330), realize as seguintes alterações:

#### Exercício 1 (manipulação do "modelo.rtf")

Crie mais dois tipos de templates de documentos (um recibo de pagamento, por exemplo, e outro à escolha do grupo). Escreva uma pequena aplicação com interface gráfica “java/swing” que permita a entrada dos dados necessários para um contrato (se o grupo preferir os dados podem estar em uma coleção e a interface carrega os dados desta coleção). Na interface deve ser possível selecionar um dos templates de documentos e definir o nome do arquivo de saída e um botão de execução que gera o documento solicitado com as informações disponibilizadas.

#### Exercício 2 (classe "pessoa")

Escreva um Analisador Sintático Descendente Recursivo que reconheça “pelo menos” a classe do exemplo, com as seguintes modificações:

* Todos os atributos são declarados antes dos métodos e são privativos
* Todos os métidos são publicos e retornam void
* As expressões aceitam apenas os operadores “=”, “>”, “+” e “*”
