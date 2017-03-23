package dad.todo.login.registrate;

import dad.todo.services.items.UsuarioItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RegistrateModelo {

	private StringProperty username, password, nombre, email;
	
	public RegistrateModelo() {
		username = new SimpleStringProperty();
		password = new SimpleStringProperty();
		nombre = new SimpleStringProperty();
		email = new SimpleStringProperty();
	}

	public StringProperty usernameProperty() {
		return this.username;
	}
	

	public String getUsername() {
		return this.usernameProperty().get();
	}
	

	public void setUsername(final String username) {
		this.usernameProperty().set(username);
	}
	

	public StringProperty passwordProperty() {
		return this.password;
	}
	

	public String getPassword() {
		return this.passwordProperty().get();
	}
	

	public void setPassword(final String password) {
		this.passwordProperty().set(password);
	}
	

	public StringProperty nombreProperty() {
		return this.nombre;
	}
	

	public String getNombre() {
		return this.nombreProperty().get();
	}
	

	public void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}
	

	public StringProperty emailProperty() {
		return this.email;
	}
	

	public String getEmail() {
		return this.emailProperty().get();
	}
	

	public void setEmail(final String email) {
		this.emailProperty().set(email);
	}
	
	public void emptyRegistro(){
		setUsername("");
		setPassword("");
		setNombre("");
		setEmail("");
	}
	
	public UsuarioItem toItem(){
		UsuarioItem user = new UsuarioItem();
		user.setUsername(getUsername());
		user.setPassword(getPassword());
		user.setNombre(getNombre());
		user.setEmail(getEmail());
		return user;
	}
	
}
