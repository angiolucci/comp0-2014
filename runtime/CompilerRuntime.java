package runtime;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CompilerRuntime {
	/**************************************************************************
	 * openFile()
	 */
	public static char[] openFile(String fileName) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo " + fileName + " não encontrado!");
			System.exit(-1);
		}
		char[] fileContent = null;
		String lineReaden = new String();
		String aux = new String();
		try {
			while ((lineReaden = br.readLine()) != null) {
				aux += '\n' + lineReaden;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fileContent = aux.toCharArray();

		// input = fileContent;
		return fileContent;
	}
	
	/**************************************************************************
	 * error()
	 */
	public static void error(String message, int lineCount) {
		System.out.println("**********************************");
		System.out.println("ERRO na linha " + lineCount + ": ");
		System.out.println(message);
		System.exit(-1);
	}
}
