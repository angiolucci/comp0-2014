import ast.*;
import java.util.ArrayList;
import lex.*;
import runtime.CompilerRuntime;

public class Compiler {
	private VarList variables;
	private Lexer lexer;

	/**************************************************************************
	 * compile()
	 */
	public String compile() {
		Program p = null;
		lexer.nextToken();
		p = program();
		return p.genC();

	}

	/**************************************************************************
	 * Compiler()
	 */

	public Compiler(char[] fileContent) {
		variables = new VarList();
		lexer = new Lexer(fileContent);
	}

	/***********************************************************************
	 * Regras de produção
	 ***********************************************************************/

	// ID ::= (Aa,Bb,..,Zz) {_, Aa,Bb,..,Zz}
	private IdExpr id() {
		IdExpr id = null;
		if (lexer.token() == Symbol.ID) {
			id = new IdExpr(lexer.idVal());
			lexer.nextToken();
		} else {
			CompilerRuntime.analysisError("É esperado um identificador.",
					lexer.getLineNumber());
		}
		variables.add(id);
		return id;
	}

	// NUM ::= (0,1,..,9) {0,1,..,9}
	private NumberExpr num() {
		NumberExpr num = null;
		if (lexer.token() == Symbol.NUM) {
			num = new NumberExpr(lexer.numVal());
			lexer.nextToken();
		} else {
			CompilerRuntime.analysisError("É esperado um operando numérico.",
					lexer.getLineNumber());
		}

		return num;
	}

	// TERM ::= NUM | ID
	private TermExpr term() {
		TermExpr term = null;
		switch (lexer.token()) {
		case Symbol.NUM:
			term = num();
			break;
		case Symbol.ID:
			term = id();
			break;
		default:
			CompilerRuntime.analysisError(
					"É esperado um operando ou um identificador.",
					lexer.getLineNumber());
		}
		return term;
	}

	// EXPR2_ ::= * TERM EXPR2_ | VAZIO
	private Expr2 expr2_() {
		Expr2 ex2 = null;
		if (lexer.token() == Symbol.MULT) {
			ex2 = new Expr2();
			lexer.nextToken();
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
		if (lexer.token() == Symbol.PLUS) {
			ex1 = new Expr1();
			lexer.nextToken();
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
		if (lexer.token() == Symbol.ASSIGN) {
			lexer.nextToken();
			ex1 = expr1();
			if (lexer.token() == Symbol.SEMICOLON) {
				lexer.nextToken();
			} else
				CompilerRuntime.analysisError(
						"É Esperado \';\' no final da expressão.",
						lexer.getLineNumber());
		} else {
			CompilerRuntime.analysisError("É Esperado sinal de atribuição.",
					lexer.getLineNumber());
		}
		as = new AttribStmt(id, ex1);
		return as;

	}

	// WRITE_STMT ::= 'write' '(' EXPR1 ')' ';'
	private WriteStmt write_stmt() {
		WriteStmt ws = null;
		CompositeExpr ce = null;

		if (lexer.token() == Symbol.WRITE) {
			lexer.nextToken();
			if (lexer.token() == Symbol.LPAR) {
				lexer.nextToken();
				ce = expr1();
				if (lexer.token() == Symbol.RPAR) {
					lexer.nextToken();
					if (lexer.token() == Symbol.SEMICOLON) {
						lexer.nextToken();
					} else
						CompilerRuntime.analysisError(
								"É esperado \';\' após a instrução \'write\'.",
								lexer.getLineNumber());
				} else
					CompilerRuntime.analysisError(
							"É esperado \')\' junto da instrução \'write\'.",
							lexer.getLineNumber());
			} else
				CompilerRuntime.analysisError(
						"É esperado \'(\' junto da instrução \'write\'.",
						lexer.getLineNumber());
		} else
			CompilerRuntime.analysisError(
					"É esperado a instrução \'write()\'.",
					lexer.getLineNumber());

		ws = new WriteStmt(ce);
		return ws;
	}

	// STMT ::= WRITE_STMT | ATTRIB_STMT
	private Stmt stmt() {
		Stmt stmt = null;
		/* while (lexer.token() != Symbol.EOF) { */
		if (lexer.token() == Symbol.WRITE)
			stmt = write_stmt();
		else if (lexer.token() == Symbol.ID)
			stmt = attrib_stmt();
		else
			CompilerRuntime.analysisError(
					"stmt(): É esperado atribuição de variável ou "
							+ "instrução \'write\'", lexer.getLineNumber());
		return stmt;
		/* } */
	}

	// STMT_LIST ::= { STMT }
	private ArrayList<Stmt> stmt_list() {
		ArrayList<Stmt> stmts = new ArrayList<Stmt>();
		while (lexer.token() != Symbol.EOF) {
			stmts.add(stmt());
		}
		return stmts;
	}

	// PROGRAM ::= STMT_LIST
	private Program program() {
		Program program = new Program();
		program.setVarLists(this.variables);
		ArrayList<Stmt> stms = stmt_list();
		program.setStmts(stms);

		return program;

	}

}