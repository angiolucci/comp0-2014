package lex;

import runtime.CompilerRuntime;

public class Lexer {
	private char[] input;
	private int tokenPos;
	private int token;
	private int numVal;
	private String idVal;
	private int lineCount;
	
	
	
	public int getLineNumber(){
		return this.lineCount;
	}
	
	public int token(){
		return this.token;
	}
	
	public int numVal(){
		return this.numVal;
	}
	
	public String idVal(){
		return this.idVal;
	}

	public Lexer(char[] input) {
		this.input = input;
		tokenPos = -1; // IMPORTANTE! nextToken()
		// incrementa em +1 para acessar a posicão zero
		token = Symbol.EOF;
		numVal = -255;
		idVal = new String();

		lineCount = 0;
	}

	/**************************************************************************
	 * nextToken()
	 */
	public void nextToken() {

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

			if (Character.isLetter(input[tokenPos])) {
				while (tokenPos < input.length
						&& (Character.isLetter(input[tokenPos])
								|| input[tokenPos] == '_' || Character
									.isDigit(input[tokenPos]))) {

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
					while (tokenPos < input.length
							&& Character.isDigit(input[tokenPos])) {
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
					case '#':
						while (tokenPos < input.length && input[tokenPos] != '\n')
								tokenPos++;
						if (tokenPos == input.length)
							tokenPos--;
						lineCount++;
						this.nextToken();
						break;
					default:
						token = Symbol.UNKNOW;
						CompilerRuntime.analysisError("Caractere especial não aceito: \'"
								+ input[tokenPos] + "\'", lineCount);
					}
				}
			}
		}
	} // nextToken()

}
