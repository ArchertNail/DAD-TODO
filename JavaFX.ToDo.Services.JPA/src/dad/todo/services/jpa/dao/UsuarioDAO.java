package dad.todo.services.jpa.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import dad.todo.services.ServiceException;
import dad.todo.services.jpa.entities.PerfilEntity;
import dad.todo.services.jpa.entities.UsuarioEntity;
import dad.todo.services.jpa.utils.DAO;
import dad.todo.services.jpa.utils.DAOCommand;
import dad.todo.services.jpa.utils.JPAUtil;

public class UsuarioDAO extends DAO {

	public UsuarioEntity findByCredentials(String username, String password) {
		UsuarioEntity usuario = execute(new DAOCommand<UsuarioEntity>() {
			@Override
			public UsuarioEntity doAction(EntityManager entityManager) throws PersistenceException {
				TypedQuery<UsuarioEntity> query = entityManager.createQuery("from UsuarioEntity u where u.username = :username and u.password = :password", UsuarioEntity.class);
				query.setParameter("username", username);
				query.setParameter("password", password);
				try {
					return query.getSingleResult();
				} catch (NoResultException e) {
					return null;
				}
			}
		});
		return usuario;
	}
	
	public UsuarioEntity findByUsername(String username) {
		UsuarioEntity usuario = execute(new DAOCommand<UsuarioEntity>() {
			@Override
			public UsuarioEntity doAction(EntityManager entityManager) throws PersistenceException {
				return entityManager.find(UsuarioEntity.class, username);
			}
		});
		return null;
	}

	public void create(UsuarioEntity usuario) {
		executeInTransaction(new DAOCommand<Void>() {
			@Override
			public Void doAction(EntityManager entityManager) throws PersistenceException {
				if(findByUsername(usuario.getUsername()) == null){
					entityManager.persist(usuario);
				}
				else{
					System.out.println("ya existe");
					return null;
				}
				return null;
			}
		});
	}

	public void delete(String username) {
		executeInTransaction(new DAOCommand<Void>() {
			@Override
			public Void doAction(EntityManager entityManager) throws PersistenceException {
				UsuarioEntity local = entityManager.find(UsuarioEntity.class, username);
				entityManager.remove(local);
				return null;
			}
		});	
	}

	public void update(UsuarioEntity usuario) {
		executeInTransaction(new DAOCommand<Void>() {
			@Override
			public Void doAction(EntityManager entityManager) throws PersistenceException {
				entityManager.merge(usuario);
				return null;
			}
		});
	}

	public UsuarioEntity findByEmail(String email) {
		
		
		UsuarioEntity user;
		EntityManager em= JPAUtil.getEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<UsuarioEntity>  query= em.createQuery("from UsuarioEntity u where u.perfil.email = :correo ", UsuarioEntity.class);
			query.setParameter("correo", email);
			user = query.getSingleResult();
		}
		catch ( NoResultException e) {
			return null;
		}
		
		em.close();
		return user;
		
	}
	
}
