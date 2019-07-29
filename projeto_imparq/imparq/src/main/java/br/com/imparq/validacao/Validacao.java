package br.com.imparq.validacao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Validacao {

	//verifica se o arquivo existente na pasta é válido com a extensão ".dat"
	public static boolean arquivoValido(Path arquivo){
		return arquivo.toString().contains(".dat");
	}
	
	//verifica se o arquivo existente na pasta já foi processado na pasta de arquivos de saída
	public static boolean arquivoProcessado(String path){
		return (Files.exists(Paths.get(path)));
	}
	
	//verifica se o diretório de saída dos arquivos processos existe. Se não existe, cria
	public static void validarDiretorioSaida(String path) throws IOException{
		if (!Files.exists(Paths.get(path))) Files.createDirectory(Paths.get(path));
	}
}
