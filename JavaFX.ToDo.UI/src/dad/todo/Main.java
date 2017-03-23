package dad.todo;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import dad.todo.services.LoginService;
import dad.todo.services.ServiceException;
import dad.todo.services.ServiceFactory;
import dad.todo.services.TodoService;
import dad.todo.services.items.EventoItem;
import dad.todo.services.items.LugarItem;
import dad.todo.services.items.UsuarioItem;
import dad.todo.services.jpa.entities.EventoEntity;
import dad.todo.services.jpa.entities.PerfilEntity;
import dad.todo.services.jpa.utils.JPAUtil;

public class Main {

	public static void main(String[] args) {
		JPAUtil.initEntityManagerFactory("todo");

//		todoServiceTest();
		loginServiceTest();
		
		JPAUtil.closeEntityManagerFactory();
	}

	private static void todoServiceTest() {
		try {
			TodoService service = ServiceFactory.getEventoService();
			
			EventoItem evento = new EventoItem();
			evento.setTitulo("pokemon");
			evento.setDuracion((long) 3);
			evento.setDescripcion("juego");
			//evento.setFecha(LocalDate.now());
			evento.setRealizado(false);
			service.addEvento(evento);
//			LugarItem lugar = new LugarItem();
//			lugar.setLongitud(100.3);
//			lugar.setLatitud(4.4);
//			lugar.setDescripcion("Meridiano");
//			evento.setLugar(lugar);
//			service.addEvento(evento);
			
			//EventoItem eventoSelect = service.getEvento(2);
			//System.out.println(eventoSelect);
			
			/*
			EventoItem evento = new EventoItem();
			evento.setTitulo("Gravity rush");
			evento.setDuracion((long) 3);
			evento.setDescripcion("juegaso de ps4");
			evento.setFecha(LocalDate.now());
			evento.setRealizado(false);
			LugarItem lugar = new LugarItem();
			lugar.setLongitud(1001.5);
			lugar.setLatitud(22.4);
			lugar.setDescripcion("Game de 3 de mayo");
			evento.setLugar(lugar);*/
				
			//service.addEvento(evento);
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loginServiceTest() {
		try { 
			LoginService serviceLogin = ServiceFactory.getLoginService();
			TodoService serviceTodo = ServiceFactory.getEventoService();

			UsuarioItem usuario = new UsuarioItem();
			usuario.setUsername("jorch");
			usuario.setPassword("1111");
			usuario.setEmail("jorch@gmail.com");
			usuario.setNombre("jorge");
			serviceLogin.registerUser(usuario);

			UsuarioItem usuario2 = new UsuarioItem();
			usuario2.setUsername("peter");
			usuario2.setPassword("2222");
			usuario2.setEmail("peter@gmail.com");
			usuario2.setNombre("pet");
			serviceLogin.registerUser(usuario2);
			
			
			serviceLogin.login("jorch", "1111");
			UsuarioItem usuarioLogeado = serviceLogin.getLogueado();
			
			EventoItem evento = new EventoItem();
			evento.setTitulo("Gravity Rush2");
			evento.setDescripcion("Juego ps4 exclusivo");
			evento.setDuracion((long) 12);	
			evento.setFecha(newDate(3, 10, 2015, 5, 30));
			evento.setRealizado(false);
			LugarItem lugar = new LugarItem();
			lugar.setDescripcion("3 de mayo");
			lugar.setLatitud(40.5);
			lugar.setLongitud(650.5);
			evento.setLugar(lugar);
			serviceTodo.addEvento(evento);
			
			EventoItem evento2 = new EventoItem();
			evento2.setTitulo("Digimon");
			evento2.setDescripcion("Juego ps4");
			evento2.setDuracion((long) 12);	
			evento2.setFecha(new Date());
			evento2.setRealizado(false);
			serviceTodo.addEvento(evento2);
			
			
			serviceLogin.login("peter", "2222");
			
			EventoItem evento3 = new EventoItem();
			evento3.setTitulo("RE");
			evento3.setDescripcion("Juego ps4");
			evento3.setDuracion((long) 12);	
			evento3.setFecha(new Date());
			evento3.setRealizado(false);
			serviceTodo.addEvento(evento3);
			
			
			serviceLogin.login("jorch", "1111");
			
			
//			
//			EventoItem e = serviceTodo.getEvento(3);
//			
//			LugarItem l = new LugarItem();
//			l.setDescripcion("s/cvfvfvfvfvf");
//			l.setLatitud(44.5);
//			l.setLongitud(330.2);
//			e.setLugar(l);
//
//			serviceTodo.updateEvento(e);
//			
//			
//			EventoItem e2 = serviceTodo.getEvento(1);
//			e2.setLugar(null);
//			serviceTodo.updateEvento(e2);		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static Date newDate(int dia, int mes, int anyo, int hora, int minutos) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, dia);
		cal.set(Calendar.MONTH, mes - 1);
		cal.set(Calendar.YEAR, anyo);
		cal.set(Calendar.HOUR_OF_DAY, hora);
		cal.set(Calendar.MINUTE, minutos);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
}
