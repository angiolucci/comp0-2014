
public class Main {
	public static void main(String[] args) {
		Compiler comp = new Compiler();
                char[] input = null;
                
                try {
                    input = comp.openFile(args[0]);
                }catch(ArrayIndexOutOfBoundsException ex){
                    System.out.println("Especifique o caminho completo para o código fonte.");
                    System.exit(-1);
                }
		
		comp.compile(input);
		
	}
}
