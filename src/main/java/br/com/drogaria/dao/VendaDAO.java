package br.com.drogaria.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.drogaria.domain.ItemVenda;
import br.com.drogaria.domain.Produto;
import br.com.drogaria.domain.Venda;
import br.com.drogaria.util.HibernateUtil;

public class VendaDAO extends GenericDAO<Venda> {

	public void save(Venda venda, List<ItemVenda> itemVendas) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {

			transaction = session.beginTransaction();

			session.save(venda);

			for (int posicao = 0; posicao < itemVendas.size(); posicao++) {
				ItemVenda itemVenda = itemVendas.get(posicao);
				itemVenda.setVenda(venda);

				session.save(itemVenda);
				Produto produto = itemVenda.getProduto();
				int quantidade = produto.getQuantidade() - itemVenda.getQuantidade();
				if (quantidade >= 0) {
					produto.setQuantidade(new Short(quantidade + ""));

					session.update(produto);
				} else {
					throw new RuntimeException("A quantidade de produtos é insuficiente em estoque!");
				}

			}

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
}
