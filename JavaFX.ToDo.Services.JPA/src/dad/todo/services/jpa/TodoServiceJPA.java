package dad.todo.services.jpa;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import dad.todo.services.LoginService;
import dad.todo.services.ServiceException;
import dad.todo.services.ServiceFactory;
import dad.todo.services.TodoService;
import dad.todo.services.items.EventoItem;
import dad.todo.services.items.UsuarioItem;
import dad.todo.services.jpa.dao.DAOFactory;
import dad.todo.services.jpa.entities.EventoEntity;
import dad.todo.services.jpa.entities.LugarEntity;
import dad.todo.services.jpa.entities.UsuarioEntity;
import dad.todo.services.jpa.utils.DAO;
import dad.todo.services.jpa.utils.JPAUtil;

public class TodoServiceJPA implements TodoService{

	private LoginService usuarioService;
	
	public TodoServiceJPA() {
		usuarioService = ServiceFactory.getLoginService();
	}
	
	@Override
	public EventoItem getEvento(long id) throws ServiceException {
		
		try {
			EventoEntity entity = DAOFactory.getEventoDAO().findById(id);
			EventoItem item = (entity != null) ? entity.toItem() : null;
			return item;
		} catch (Exception e) {
			throw new ServiceException("Error al recuperar el evento con id: '" + id + "'", e);
		}
	}

	@Override
	public void addEvento(EventoItem evento) throws ServiceException {
		
		try {
			//UsuarioItem usuario = ServiceFactory.getLoginService().getLogueado();
			
			if(usuarioService.getLogueado()!=null){
				EventoEntity entity = EventoEntity.fromItem(evento);
				entity.setUsuario(UsuarioEntity.fromItem(usuarioService.getLogueado()));
				DAOFactory.getEventoDAO().create(entity);
			}
			else{
				System.out.println("Debes estar logeado");
			}
			//evento.setId(entity.getId());
			
		} catch (Exception e) {
			throw new ServiceException("Error al añadir el evento: [" + evento + "] al usuario", e);
		}
	}

	@Override
	public void deleteEvento(Long id) throws ServiceException {
		
		UsuarioItem item = ServiceFactory.getLoginService().getLogueado();
		if(item==null){
			System.out.println("No se ha registrado usuario");
		}
		else{
			try {
				DAOFactory.getEventoDAO().delete(id);
			} catch (Exception e) {
				throw new ServiceException("Error al eliminar el evento: [" + id + "] del usuario", e);
			}
		}
		
	}

	@Override
	public void updateEvento(EventoItem evento) throws ServiceException {
			
		try {
			EventoEntity entity = DAOFactory.getEventoDAO().findById(evento.getId());
			entity = EventoEntity.fromItem(entity, evento);
			DAOFactory.getEventoDAO().update(entity);
		} catch (Exception e) {
			throw new ServiceException("Error al actualizar el evento: [" + evento+ "] del usuario", e);
		}
		
	}

	@Override
	public List<EventoItem> getEventos() throws ServiceException {
	
			
		if(usuarioService.getLogueado()!=null){
			
			List<EventoItem> eventoItem = new ArrayList<>();
			try{
				List<EventoEntity> eventos = DAOFactory.getEventoDAO().getAll();
				
				eventoItem.addAll(eventos.stream().map(EventoEntity::toItem).collect(Collectors.toList()));
				
			}catch(Exception e){
				throw new ServiceException("Error al recuperar eventos");
			}
			
			return eventoItem;
		}
		else{
			throw new ServiceException("Debes logearte");
		}
			
	}

	@Override
	public List<EventoItem> buscarEventoPorFecha(Date fecha) throws ServiceException {
		
		if(usuarioService.getLogueado() != null){
			
			List<EventoEntity> eventoEntityList = DAOFactory.getEventoDAO().findByDate(fecha);
			
			List<EventoItem> eventos = new ArrayList<>();
			
			eventos.addAll(eventoEntityList.stream().map(EventoEntity::toItem).collect(Collectors.toList()));
			
			return eventos;
			
		}
		else{
			throw new ServiceException("Debes logearte");
		}

	}

	
}
