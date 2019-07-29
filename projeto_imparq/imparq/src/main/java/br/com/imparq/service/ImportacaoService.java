package br.com.imparq.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import br.com.imparq.dto.RetornoDTO;
import br.com.imparq.model.Cliente;
import br.com.imparq.model.Venda;
import br.com.imparq.model.VendaItem;
import br.com.imparq.model.Vendedor;

public class ImportacaoService {
	
	public void inicializaRetornoDto( RetornoDTO retornoDto) {	
		retornoDto.setListaVendedor(new ArrayList<Vendedor>());
		retornoDto.setListaCliente(new ArrayList<Cliente>());
		retornoDto.setListaVenda(new ArrayList<Venda>());
	}

	public void processarRegistro(String registro, RetornoDTO retornoDto){
		
		System.out.println(registro);
		
		//separa os valores da linha do arquivo pelo delimitador "ç"
		String[] campos = registro.split("ç");
		
		if (campos[0].equals("001")){
			
			//cria objeto de vendedor
			retornoDto.getListaVendedor().add(new Vendedor(campos[1], campos[2], Double.valueOf(campos[3])));
    			
		}else if (campos[0].equals("002")){
			
			//cria objeto de cliente 
			retornoDto.getListaCliente().add(new Cliente(campos[1], campos[2], campos[3]));
			
		}else if (campos[0].equals("003")){
			
			//separa os valores dos itens da venda contido entre "[""]" delimitados po ","
			String[] listaItensVenda = campos[2].replace("[","").replace("]","").split(",");
				
			List<VendaItem> listaVendaItem = new ArrayList<VendaItem>();
			
			//percorre os itens da venda...
			for (int i = 0; i < listaItensVenda.length; i++){
				//...separando os campos de cada item delimitados por "-"
				String[] camposVendaItem = listaItensVenda[i].split("-");
				//cria objeto de item da venda
				listaVendaItem.add(new VendaItem(Long.valueOf(camposVendaItem[0]), Long.valueOf(camposVendaItem[1]), Double.valueOf(camposVendaItem[2])));
			}
			
			//cria objeto de venda
			retornoDto.getListaVenda().add(new Venda(Long.valueOf(campos[1]), buscarVendedor(retornoDto.getListaVendedor(), campos[3]), listaVendaItem));
			
		}else{
			
			return;
			
		}		
		
	}
	
	//retorna a quantidade de clientes processados
	public String getQuantidadeClientes(RetornoDTO retornoDto){
		return "Quantidade de Clientes: " + retornoDto.getListaCliente().size();
	}
	
	//retorna a quantidade de vendedores processados
	public String getQuantidadeVendedor(RetornoDTO retornoDto){
		return "Quantidade de Vendedores: " + retornoDto.getListaVendedor().size();
	}
	
	//retorna a venda mais cara processada
	public String getVendaMaisCara(RetornoDTO retorno){
		
		Venda venda = buscarVendaMaisCara(retorno);
		
		return "Venda mais cara: " + venda.toString();
	}
	
	//retorna o pior vendedor processado
	public String getPiorVendedor(RetornoDTO retorno) {
		Vendedor vendedor = buscarPiorVendedor(retorno);
		
		return "Pior Vendedor: " + vendedor.getNome() ;
		
	}
	
	//busca venda mais cara
	public Venda buscarVendaMaisCara(RetornoDTO retorno) {
		//percorre a lista de vendas identificando a que possui o maior valor total
		Double maiorValor = retorno.getListaVenda().stream().mapToDouble(venda -> venda.getValorTotal()).max().getAsDouble();
		return retorno.getListaVenda().stream().filter(venda -> venda.getValorTotal().equals(maiorValor)).findFirst().get();
	}
	
	public Vendedor buscarPiorVendedor(RetornoDTO retorno){
		
		//objeto temporario para armazenas os vendedores processados e o valor total das vendas do mesmo
		class ValorVendedor{
			Vendedor vendedor;
			Double valor;
			public ValorVendedor(Vendedor vendedor, Double valor) {
				super();
				this.vendedor = vendedor;
				this.valor = valor;
			}
		}
		
		List<ValorVendedor> listaValorVendedor = new ArrayList<>();
		
		//percorre as vendas processadas...
		for (Venda venda : retorno.getListaVenda()){
			if (listaValorVendedor.isEmpty()){
				//inicializa a lista com o primeiro valorVendedor criando objeto com nome do vendedor e o valor da venda
				listaValorVendedor.add(new ValorVendedor(venda.getVendedor(), venda.getValorTotal()));
			}else{
				//se o vendedor ja consta na listaValorVendedor acumula o valor de venda
				ValorVendedor valorVendedor = listaValorVendedor.stream().filter(v -> v.vendedor.equals(venda.getVendedor())).findFirst().orElse(null);
				if (valorVendedor != null){ 
					valorVendedor.valor += venda.getValorTotal(); 
				}else{
					//se o vendedor nao consta na listaValorVendedor adiciona novo objeto com nome do vendedor e o valor da venda
					listaValorVendedor.add(new ValorVendedor(venda.getVendedor(), venda.getValorTotal()));
				}
			}
		}
		
		//busca na listaValorVendedor o menor valor acumulado...
		Double menorValor = listaValorVendedor.stream().mapToDouble(valorVendedor -> valorVendedor.valor).min().getAsDouble();
		
		//...retorna o pior vendedor
		return listaValorVendedor.stream().filter(valorVendedor -> valorVendedor.valor.equals(menorValor)).findFirst().get().vendedor;
	}	
	
	//busca o objeto vendedor pelo nome
	public Vendedor buscarVendedor(List<Vendedor> listaVendedor, String nome) {
		Optional<Vendedor> vendedor = listaVendedor.stream().filter(v -> v.getNome().equals(nome)).findFirst();
		return vendedor.get();
	}
	
}
