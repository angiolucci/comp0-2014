package ast;

public class OperExpr extends Expr{
	private char operator;
	
	public void genC(int tabs){
		System.out.print(this.operator + " ");
	}
	
	public OperExpr(char op){
		this.operator = op;
	}
	
	public char getOper(){
		return this.operator;
	}

}
