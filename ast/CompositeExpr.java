package ast;

public abstract class CompositeExpr extends Expr{
	protected Expr operExpr;
	protected Expr leftExpr;
	protected Expr rightExpr;
	
	public abstract void genC(int tabs);
	
	
	public CompositeExpr(Expr left, Expr right, Expr oper ){
		this.leftExpr = left;
		this.operExpr = oper;
		this.rightExpr = right;
	}
	
	public CompositeExpr(Expr right){
		this.leftExpr = null;
		this.operExpr = null;
		this.rightExpr = right;
	}
	
	public CompositeExpr(){
		this.leftExpr = null;
		this.operExpr = null;
		this.rightExpr = null;
	}
	
	public void setLeft(Expr left){
		this.leftExpr = left;
	}
	
	public void setRight(Expr right){
		this.rightExpr = right;
	}
	
	public void setOper(Expr oper){
		this.operExpr = oper;
	}
}