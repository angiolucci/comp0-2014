package ast;

public class Expr1 extends CompositeExpr{

	@Override
	public void genC(int tabs) {
		if (this.leftExpr == null)
			this.rightExpr.genC(tabs);
		else {
			this.leftExpr.genC(tabs);
			System.out.print("+ ");
			this.rightExpr.genC(tabs);
		}
	}
}
