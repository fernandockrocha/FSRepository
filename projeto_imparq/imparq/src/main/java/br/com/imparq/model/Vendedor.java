package br.com.imparq.model;

public class Vendedor {
	
	private String id;
	
	private String nome;
	
	private Double valor;
	
	public Vendedor(String id, String nome, Double valor) {
		this.id = id;
		this.nome = nome;
		this.valor = valor;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}	
}
