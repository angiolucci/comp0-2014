import runtime.CompilerRuntime;

public class Comp {
	public static void main(String[] args) {
		Compiler comp = null;
		char[] input = null;

		try {
			input = CompilerRuntime.openFile(args[0]);
		} catch (ArrayIndexOutOfBoundsException ex) {
			String msgFileNotFound = "Especifique o caminho completo para o código fonte.";
			System.out.println(msgFileNotFound);
			System.exit(-1);
		}
		comp = new Compiler(input);
		String code = new String(comp.compile());
		
		CompilerRuntime.saveFile(args[0], code);

	}
}
