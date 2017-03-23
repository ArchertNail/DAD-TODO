package dad.todo.services.jpa.utils;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import dad.todo.services.items.EventoItem;
import dad.todo.services.jpa.entities.EventoEntity;

public class CrudDAO<T> extends DAO {

	protected List<T> query(Class<T> entityClass, String queryString, Object ... parameters) throws PersistenceException {
		return execute(new DAOCommand<List<T>>() {
			@Override
			public List<T> doAction(EntityManager entityManager) throws PersistenceException {
				TypedQuery<T> query = entityManager.createQuery(queryString, entityClass);
				int i = 1;
				for (Object param : parameters) {
					query.setParameter(i++, param);	
				}
				return query.getResultList();
			}
		});
	}

	protected T findById(Class<T> entityClass, long id) {
		return execute(new DAOCommand<T>() {
			@Override
			public T doAction(EntityManager entityManager) throws PersistenceException {
				return entityManager.find(entityClass, id);
			}
		});
	}

	public void create(T entity) {
		executeInTransaction(new DAOCommand<Void>() {
			@Override
			public Void doAction(EntityManager entityManager) throws PersistenceException {
		
				entityManager.persist(entity);
				return null;
			}
		});
	}

	public void delete(Class<T> entityClass, long id) {
		executeInTransaction(new DAOCommand<Void>() {
			@Override
			public Void doAction(EntityManager entityManager) throws PersistenceException {
				T entity = entityManager.find(entityClass, id);
				entityManager.remove(entity);
				return null;
			}
		});
	}

	public void update(T entity) {
		executeInTransaction(new DAOCommand<Void>() {
			@Override
			public Void doAction(EntityManager entityManager) throws PersistenceException {
				entityManager.merge(entity);
				return null;
			}
		});
	}

}
