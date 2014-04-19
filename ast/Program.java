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
	
	public void setVarLists(VarList vList){
		this.vars = vList;
	}
	
	public void setStmts( ArrayList<Stmt> stmtList){
		this.stmts = stmtList;
	}
	
	public String genC(){
		int tabCounts = 1;
		StringBuilder code = new StringBuilder();
		
		code.append("#include <stdio.h>\n");
		code.append("int main(){\n");
		vars.genC(tabCounts, code);
		
		for (int i = 0; i < this.stmts.size(); i++)
			this.stmts.get(i).genC(tabCounts, code);
		
		code.append("\treturn 0;\n}");
		return new String(code);
		
	}
}
