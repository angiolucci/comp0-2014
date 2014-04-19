package ast;

public class WriteStmt extends Stmt{
	private CompositeExpr expr;
	
	public WriteStmt(CompositeExpr compExpr){
		this.expr = compExpr;
	}

	
	public void genC(int tabs, StringBuilder code) {
		
		for (int i = 0; i < tabs; i++)
			code.append("\t");
			
		code.append("printf( \"%d\\n\", ");
		this.expr.genC(tabs, code);
		code.append(");\n");
	}

}
