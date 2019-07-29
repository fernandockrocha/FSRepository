package br.com.imparq.model;

public class Cliente {
	
	private String id;
	
	private String nome;
	
	private String ramoAtividade;
	
	public Cliente(String id, String nome, String ramoAtividade) {
		this.id = id;
		this.nome = nome;
		this.ramoAtividade = ramoAtividade;
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

	public String getRamoAtividade() {
		return ramoAtividade;
	}

	public void setRamoAtividade(String ramoAtividade) {
		this.ramoAtividade = ramoAtividade;
	}
	
}
