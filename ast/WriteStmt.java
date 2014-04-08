package ast;

public class WriteStmt extends Stmt{
	private CompositeExpr expr;
	
	public WriteStmt(CompositeExpr compExpr){
		this.expr = compExpr;
	}

	
	public void genC(int tabs) {
		
		for (int i = 0; i < tabs; i++)
			System.out.print("\t");
			
		System.out.print("printf( \"%d\\n\", ");
		this.expr.genC(tabs);
		System.out.println(");");
	}

}
