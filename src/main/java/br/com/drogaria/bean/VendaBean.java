package br.com.drogaria.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.ClienteDAO;
import br.com.drogaria.dao.FuncionarioDAO;
import br.com.drogaria.dao.ProdutoDAO;
import br.com.drogaria.dao.VendaDAO;
import br.com.drogaria.domain.Cliente;
import br.com.drogaria.domain.Funcionario;
import br.com.drogaria.domain.ItemVenda;
import br.com.drogaria.domain.Produto;
import br.com.drogaria.domain.Venda;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VendaBean implements Serializable {
	private Venda venda;
	private List<Produto> produtos;
	private List<ItemVenda> itemVendas;
	private List<Cliente> clientes;
	private List<Funcionario> funcionarios;
	private List<Venda> vendas;

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<ItemVenda> getItemVendas() {
		return itemVendas;
	}

	public void setItemVendas(List<ItemVenda> itemVendas) {
		this.itemVendas = itemVendas;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

	public void novo() {
		try {
			venda = new Venda();
			venda.setPrecoTotal(new BigDecimal("0.00"));

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar();

			itemVendas = new ArrayList<>();
		} catch (RuntimeException e) {
			// TODO: handle exception
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Produtos!");
			e.printStackTrace();
		}
	}

	public void listar() {
		VendaDAO vendaDAO = new VendaDAO();
		vendas = vendaDAO.listar("horario");
	}

	public void adicionar(ActionEvent event) {
		try {
			Produto produto = (Produto) event.getComponent().getAttributes().get("produtoSelecionado");

			int achou = -1;
			for (int posicao = 0; posicao < itemVendas.size(); posicao++) {
				if (itemVendas.get(posicao).getProduto().equals(produto)) {
					achou = posicao;
				}
			}

			if (achou < 0) {

				ItemVenda itemVenda = new ItemVenda();
				itemVenda.setPrecoParcial(produto.getPreco());
				itemVenda.setProduto(produto);
				itemVenda.setQuantidade(new Short("1"));
				itemVendas.add(itemVenda);
			} else {
				ItemVenda itemVenda = itemVendas.get(achou);
				itemVenda.setQuantidade(new Short(String.valueOf(itemVenda.getQuantidade() + 1)));
				itemVenda.setPrecoParcial(produto.getPreco().multiply(new BigDecimal(itemVenda.getQuantidade())));
			}

			calcular();
			// Messages.addGlobalInfo("Produto editado com sucesso.");
		} catch (RuntimeException e) {

			e.printStackTrace();
		}
	}

	public void remover(ActionEvent event) {
		try {
			ItemVenda itemVenda = (ItemVenda) event.getComponent().getAttributes().get("itemSelecionado");

			int achou = -1;
			for (int posicao = 0; posicao < itemVendas.size(); posicao++) {
				if (itemVendas.get(posicao).getProduto().equals(itemVenda.getProduto())) {
					achou = posicao;
				}
			}

			if (achou > -1) {

				itemVendas.remove(achou);
			}

			calcular();
			// Messages.addGlobalInfo("Produto editado com sucesso.");
		} catch (RuntimeException e) {

			e.printStackTrace();
		}
	}

	public void calcular() {
		venda.setPrecoTotal(new BigDecimal("0.00"));

		for (int posicao = 0; posicao < itemVendas.size(); posicao++) {
			ItemVenda itemVenda = itemVendas.get(posicao);

			venda.setPrecoTotal(venda.getPrecoTotal().add(itemVenda.getPrecoParcial()));
		}
	}

	public void finalizar() {
		try {
			venda.setHorario(new Date());

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listarOrdenado();

			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listarOrdenado();
		} catch (RuntimeException e) {
			// TODO: handle exception
			Messages.addGlobalError("Ocorreu um erro ao tentar finalizar a Venda!");
			e.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (venda.getPrecoTotal().signum() == 0) {
				Messages.addGlobalError("Informe pelo menos um produto para a Venda!");
				return;
			}

			VendaDAO vendaDAO = new VendaDAO();
			vendaDAO.save(venda, itemVendas);

			novo();
			Messages.addGlobalInfo("Venda realizada com sucesso!");
		} catch (RuntimeException e) {
			// TODO: handle exception
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Venda!");
			e.printStackTrace();
		}
	}
}
