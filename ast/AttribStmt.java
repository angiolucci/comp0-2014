package ast;

public class AttribStmt extends Stmt{
	private IdExpr id;
	private CompositeExpr expr;

	public void genC(int tabs) {
		
		for (int i = 0; i < tabs; i++)
			System.out.print("\t");
			
		this.id.genC(tabs);
		System.out.print("= ");
		this.expr.genC(tabs);
		
		System.out.println(";");
	}
	
	public AttribStmt(IdExpr id, CompositeExpr expr){
		this.id = id;
		this.expr = expr;
	}

}
