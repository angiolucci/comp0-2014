package ast;

public abstract class Stmt {
	public abstract void genC(int tabs, StringBuilder code);
}
