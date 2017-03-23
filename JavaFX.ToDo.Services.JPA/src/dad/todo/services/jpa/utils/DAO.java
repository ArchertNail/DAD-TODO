package dad.todo.services.jpa.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

public class DAO {
	
	public <T> T executeInTransaction(DAOCommand<T> command) throws PersistenceException {
		T result = null;
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;
        try {
        	transaction = entityManager.getTransaction();
        	transaction.begin();
        	result = command.doAction(entityManager);
        	transaction.commit();
        } catch (PersistenceException e) {
        	e.printStackTrace();
        	transaction.rollback();
        	throw e;
        } finally {
        	entityManager.close();
        }
        return result;
	}
	
	public <T> T execute(DAOCommand<T> command) throws PersistenceException {
		T result = null;
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
        	result = command.doAction(entityManager);
        } catch (PersistenceException e) {
        	e.printStackTrace();
        	throw e;
        } finally {
        	entityManager.close();
        }
        return result;
	}
	
}
