package dad.todo.services.jpa.dao;

public final class DAOFactory {
	
	private static EventoDAO EventoDao;
	private static UsuarioDAO usuarioDao;
	
	private DAOFactory() {}
	
	public static EventoDAO getEventoDAO() {
		if (EventoDao == null) {
			EventoDao = new EventoDAO();
		}
		return EventoDao;
	}
	
	public static UsuarioDAO getUsuarioDAO() {
		if (usuarioDao == null) {
			usuarioDao = new UsuarioDAO();
		}
		return usuarioDao;
	}
}
