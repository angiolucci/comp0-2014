package ast;

public class numberExpr extends Expr{
	private int number;
	
	public void genC(int tabs){
		System.out.print(this.number + " ");
	}
	
	public numberExpr(int num){
		this.number = num;
	}
	
	public int getNum(){
		return this.number;
	}

}
