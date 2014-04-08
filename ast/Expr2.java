package ast;

//Expr1 é uma especialização de CompositeExpr, que acrescenta o operador de multiplicação.

public class Expr2 extends CompositeExpr {

	@Override
	public void genC(int tabs) {
		if (this.rightExpr == null)
			this.leftExpr.genC(tabs);
		else {
			this.leftExpr.genC(tabs);
			System.out.print("* ");
			this.rightExpr.genC(tabs);
		}
	}
}