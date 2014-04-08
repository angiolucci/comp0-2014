import ast.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Compiler {
	private char[] input;
	private int tokenPos;
	private int token;
	private int numVal;
	private String idVal;
	private int lineCount;
	private VarList variables;

	/**************************************************************************
	 * compile()
	 */
	public void compile(char[] fileContent) {
		this.input = fileContent;
		Program p = null;

		nextToken();
		p = program();
		/*
		 * while (token != Symbol.EOF) { System.out.print(token + " ");
		 * nextToken(); }
		 */
		p.genC();
	}

	/**************************************************************************
	 * Compiler()
	 */

	public Compiler() {
		input = null;
		tokenPos = -1; // IMPORTANTE! nextToken()
						// incrementa em +1 para acessar a posicão zero
		token = Symbol.EOF;
		numVal = -255;
		idVal = new String();

		lineCount = 0;
		variables = new VarList();
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
					variables.add(idVal);
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
					default:
						token = Symbol.UNKNOW;
						error("Caractere especial não aceito: \'" + input[tokenPos] + "\'");
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
	private void error(String message) {
		System.out.println("**********************************");
		System.out.println("ERRO na linha " + lineCount + ": ");
		System.out.println(message);
		System.exit(-1);
	}

	/***********************************************************************
	 * Regras de produção
	 ***********************************************************************/

	// ID ::= (Aa,Bb,..,Zz) {_, Aa,Bb,..,Zz}
	private IdExpr id() {
		IdExpr id = null;
		if (token == Symbol.ID) {
			id = new IdExpr(idVal);
			nextToken();
		} else {
			error("É esperado um identificador.");
		}
		
		return id;
	}

	// NUM ::= (0,1,..,9) {0,1,..,9}
	private NumberExpr num() {
		NumberExpr num = null;
		if (token == Symbol.NUM) {
			num = new NumberExpr(numVal); 
			nextToken();
		} else {
			error("É esperado um operando numérico.");
		}
		
		return num;
	}

	// TERM ::= NUM | ID
	private TermExpr term() {
		TermExpr term = null;
		switch (token) {
		case Symbol.NUM:
			term = num();
			break;
		case Symbol.ID:
			term = id();
			break;
		default:
			error("É esperado um operando ou um identificador.");
		}
		return term;
	}

	// EXPR2_ ::= * TERM EXPR2_ | VAZIO
	private Expr2 expr2_() {
		Expr2 ex2 = null;
		if (token == Symbol.MULT) {
			ex2 = new Expr2();
			nextToken();
			ex2.setLeft(term());
			ex2.setRight(expr2_());
		}
		return ex2;
	}

	// EXPR2 ::= TERM EXPR2_
	private Expr2 expr2() {
		Expr2 ex2 = new Expr2();
		ex2.setLeft(term());
		ex2.setRight(expr2_());
		
		return ex2;
	}

	// EXPR1_ ::= + EXPR2 EXPR1_ | VAZIO
	private Expr1 expr1_() {
		Expr1 ex1 = null;
		if (token == Symbol.PLUS) {
			ex1 = new Expr1();
			nextToken();
			ex1.setLeft(expr2());
			ex1.setRight(expr1_());
		}
		return ex1;
	}

	// EXPR1 ::= EXPR2 EXPR1_
	private Expr1 expr1() {
		Expr1 ex1 = new Expr1();
		ex1.setLeft(expr2());
		ex1.setRight(expr1_());
		return ex1;
	}

	// ATTRIB_STMT ::= ID '=' EXPR1 ';'
	private AttribStmt attrib_stmt() {
		AttribStmt as = null;
		IdExpr id = null;
		Expr1 ex1 = null;
		
		id = id();
		if (token == Symbol.ASSIGN) {
			nextToken();
			ex1 = expr1();
			if (token == Symbol.SEMICOLON) {
				nextToken();
			} else
				error("É Esperado \';\' no final da expressão.");
		} else {
			error("É Esperado sinal de atribuição.");
		}
		as = new AttribStmt(id, ex1);
		return as;
		
	}

	// WRITE_STMT ::= 'write' '(' EXPR1 ')' ';'
	private WriteStmt write_stmt() {
		WriteStmt ws = null;
		CompositeExpr ce = null;
		
		if (token == Symbol.WRITE) {
			nextToken();
			if (token == Symbol.LPAR) {
				nextToken();
				ce = expr1();
				if (token == Symbol.RPAR) {
					nextToken();
					if (token == Symbol.SEMICOLON) {
						nextToken();
					} else
						error("É esperado \';\' após a instrução \'write\'.");
				} else
					error("É esperado \')\' junto da instrução \'write\'.");
			} else
				error("É esperado \'(\' junto da instrução \'write\'.");
		} else
			error("É esperado a instrução \'write()\'.");
		
		ws = new WriteStmt(ce);
		return ws;
	}

	// STMT ::=  WRITE_STMT | ATTRIB_STMT
	private Stmt stmt() {
		Stmt stmt = null;
		/* while (token != Symbol.EOF) { */
			if (token == Symbol.WRITE)
				stmt = write_stmt();
			else if (token == Symbol.ID)
				stmt = attrib_stmt();
			else
				error("stmt(): É esperado atribuição de variável ou "
						+ "instrução \'write\'");
			return stmt;
		/*} */
	}
	
	// STMT_LIST ::= { STMT }
	private ArrayList<Stmt> stmt_list(){
		ArrayList<Stmt> stmts = new ArrayList<Stmt>();
		while (token != Symbol.EOF){
			stmts.add(stmt());
		}
		return stmts;
	}
	
	// PROGRAM	::= STMT_LIST
	private Program program(){
		Program program = new Program();
		program.setVarLists(this.variables);
		ArrayList<Stmt> stms = stmt_list();
		program.setStmts(stms);
		
		return program;
		
	}
	
	 
	
	
}