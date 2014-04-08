package ast;

public abstract class CompositeExpr extends Expr{
	protected CompositeExpr leftExpr;
	protected CompositeExpr rightExpr;
	
	public abstract void genC(int tabs);
	
	
	public CompositeExpr(CompositeExpr left, CompositeExpr right){
		this.leftExpr = left;
		this.rightExpr = right;
	}
	
	
	public CompositeExpr(){
		this.leftExpr = null;
		this.rightExpr = null;
	}
	
	public void setLeft(CompositeExpr left){
		this.leftExpr = left;
	}
	
	public void setRight(CompositeExpr right){
		this.rightExpr = right;
	}
}