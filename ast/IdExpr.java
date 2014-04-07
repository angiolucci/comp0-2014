package ast;

public class IdExpr extends Expr{
	private String name;
	
	public IdExpr(String name){
		this.name = name;
	}
	
	public void genC(int tabs){
		System.out.println(this.name + " ");;
	}
	
	public String getName(){
		return this.name;
	}
}
