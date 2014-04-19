package ast;

public class AttribStmt extends Stmt{
	private IdExpr id;
	private CompositeExpr expr;

	public void genC(int tabs, StringBuilder code) {
		
		for (int i = 0; i < tabs; i++)
			code.append("\t");
			
		this.id.genC(tabs, code);
		code.append("= ");
		this.expr.genC(tabs, code);
		
		code.append(";\n");
	}
	
	public AttribStmt(IdExpr id, CompositeExpr expr){
		this.id = id;
		this.expr = expr;
	}

}
