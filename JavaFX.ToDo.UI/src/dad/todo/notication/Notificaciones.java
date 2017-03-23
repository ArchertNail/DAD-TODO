package dad.todo.notication;

import org.controlsfx.control.Notifications;

public class Notificaciones {

	public static Notifications notif;
	
	public static void errorNotificacion(String titulo, String contenido){
		notif = Notifications.create()
				.title(titulo)
				.text(contenido);
				
		notif.showError();
		
	}
	
	public static void correctaNotificacion(String titulo, String contenido){
		notif = Notifications.create()
				.title(titulo)
				.text(contenido);
		notif.showConfirm();
	}
}
