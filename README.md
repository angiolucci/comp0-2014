Fase 4 do compilador 0 (2014)
============================
* Análise léxica;
* Análise sintática (com geração de árvore sintática);
* Geração de código em C.

Como compilar:
-------------
`javac Comp.java`

Como executar:
--------------
`java Comp < caminho para código fonte >`

Um código fonte com a extensão `.c` será gerado no mesmo diretório do
código fonte original.

Gramática:
----------
 * PROGRAM	::= STMT_LIST
 * STMT_LIST	::= { STMT }
 * STMT 	::=  WRITE_STMT | ATTRIB_STMT 
 * WRITE_STMT	::= 'write' '(' EXPR1 ')' ';'
 * ATTRIB_STMT	::= ID '=' EXPR1 ';'
 * EXPR1	::= EXPR2 EXPR1_
 * EXPR1_	::= + EXPR2 EXPR1_ | VAZIO
 * EXPR2	::= TERM EXPR2_
 * EXPR2_	::= * TERM EXPR2_  | VAZIO
 * TERM		::= NUM | ID
 * NUM		::= ( 0|1|...|9 ) { 0,1,..,9 }
 * ID		::= ( Aa|Bb|..|Zz ) { _, Aa,Bb,..,Zz }

Para fazer um comentário no código, utiliza o caractere `#`.