package ast;

import java.util.ArrayList;

public class VarList {
	private ArrayList<IdExpr>  variables;
	
	public VarList(){
		this.variables = new ArrayList<IdExpr>();
	}
	
	public VarList(ArrayList<IdExpr> vars){
		this.variables = vars;
	}
	
	public boolean add(IdExpr newVar){
		String varName = newVar.getName();
		IdExpr idExpr = new IdExpr(newVar.getName());
		boolean contains = false;
		
		for (int i = 0; i < this.variables.size() && contains == false; i++)
			if (this.variables.get(i).getName().compareTo(varName) == 0)
				contains = true;
		
		if (!contains)
			this.variables.add(idExpr);
		
		return contains;
		
	}
	
	public boolean add(String nameVar){
		return this.add(new IdExpr(nameVar));
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
				
				if (i != temp.length - 1)
					System.out.print(',');
			}
			System.out.println(";");
		}
	}
	
	public int size(){
		return this.variables.size();
	}
}
