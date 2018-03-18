package br.com.drogaria.dao;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Pessoa;
import br.com.drogaria.domain.Usuario;
import br.com.drogaria.enumeracao.TipoUsuario;

public class UsuarioDAOTest {

	@Test
	@Ignore
	public void salvar() {
		Long codigoPessoa = 6L;

		PessoaDAO pessoaDAO = new PessoaDAO();
		Pessoa pessoa = pessoaDAO.buscar(codigoPessoa);

		Usuario usuario = new Usuario();
		usuario.setSenhaSemCriptografia("abc");

		SimpleHash hash = new SimpleHash("sha-256", usuario.getSenhaSemCriptografia());
		usuario.setAtivo(true);
		usuario.setPessoa(pessoa);
		usuario.setSenha(hash.toHex());
		usuario.setTipoUsuario(TipoUsuario.ADMINISTRADOR);

		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.salvar(usuario);
	}
}
