package dad.todo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ConfiguracionObject {

	private StringProperty username, password;
	private BooleanProperty sesion;
	private IntegerProperty tema;
	private DoubleProperty xPosScreen, yPosScreen, widthScreen, heigthScreen;
	
	public ConfiguracionObject() {
		sesion = new SimpleBooleanProperty();
		username = new SimpleStringProperty();
		password = new SimpleStringProperty();
		tema = new SimpleIntegerProperty(this, "tema",1);
		xPosScreen = new SimpleDoubleProperty();
		yPosScreen = new SimpleDoubleProperty();
		widthScreen = new SimpleDoubleProperty();
		heigthScreen = new SimpleDoubleProperty();
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

	public BooleanProperty sesionProperty() {
		return this.sesion;
	}
	

	public boolean isSesion() {
		return this.sesionProperty().get();
	}
	

	public void setSesion(final boolean sesion) {
		this.sesionProperty().set(sesion);
	}

	public IntegerProperty temaProperty() {
		return this.tema;
	}
	

	public int getTema() {
		return this.temaProperty().get();
	}
	

	public void setTema(final int tema) {
		this.temaProperty().set(tema);
	}

	public DoubleProperty xPosScreenProperty() {
		return this.xPosScreen;
	}
	

	public double getXPosScreen() {
		return this.xPosScreenProperty().get();
	}
	

	public void setXPosScreen(final double xPosScreen) {
		this.xPosScreenProperty().set(xPosScreen);
	}
	

	public DoubleProperty yPosScreenProperty() {
		return this.yPosScreen;
	}
	

	public double getYPosScreen() {
		return this.yPosScreenProperty().get();
	}
	

	public void setYPosScreen(final double yPosScreen) {
		this.yPosScreenProperty().set(yPosScreen);
	}
	

	public DoubleProperty widthScreenProperty() {
		return this.widthScreen;
	}
	

	public double getWidthScreen() {
		return this.widthScreenProperty().get();
	}
	

	public void setWidthScreen(final double widthScreen) {
		this.widthScreenProperty().set(widthScreen);
	}
	

	public DoubleProperty heigthScreenProperty() {
		return this.heigthScreen;
	}
	

	public double getHeigthScreen() {
		return this.heigthScreenProperty().get();
	}
	

	public void setHeigthScreen(final double heigthScreen) {
		this.heigthScreenProperty().set(heigthScreen);
	}
	
	
	
}
