package dad.todo.services.items;


public class LugarItem {
	private Double longitud,latitud;
	private String descripcion;
	
	public LugarItem(){
		
	}
	
	public LugarItem(Double longitud, Double latitud, String descripcion) {
		this.longitud = longitud;
		this.latitud = latitud;
		this.descripcion = descripcion;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
