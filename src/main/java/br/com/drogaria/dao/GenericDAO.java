package br.com.drogaria.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.drogaria.util.HibernateUtil;

public class GenericDAO<Entidade> {

	private Class<Entidade> classe;

	@SuppressWarnings("unchecked")
	public GenericDAO() {
		this.classe = (Class<Entidade>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public void salvar(Entidade entidade) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {

			transaction = session.beginTransaction();
			session.save(entidade);
			transaction.commit();

		} catch (RuntimeException erro) {

			if (transaction != null) {
				transaction.rollback();
			}
			throw erro;

		} finally {
			session.close();
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Entidade> listar() {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {

			Criteria consulta = session.createCriteria(classe);
			List<Entidade> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException erro) {

			throw erro;

		} finally {
			session.close();
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Entidade> listar(String campoOrdenacao) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {

			Criteria consulta = session.createCriteria(classe);
			consulta.addOrder(Order.asc(campoOrdenacao));
			List<Entidade> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException erro) {

			throw erro;

		} finally {
			session.close();
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public Entidade buscar(Long codigo) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {

			Criteria consulta = session.createCriteria(classe);
			consulta.add(Restrictions.idEq(codigo));
			Entidade resultado = (Entidade) consulta.uniqueResult();
			return resultado;

		} catch (RuntimeException erro) {

			throw erro;

		} finally {
			session.close();
		}
	}

	public void excluir(Entidade entidade) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {

			transaction = session.beginTransaction();
			session.delete(entidade);
			transaction.commit();

		} catch (RuntimeException erro) {

			if (transaction != null) {
				transaction.rollback();
			}
			throw erro;

		} finally {
			session.close();
		}
	}

	public void editar(Entidade entidade) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {

			transaction = session.beginTransaction();
			session.update(entidade);
			transaction.commit();

		} catch (RuntimeException erro) {

			if (transaction != null) {
				transaction.rollback();
			}
			throw erro;

		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public Entidade merge(Entidade entidade) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {

			transaction = session.beginTransaction();
			Entidade retorno = (Entidade) session.merge(entidade);
			transaction.commit();
			return retorno;
		} catch (RuntimeException erro) {

			if (transaction != null) {
				transaction.rollback();
			}
			throw erro;

		} finally {
			session.close();
		}
	}
}
