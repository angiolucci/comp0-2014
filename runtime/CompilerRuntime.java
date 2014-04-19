package runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
			System.out.println("Falha ao tentar ler o arquivo:\n" + fileName);
			System.out.println("\nDetalhes do erro:\n" + e.getMessage());
			System.exit(-1);
		}
		fileContent = aux.toCharArray();

		return fileContent;
	}

	/**************************************************************************
	 * analysisError()
	 */
	public static void analysisError(String message, int lineCount) {
		System.out.println("**********************************");
		System.out.println("ERRO na linha " + lineCount + ": ");
		System.out.println(message);
		System.exit(-2);
	}

	/**************************************************************************
	 * saveFile()
	 */
	public static void saveFile(String filePath, String code) {

		String cCodeFilePath = new String(filePath + ".c");
		File cCodeFile = new File(cCodeFilePath);
		try (FileOutputStream fop = new FileOutputStream(cCodeFile)) {

			// if file doesn't exists, then create it
			if (!cCodeFile.exists()) {
				cCodeFile.createNewFile();
			}

			// get the content in bytes
			byte[] contentInBytes = code.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();
			// System.out.println("código-fonte gerado: " + cCodeFilePath);

		} catch (IOException e) {
			System.out.println("Erro ao salvar arquivo gerado:\n" + cCodeFilePath);
			System.out.print("Verifique se você possui os privilégios para escrita e se a unidade");
			System.out.println(" de disco possui espaço livre disponível para efetuar a gravação.");
			System.out.println("\nDetalhes do erro:\n" + e.getMessage());
			System.exit(-3);
		}
	}
}
