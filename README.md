Fase 2 do compilador 0 (2014)
============================
* Análise léxica;
* Análise sintática;
* Geração de cófigo em C.

Como compilar:
-------------
`javac *.java`

Como executar:
--------------
`java Main [caminho para código fonte]`
**ou**
`java Main [caminho para código fonte] > output.c`


Gramática:
----------
* STMT 		::= { WRITE_STMT | ATTRIB_STMT }
* WRITE_STMT	::= 'write' '(' EXPR1 ')' ';'
* ATTRIB_STMT	::= ID '=' EXPR1 ';'
* EXPR1		::= EXPR2 EXPR1_
* EXPR1_		::= + EXPR2 EXPR1_ | VAZIO
* EXPR2		::= TERM EXPR2_
* EXPR2_		::= * TERM EXPR2_  | VAZIO
* TERM		::= NUM | ID
* NUM		::= ( 0|1|...|9 ) { 0,1,..,9 }
* ID		::= ( Aa|Bb|..|Zz ) { Aa,Bb,..,Zz }

Authors
-------
Jonas M. dos Santos and Vinícius A. Reis
