package ast;

public class IdExpr extends TermExpr{
	private String name;
	
	public IdExpr(String name){
		this.name = name;
	}
	
	public void genC(int tabs, StringBuilder code){
		code.append(this.name + " ");;
	}
	
	public String getName(){
		return this.name;
	}
}
