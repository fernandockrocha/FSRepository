package br.com.imparq.validacao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Validacao {

	//verifica se o arquivo existente na pasta � v�lido com a extens�o ".dat"
	public static boolean arquivoValido(Path arquivo){
		return arquivo.toString().contains(".dat");
	}
	
	//verifica se o arquivo existente na pasta j� foi processado na pasta de arquivos de sa�da
	public static boolean arquivoProcessado(String path){
		return (Files.exists(Paths.get(path)));
	}
	
	//verifica se o diret�rio de sa�da dos arquivos processos existe. Se n�o existe, cria
	public static void validarDiretorioSaida(String path) throws IOException{
		if (!Files.exists(Paths.get(path))) Files.createDirectory(Paths.get(path));
	}
}
