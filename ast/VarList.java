package ast;

import java.util.Collection;
import java.util.HashMap;

public class VarList {
	private HashMap<String, IdExpr> variables;
	
	public VarList(){
		this.variables = new HashMap<String, IdExpr>();
	}
	
	public VarList(HashMap<String, IdExpr> vars){
		this.variables = vars;
	}
	
	public IdExpr add(IdExpr newVar){
		String varName = newVar.getName();
		IdExpr idExpr = new IdExpr(varName);
		
		return this.variables.put(varName, idExpr);
		
		
	}
	
	public IdExpr add(String nameVar){
		return this.add(new IdExpr(nameVar));
	}
	
	
	public void genC(int tabs){
		if (! this.variables.isEmpty()){
			for (int i = 0; i < tabs; i++)
				System.out.print("\t");
			
			System.out.print("int ");
			
			Object[] temp;
			Collection<IdExpr> temp2 =  this.variables.values();
			temp = temp2.toArray();
			
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
