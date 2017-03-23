package dad.todo.services.jpa.dao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import dad.todo.services.ServiceException;
import dad.todo.services.ServiceFactory;
import dad.todo.services.jpa.entities.EventoEntity;
import dad.todo.services.jpa.entities.PerfilEntity;
import dad.todo.services.jpa.entities.UsuarioEntity;
import dad.todo.services.jpa.utils.CrudDAO;
import dad.todo.services.jpa.utils.JPAUtil;

public class EventoDAO extends CrudDAO<EventoEntity> {
	/*
	public List<PersonaEntity> find(String nombre, String apellidos) throws PersistenceException {
		if (nombre == null) nombre = "";
		if (apellidos == null) apellidos = "";
        return query(PersonaEntity.class, 
        		"from PersonaEntity p " +
        		"where (p.nombre like ?1) and (p.apellidos like ?2) " +
        		"order by p.nombre", 
        		"%" + nombre + "%", "%" + apellidos + "%"
        	);
	}*/
	
	
	public List<EventoEntity> getAll() throws PersistenceException {
		
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		
		
		try {
			UsuarioEntity user = em.find(UsuarioEntity.class, ServiceFactory.getLoginService().getLogueado().getUsername());
			
	
			Query query = em.createQuery("from EventoEntity e where e.usuario=:usuario");
			query.setParameter("usuario", user);
			
			List<EventoEntity> listaEventos = query.getResultList();
				
			return listaEventos;
			
			
		} catch (ServiceException e) {

			e.printStackTrace();
			return null;
		}
		
        //return query(EventoEntity.class, "from EventoEntity e where e.username = " + username + " order by e.titulo");
	}

	public EventoEntity findById(long id) {
		return findById(EventoEntity.class, id);
	}
	
	public void delete(long id) {
		delete(EventoEntity.class, id);
	}

	public List<EventoEntity> findByDate(Date fecha) {
		
		
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(fecha);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			Date fecha1 = cal.getTime();
			
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			Date fecha2 = cal.getTime();
			
			UsuarioEntity user = em.find(UsuarioEntity.class, ServiceFactory.getLoginService().getLogueado().getUsername());
			
			Query query = em.createQuery("from EventoEntity e where e.fecha BETWEEN :fecha2 and :fecha1 and e.usuario=:usuario");
			query.setParameter("fecha2", fecha2);
			query.setParameter("fecha1", fecha1);
			query.setParameter("usuario",user);
				
			List<EventoEntity> listaEventos = query.getResultList();
				
			return listaEventos;
			
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	
//		return query(EventoEntity.class, 
//        		"from EventoEntity e " +
//        		"where (e.fecha like ?1)", 
//        		"%" + fecha + "%"
//        	);
	}
//	
//	@Override
//	public void update(EventoEntity entity) {
//		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
//		em.merge(entity.getLugar());
//		super.update(entity);
//		em.close();
//	}
//	
	

}
