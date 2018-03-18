package br.com.drogaria.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import br.com.drogaria.domain.Cidade;
import br.com.drogaria.util.HibernateUtil;

public class CidadeDAO extends GenericDAO<Cidade> {

	public List<Cidade> buscarPorEstado(Long estadoCodigo) {

		// Open Session
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {

			// Get Criteria Builder
			CriteriaBuilder builder = session.getCriteriaBuilder();

			// Create Criteria
			CriteriaQuery<Cidade> criteria = builder.createQuery(Cidade.class);
			Root<Cidade> cidadeRoot = criteria.from(Cidade.class);
			//Root<Estado> estadoRoot = criteria.from(Estado.class);
			
			criteria.select(cidadeRoot)
			.where(builder.equal(cidadeRoot.get("estado"), estadoCodigo))
					.orderBy(builder.asc(cidadeRoot.get("Nome")));

			// Use criteria to query with session to fetch all contacts
			List<Cidade> cidades = session.createQuery(criteria).getResultList();

			return cidades;
		} catch (RuntimeException erro) {

			throw erro;

		} finally {
			session.close();
		}
	}
}
