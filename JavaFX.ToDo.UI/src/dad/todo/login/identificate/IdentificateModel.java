package dad.todo.login.identificate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class IdentificateModel {

	private StringProperty username,password;
	
	public IdentificateModel() {
		username = new SimpleStringProperty();
		password = new SimpleStringProperty();
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
	
	
	
}
