package br.com.drogaria.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.dao.EstadoDAO;
import br.com.drogaria.domain.Estado;

public class EstadoDAOTest {
	@Test
	@Ignore
	public void salvar() {
		Estado estado = new Estado();
		estado.setNome("Goiais");
		estado.setSigla("GO");

		EstadoDAO estadoDAO = new EstadoDAO();
		estadoDAO.salvar(estado);
	}

	@Test
	@Ignore
	public void listar() {
		EstadoDAO estadoDAO = new EstadoDAO();
		List<Estado> resultado = estadoDAO.listar();

		for (Estado estado : resultado) {
			System.out.println(estado.getSigla() + " - " + estado.getNome());
		}
	}

	@Test
	@Ignore
	public void buscar() {
		Long codigo = 2L;

		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscar(codigo);

		if (estado == null) {
			System.out.println("Nenhum Resultado!");
		} else {
			System.out.println(estado.getSigla() + " - " + estado.getNome());
		}
	}
	
	@Test
	@Ignore
	public void excluir() {
		Long codigo = 3L;

		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscar(codigo);
		
		if (estado == null) {
			System.out.println("Nenhum Resultado!");
		} else {
			estadoDAO.excluir(estado);
			System.out.println(estado.getCodigo() + " - " + estado.getSigla() + " - " + estado.getNome() + " Estado excluido");
		}
	}
	
	@Test
	@Ignore
	public void editar() {
		Long codigo = 2L;

		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscar(codigo);
		
		if (estado == null) {
			System.out.println("Nenhum Resultado!");
		} else {
			//estado.setNome("Test");
			estado.setSigla("GO");

			estadoDAO = new EstadoDAO();
			estadoDAO.editar(estado);
			System.out.println(estado.getCodigo() + " - " + estado.getSigla() + " - " + estado.getNome() + " Estado editado");
		}
	}
}
