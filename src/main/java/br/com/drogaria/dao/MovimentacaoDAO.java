package br.com.drogaria.dao;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.drogaria.domain.Caixa;
import br.com.drogaria.domain.Movimentacao;
import br.com.drogaria.util.HibernateUtil;

public class MovimentacaoDAO extends GenericDAO<Movimentacao> {

	@SuppressWarnings("deprecation")
	public Caixa buscar(Date dataAbertura) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			Criteria consulta = session.createCriteria(Caixa.class);
			consulta.add(Restrictions.eq("dataAbertura", dataAbertura));
			Caixa reultado = (Caixa) consulta.uniqueResult();
			return reultado;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		} finally {
			session.close();
		}
	}
}
