package dad.todo.services.jpa.utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

public interface DAOCommand<T> {

	public T doAction(EntityManager entityManager) throws PersistenceException;
	
}
