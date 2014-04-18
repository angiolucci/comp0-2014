import runtime.Runtime;

public class Main {
	public static void main(String[] args) {
		Compiler comp = null;
		char[] input = null;

		try {
			input = Runtime.openFile(args[0]);
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.out
					.println("Especifique o caminho completo para o código fonte.");
			System.exit(-1);
		}

		comp = new Compiler(input);
		comp.compile();

	}
}
