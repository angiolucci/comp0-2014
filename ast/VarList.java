package ast;

import java.util.HashSet;

public class VarList {
	private HashSet<IdExpr>  variables;
	
	public VarList(){
		this.variables = new HashSet<IdExpr>();
	}
	
	public VarList(HashSet<IdExpr> vars){
		this.variables = vars;
	}
	
	public boolean add(IdExpr newVar){
		return this.variables.add(newVar);
	}
	
	// Revisar, talvez não faça sentido!
	public boolean contains(IdExpr newVar){
		return this.variables.contains(newVar);
	}
	
	public void genC(int tabs){
		if (! this.variables.isEmpty()){
			for (int i = 0; i < tabs; i++)
				System.out.print("\tint ");
			
			Object[] temp;
			temp = this.variables.toArray();
			
			for (int i = 0; i < temp.length; i++){
				IdExpr tmpId = (IdExpr)temp[i];
				tmpId.genC(tabs);
			}
			System.out.println(";");
		}
	}
	
	public int size(){
		return this.variables.size();
	}
}
