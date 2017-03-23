package dad.todo.properties;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LugarPropery {
	private StringProperty descripcion;
	private DoubleProperty latitud;
	private DoubleProperty longitud;
	
	public LugarPropery() {
		
		descripcion = new SimpleStringProperty();
		latitud = new SimpleDoubleProperty();
		longitud = new SimpleDoubleProperty();
	}
	

	public StringProperty descripcionProperty() {
		return this.descripcion;
	}
	

	public String getDescripcion() {
		return this.descripcionProperty().get();
	}
	

	public void setDescripcion(final String descripcion) {
		this.descripcionProperty().set(descripcion);
	}
	

	public DoubleProperty latitudProperty() {
		return this.latitud;
	}
	

	public double getLatitud() {
		return this.latitudProperty().get();
	}
	

	public void setLatitud(final double latitud) {
		this.latitudProperty().set(latitud);
	}
	

	public DoubleProperty longitudProperty() {
		return this.longitud;
	}
	

	public double getLongitud() {
		return this.longitudProperty().get();
	}
	

	public void setLongitud(final double longitud) {
		this.longitudProperty().set(longitud);
	}
	
	
}
