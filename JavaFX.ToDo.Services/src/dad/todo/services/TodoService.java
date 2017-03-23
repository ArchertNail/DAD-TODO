package dad.todo.services;

import java.util.Date;
import java.util.List;
import dad.todo.services.items.EventoItem;

public interface TodoService {

	/*public List<PersonaListItem> findPersonas(String nombre, String apellidos) throws ServiceException;
	public List<PersonaListItem> getPersonas() throws ServiceException;
	public PersonaItem getPersona(long id) throws ServiceException;*/
	public EventoItem getEvento(long id) throws ServiceException;
	public void addEvento(EventoItem evento) throws ServiceException;
	public void deleteEvento(Long id) throws ServiceException;
	public void updateEvento(EventoItem evento) throws ServiceException;
	public List<EventoItem> getEventos() throws ServiceException;
	public List<EventoItem> buscarEventoPorFecha(Date fecha) throws ServiceException;
	
	
}
