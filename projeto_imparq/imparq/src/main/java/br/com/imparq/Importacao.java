package br.com.imparq;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import br.com.imparq.dto.RetornoDTO;
import br.com.imparq.service.ImportacaoService;
import br.com.imparq.validacao.Validacao;

public class Importacao extends Validacao{
	
	private final static String HOMEPATH = System.getProperty("user.home");
	
	private final static String DIRETORIO_ENTRADA = HOMEPATH + "/data/in/";
	
	private final static String DIRETORIO_SAIDA = HOMEPATH + "/data/out/";
	
	public static ImportacaoService service = new ImportacaoService();
	
	public static void main(String[] args) throws IOException, InterruptedException {	
		
		RetornoDTO retorno = new RetornoDTO();
		
		System.out.println("Arquivos de Entrada: " + DIRETORIO_ENTRADA);
		System.out.println("Arquivos de Saída: " + DIRETORIO_SAIDA);
		
		System.out.println("ATENCAO: a leitura de novos arquivos é realizada a cada 15 segundos");
		
		while (true) {
			
			//deley para leitura de novos arquivos no diretório de entrada
			TimeUnit.SECONDS.sleep(15);
			
			//ler arquivos novos no diretorio de entrada
			if(Files.exists(Paths.get(DIRETORIO_ENTRADA)))  
				lerArquivo(DIRETORIO_ENTRADA, retorno);
			else 
				return;	
		}
		
	}

	@SuppressWarnings("resource")
	public static void lerArquivo(String diretorio, RetornoDTO retornoDto) throws IOException{
				
		Path path = Paths.get(diretorio);
		Stream<Path> listaArquivo = Files.walk(path);

		validarDiretorioSaida(DIRETORIO_SAIDA);
		
		listaArquivo.forEach(arquivo->{
			try {
				service.inicializaRetornoDto(retornoDto);
				
				String pathArquivoSaida = DIRETORIO_SAIDA + arquivo.getFileName().toString().replace(".dat", ".done.dat");
				
				//Valida se o arquivo já foi processado
				if (arquivoValido(arquivo) && !arquivoProcessado(pathArquivoSaida)){
					
					System.out.println("Processando arquivo " + arquivo.getFileName());
					
					//processa as linhas de cada arquivo
					Files.lines(Paths.get(arquivo.toUri())).forEach(registro -> {
						
							//leitura das linhas do arquivo e divisão dos campos por entidade
							service.processarRegistro(registro, retornoDto);
	
						});
					
					LocalDateTime dataGeracao = LocalDateTime.now();
					List<String> linhas = new ArrayList<>();
					
					//data da geração do arquivo de saída
					linhas.add(dataGeracao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
					//quantidade de clientes processados no arquivo
					linhas.add(service.getQuantidadeClientes(retornoDto));
					//quantidade de vendedores processados no arquivo
					linhas.add(service.getQuantidadeVendedor(retornoDto));
					//retorna a venda com maior valor total
					linhas.add(service.getVendaMaisCara(retornoDto));
					//retorna o pior vendedor de acordo com as vendas processadas
					linhas.add(service.getPiorVendedor(retornoDto));
					
					//grava o arquivo de saída com o relatorio do arquivo de entrada
					gerarArquivo(pathArquivoSaida, linhas);
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}});
	
	}
	
	public static void gerarArquivo(String diretorio, List<String> linhas) throws IOException{
		
		Path path = Paths.get(diretorio);
		
		System.out.println("Gravando arquivo de saída " + path.getFileName());
		
		Files.createFile(path);
		Files.write(path, linhas);
		
		System.out.println("concluído.");
		
	}	
	
}
