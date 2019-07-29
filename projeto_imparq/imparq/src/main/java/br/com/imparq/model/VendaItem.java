package br.com.imparq.model;

public class VendaItem {
	
	private Long id;
	private Long quantidade;
	private Double valor;
	
	public VendaItem(Long id, Long quantidade, Double valor) {
		super();
		this.id = id;
		this.quantidade = quantidade;
		this.valor = valor;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}

}
