package ast;

public class NumberExpr extends TermExpr{
	private int number;
	
	public void genC(int tabs){
		System.out.print(this.number + " ");
	}
	
	public NumberExpr(int num){
		this.number = num;
	}
	
	public int getNum(){
		return this.number;
	}

}
