package ast;

public class WriteStmt extends Stmt{
	private Expr expr;
	
	public WriteStmt(Expr expr){
		this.expr = expr;
	}

	
	public void genC(int tabs) {
		
		System.out.print("\n");
		for (int i = 0; i < tabs; i++)
			System.out.println("\t");
			
		System.out.print("printf(\"%d\\n\", ");
		this.expr.genC(tabs);
		System.out.println(");");
	}

}
