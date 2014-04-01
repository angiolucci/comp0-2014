import java.io.BufferedReader;
/*
 * STMT 		::= { WRITE_STMT | ATTRIB_STMT }
 * WRITE_STMT	::= 'write' '(' EXPR1 ')' ';'
 * ATTRIB_STMT	::= ID '=' EXPR1 ';'
 * EXPR1		::= EXPR2 EXPR1_
 * EXPR1_		::= + EXPR2 EXPR1_ | VAZIO
 * EXPR2		::= TERM EXPR2_
 * EXPR2_		::= * TERM EXPR2_  | VAZIO
 * TERM			::= NUM | ID
 * NUM			::= ( 0|1|...|9 ) { 0,1,..,9 }
 * ID			::= ( Aa|Bb|..|Zz ) { Aa,Bb,..,Zz }
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Compiler {
	private char[] input;
	private int tokenPos;
	private int token;
	private int numVal;
	private String idVal;
	private int lineCount;

	/**************************************************************************
	 * compile()
	 */
	public void compile(char[] fileContent) {
		this.input = fileContent;

		nextToken();
		stmt();
		/*while (token != Symbol.EOF) {
			System.out.print(token + " ");
			nextToken();
		}*/
	}

	/**************************************************************************
	 * Compiler()
	 */

	public Compiler() {
		input = null;
		tokenPos = -1;	// IMPORTANTE! nextToken()
					  	// incrementa em +1 para acessar a posicão zero
		token = Symbol.EOF;
		numVal = -255;
		idVal = new String();
		lineCount = 0;
	}

	/**************************************************************************
	 * openFile()
	 */
	public char[] openFile(String fileName) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo " + fileName + " não encontrado!");
			System.exit(-1);
		}
		char[] fileContent = null;
		String lineReaden = new String();
		String aux = new String();
		try {
			while ((lineReaden = br.readLine()) != null) {
				aux += '\n' + lineReaden;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fileContent = aux.toCharArray();

		// input = fileContent;
		return fileContent;
	}

	/**************************************************************************
	 * nextToken()
	 */
	private void nextToken() {

		if (input == null) {
			System.out.println("nextToken(): Erro ao tentar ler código-fonte!");
			System.exit(-1);
		}

		tokenPos++;

		while (tokenPos < input.length
				&& (input[tokenPos] == ' ' || input[tokenPos] == '\n'
                                || input[tokenPos] == '\t' || input[tokenPos] == '\r')) {
			
			if (input[tokenPos] == '\n')
				lineCount++;
			
			tokenPos++;
		}

		if (tokenPos == input.length)
			token = Symbol.EOF;
		else {
			StringBuilder sb = new StringBuilder();

			if (Character.isLetter(input[tokenPos]) || input[tokenPos] == '_') {
				while (tokenPos < input.length 
                                                && ( Character.isLetter(input[tokenPos])
						|| input[tokenPos] == '_'
						|| Character.isDigit(input[tokenPos])) ) {

					sb.append(input[tokenPos]);
					tokenPos++;
				} // while
				tokenPos--;

				if (sb.toString().equalsIgnoreCase("write"))
					token = Symbol.WRITE;
				else {
					token = Symbol.ID;
					idVal = new String(sb);
				}

			} else {
				if (Character.isDigit(input[tokenPos])) {
					while (tokenPos < input.length && Character.isDigit(input[tokenPos])) {
						sb.append(input[tokenPos]);
						tokenPos++;
					}
					tokenPos--;
					token = Symbol.NUM;
					numVal = Integer.parseInt(sb.toString());
				} else {
					switch (input[tokenPos]) {
					case ';':
						token = Symbol.SEMICOLON;
						break;
					case '(':
						token = Symbol.LPAR;
						break;
					case ')':
						token = Symbol.RPAR;
						break;
					case '+':
						token = Symbol.PLUS;
						break;
					case '*':
						token = Symbol.MULT;
						break;
					case '=':
						token = Symbol.ASSIGN;
						break;
					default:
						token = Symbol.UNKNOW;
						// System.out.println("nextToken(): Símbolo não inválido: "
						// + input[tokenPos]);
					}
				}
			}
		}
	} // nextToken()
	
	/**************************************************************************
	 * error()
	 */
	private void error(String message){
		System.out.println("**********************************");
		System.out.println("ERRO na linha " + lineCount + ": ");
        System.out.println(message);
        System.exit(-1);
	}
	
	/***********************************************************************
        * Regras de produção
        ***********************************************************************/
        
	// ID ::= [Aa,Bb,..,Zz] {Aa,Bb,..,Zz}
	private void id(){
		if (token == Symbol.ID){
			nextToken();
		} else {
			error("É esperado um identificador.");
		}
	}
	
	//NUM ::= [0,1,..,9] {0,1,..,9}
	private void num(){
		if (token == Symbol.NUM){
			nextToken();
		} else {
			error("É esperado um operando numérico.");
		}
	}
	
	//TERM ::= NUM | ID
	private void term(){
		switch (token){
		case Symbol.NUM:
			num();
			break;
		case Symbol.ID:
			id();
			break;
		default:
			error("É esperado um operando ou um identificador.");
		}
	}
	
	//EXPR2_ ::= * TERM EXPR2_ | VAZIO
	private void expr2_(){
		if (token == Symbol.MULT){
			nextToken();
			term();
			expr2_();
		}
	}
	
	//EXPR2	::= TERM EXPR2_
	private void expr2(){
		term();
		expr2_();
	}
	
	//EXPR1_ ::= + EXPR2 EXPR1_ | VAZIO
	private void expr1_(){
		if (token == Symbol.PLUS){
			nextToken();
			expr2();
			expr1_();
		}
	}
	
	// EXPR1 ::= EXPR2 EXPR1_
	private void expr1(){
		expr2();
		expr1_();
	}
	
	//ATTRIB_STMT ::= ID '=' EXPR1 ';'
	private void attrib_stmt(){
		id();
		if (token == Symbol.ASSIGN){
			nextToken();
			expr1();
			if (token == Symbol.SEMICOLON){
				nextToken();
			} else
				error("É Esperado \';\' no final da expressão.");
		} else {
			error("É Esperado sinal de atribuição.");
		}
	}
	
	//WRITE_STMT ::= 'write' '(' EXPR1 ')' ';'
	private void write_stmt(){
		if (token == Symbol.WRITE){
			nextToken();
			if (token == Symbol.LPAR){
				nextToken();
				expr1();
				if (token == Symbol.RPAR){
					nextToken();
					if (token == Symbol.SEMICOLON)
						nextToken();
					else
						error("É esperado \';\' após a instrução \'write\'.");
				} else 
					error("É esperado \')\' junto da instrução \'write\'.");
			} else
				error("É esperado \'(\' junto da instrução \'write\'.");
		} else
			error("É esperado a instrução \'write()\'.");
	}
	
	//STMT ::= { WRITE_STMT | ATTRIB_STMT }
	private void stmt(){
		while (token != Symbol.EOF){
			if (token == Symbol.WRITE)
				write_stmt();
			else
				if (token == Symbol.ID)
					attrib_stmt();
				else
					error("stmt(): É esperado atribuição de variável ou "
							+ "instrução \'write\'");
		}
		
	}
}