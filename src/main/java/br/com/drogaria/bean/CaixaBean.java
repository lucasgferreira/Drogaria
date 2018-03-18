package br.com.drogaria.bean;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import br.com.drogaria.dao.CaixaDAO;
import br.com.drogaria.dao.FuncionarioDAO;
import br.com.drogaria.domain.Caixa;
import br.com.drogaria.domain.Funcionario;

@ManagedBean
@ViewScoped
public class CaixaBean {

	private Caixa caixa;
	private ScheduleModel caixas;
	private List<Funcionario> funcionarios;

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public ScheduleModel getCaixas() {
		return caixas;
	}

	public void setCaixas(ScheduleModel caixas) {
		this.caixas = caixas;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	@PostConstruct
	public void listar() {
		caixas = new DefaultScheduleModel();
	}
	
	public void novo(SelectEvent event) {
		caixa = new Caixa();
		caixa.setDataAbertura((Date) event.getObject());
		
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		funcionarios = funcionarioDAO.listar();
		
	}
	
	public void salvar() {
		CaixaDAO caixaDAO = new CaixaDAO();
		caixaDAO.salvar(caixa);
		Messages.addGlobalInfo("Caixa aberto com sucesso.");
	}
}
