package br.com.imparq.model;

import java.math.BigDecimal;
import java.util.List;

public class Venda {

	private Long id;
	private Vendedor vendedor;
	private List<VendaItem> listaVendaItem;
	private Double valorTotal;
	
	public Venda(Long id, Vendedor vendedor, List<VendaItem> listaVendaItem) {
		super();
		this.id = id;
		this.vendedor = vendedor;
		this.listaVendaItem = listaVendaItem;
		this.valorTotal = calcularValorTotal();
	}
	public Double calcularValorTotal() {
		Double valorTotal = 0D;
		
		BigDecimal b = new BigDecimal(0);
		for(VendaItem vendaItem : getListaVendaItem()){
			valorTotal += (vendaItem.getValor() * vendaItem.getQuantidade());
		}
		return valorTotal;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Vendedor getVendedor() {
		return vendedor;
	}
	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}
	public List<VendaItem> getListaVendaItem() {
		return listaVendaItem;
	}
	public void setListaVendaItem(List<VendaItem> listaVendaItem) {
		this.listaVendaItem = listaVendaItem;
	}
	public Double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	@Override
	public String toString() {
		return "ID: " + id + " Vendedor:" + vendedor.getNome() + " Total: "
				+ valorTotal;
	}
	
}
