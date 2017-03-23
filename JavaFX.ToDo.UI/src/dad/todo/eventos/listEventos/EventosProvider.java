package dad.todo.eventos.listEventos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dad.todo.jasper.EventosJasper;
import dad.todo.services.ServiceException;
import dad.todo.services.ServiceFactory;
import dad.todo.services.items.EventoItem;

public class EventosProvider {

	public EventosProvider() {
		
	}
	
	public static List<EventosJasper> getEventos(){
		
		List<EventosJasper> listaEventosJasper = new ArrayList<>();
		
		
		List<EventoItem> listEventosItems;
		try {
			listEventosItems = ServiceFactory.getEventoService().getEventos();
			
			for (EventoItem item : listEventosItems) {
				EventosJasper eventJasp = EventosJasper.fromItem(item);
				listaEventosJasper.add(eventJasp);
			}
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listaEventosJasper;
	}
}

