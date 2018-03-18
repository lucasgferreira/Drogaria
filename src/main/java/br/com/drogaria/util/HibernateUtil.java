package br.com.drogaria.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.ReturningWork;

public class HibernateUtil {

	private static SessionFactory sessionFactory = createSessionFactory();

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Connection getConexao() {
		Session session = sessionFactory.openSession();

		Connection con = session.doReturningWork(new ReturningWork<Connection>() {
			@Override
			public Connection execute(Connection connection) throws SQLException {
				// TODO Auto-generated method stub
				return connection;
			}
		});

		return con;
	}

	private static SessionFactory createSessionFactory() {
		try {

			Configuration configuration = new Configuration().configure();
			// ServiceRegistry registry = new
			// StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

			// SessionFactory factory = configuration.buildSessionFactory(registry);
			SessionFactory factory = configuration.buildSessionFactory();
			return factory;

		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("A fábrica de sessões não pode ser criada." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
}
