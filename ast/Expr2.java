package ast;

//Expr1 é uma especialização de CompositeExpr, que acrescenta o operador de multiplicação.

public class Expr2 extends CompositeExpr {

	@Override
	public void genC(int tabs, StringBuilder code) {
		if (this.rightExpr == null)
			this.leftExpr.genC(tabs, code);
		else {
			this.leftExpr.genC(tabs, code);
			code.append("* ");
			this.rightExpr.genC(tabs, code);
		}
	}
}