package dad.todo.services.items;

import java.util.ArrayList;
import java.util.List;

public class UsuarioItem {
	private String username,password,nombre, email;
	
	public UsuarioItem() {
		
	}
	
	public UsuarioItem(String username, String password, String nombre, String email){
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
