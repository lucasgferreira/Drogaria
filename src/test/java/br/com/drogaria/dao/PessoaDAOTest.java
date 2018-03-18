package br.com.drogaria.dao;

import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Pessoa;

public class PessoaDAOTest {

	@Test
	@Ignore
	public void salvar() {
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("ana");
		pessoa.setCpf("987.432.947-81");
		pessoa.setRg("234165783614");
		pessoa.setRua("Rua 6");
		pessoa.setNumero(new Short("52"));
		pessoa.setBairro("Bairro5");
		pessoa.setCep("4327728341");
		pessoa.setComplemento(null);
		pessoa.setTelefone("(61)43253427");
		pessoa.setCelular("(61)456962789");
		pessoa.setEmail("ana@gmail.com");
		
		PessoaDAO pessoaDAO = new PessoaDAO();
		pessoaDAO.salvar(pessoa);
	}

}
