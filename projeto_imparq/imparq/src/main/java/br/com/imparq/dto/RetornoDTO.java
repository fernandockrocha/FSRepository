package br.com.imparq.dto;

import java.util.List;

import br.com.imparq.model.Cliente;
import br.com.imparq.model.Venda;
import br.com.imparq.model.Vendedor;

public class RetornoDTO {

	private List<Vendedor> listaVendedor;
	private List<Cliente> listaCliente;
	private List<Venda> listaVenda;

	public List<Vendedor> getListaVendedor() {
		return listaVendedor;
	}

	public void setListaVendedor(List<Vendedor> listaVendedor) {
		this.listaVendedor = listaVendedor;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public List<Venda> getListaVenda() {
		return listaVenda;
	}

	public void setListaVenda(List<Venda> listaVenda) {
		this.listaVenda = listaVenda;
	}

}
