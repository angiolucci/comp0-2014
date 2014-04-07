package ast;

public class AttribStmt extends Stmt{
	private IdExpr id;
	private Expr expr;

	public void genC(int tabs) {
		
		System.out.print("\n");
		for (int i = 0; i < tabs; i++)
			System.out.println("\t");
			
		this.id.genC(tabs);
		System.out.print("= ");
		this.expr.genC(tabs);
		
		System.out.println(";");
	}
	
	public AttribStmt(IdExpr id, Expr expr){
		this.id = id;
		this.expr = expr;
	}

}
