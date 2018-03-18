package br.com.drogaria.dao;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.drogaria.domain.Usuario;
import br.com.drogaria.util.HibernateUtil;

public class UsuarioDAO extends GenericDAO<Usuario> {
	@SuppressWarnings("deprecation")
	public Usuario autenticar(String cpf, String senha) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			Criteria consulta = session.createCriteria(Usuario.class);
			consulta.createAlias("pessoa", "p");
			consulta.add(Restrictions.eq("p.cpf", cpf));
			
			SimpleHash hash = new SimpleHash("sha-256", senha);
			consulta.add(Restrictions.eq("senha", hash.toHex()));
			
			Usuario usuario = (Usuario) consulta.uniqueResult();
			
			return usuario;
		}catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}finally {
			session.close();
		}
	}
}
