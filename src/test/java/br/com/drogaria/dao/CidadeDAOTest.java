package br.com.drogaria.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Cidade;
import br.com.drogaria.domain.Estado;

public class CidadeDAOTest {
	@Test
	@Ignore
	public void salvar() {
		Long codigo = 11L;

		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscar(codigo);

		Cidade cidade = new Cidade();
		cidade.setNome("Taguatinga");
		cidade.setEstado(estado);

		CidadeDAO cidadeDAO = new CidadeDAO();
		cidadeDAO.salvar(cidade);
	}

	@Test
	@Ignore
	public void listar() {
		CidadeDAO cidadeDAO = new CidadeDAO();
		List<Cidade> resultado = cidadeDAO.listar();

		for (Cidade cidade : resultado) {
			System.out.println("Código: " + cidade.getCodigo());
			System.out.println("Nome: " + cidade.getNome());
			System.out.println("Código Estado: " + cidade.getEstado().getCodigo());
			System.out.println("Sigla Estado: " + cidade.getEstado().getSigla());
			System.out.println("Nome Estado: " + cidade.getEstado().getNome());
			System.out.println();
		}
	}

	@Test
	@Ignore
	public void buscar() {
		Long codigo = 1L;

		CidadeDAO cidadeDAO = new CidadeDAO();
		Cidade cidade = cidadeDAO.buscar(codigo);

		System.out.println("Código: " + cidade.getCodigo());
		System.out.println("Nome: " + cidade.getNome());
		System.out.println("Código Estado: " + cidade.getEstado().getCodigo());
		System.out.println("Sigla Estado: " + cidade.getEstado().getSigla());
		System.out.println("Nome Estado: " + cidade.getEstado().getNome());
		System.out.println();

	}

	@Test
	@Ignore
	public void excluir() {
		Long codigo = 2L;

		CidadeDAO cidadeDAO = new CidadeDAO();
		Cidade cidade = cidadeDAO.buscar(codigo);

		cidadeDAO.excluir(cidade);
	}

	@Test
	@Ignore
	public void editar() {
		Long codigo = 3L;
		Long estadoCodigo = 11L;
		
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscar(estadoCodigo);

		CidadeDAO cidadeDAO = new CidadeDAO();
		Cidade cidade = cidadeDAO.buscar(codigo);
		
		cidade.setNome("Brazlândia");
		cidade.setEstado(estado);
		cidadeDAO.editar(cidade);
	}
	
	@Test
	@Ignore
	public void buscarPorEstado() {
		Long estadoCodigo = 1L;
		
		CidadeDAO cidadeDAO = new CidadeDAO();
		List<Cidade> resultado = cidadeDAO.buscarPorEstado(estadoCodigo);
		
		for (Cidade cidade : resultado) {
			System.out.println("Código: " + cidade.getCodigo());
			System.out.println("Nome: " + cidade.getNome());
			System.out.println("Código Estado: " + cidade.getEstado().getCodigo());
			System.out.println("Sigla Estado: " + cidade.getEstado().getSigla());
			System.out.println("Nome Estado: " + cidade.getEstado().getNome());
			System.out.println();
		}
	}
}
