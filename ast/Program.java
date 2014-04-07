package ast;

import java.util.ArrayList;

public class Program {

	private ArrayList<Stmt> stmts;
	private VarList vars;
	
	public Program(){
		this.stmts = new ArrayList<Stmt>();
	}
	
	public void addStmt(Stmt stmt){
		this.stmts.add(stmt);
	}
	
	public void genC(){
		int tabCounts = 1;
		
		System.out.println("#include <stdio.h>\n");
		System.out.println("int main(){");
		
		for (int i = 0; i < this.stmts.size(); i++)
			this.stmts.get(i).genC(tabCounts);
		
	}
}
