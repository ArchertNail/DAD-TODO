package dad.todo.services.items;

import java.util.Date;

public class EventoItem {

	private Long id;
	private String titulo;
	private Date fecha;
	private String descripcion;
	private Long duracion;
	private Boolean realizado;
	private LugarItem lugar;
	public EventoItem(){
		
	}
	
	public EventoItem(String titulo, Date fecha, String descripcion, Long duracion, Boolean realizado, LugarItem lugar) {
		this.titulo = titulo;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.duracion = duracion;
		this.realizado = realizado;
		this.lugar = lugar;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getDuracion() {
		return duracion;
	}

	public void setDuracion(Long duracion) {
		this.duracion = duracion;
	}

	public Boolean getRealizado() {
		return realizado;
	}

	public void setRealizado(Boolean realizado) {
		this.realizado = realizado;
	}
	
	public LugarItem getLugar() {
		return lugar;
	}

	public void setLugar(LugarItem lugar) {
		this.lugar = lugar;
	}

	@Override
	public String toString(){
		return "id= " + getId() + "/titulo= " + getTitulo() + "/fecha= " + getFecha() + "/descripcion= " + getDescripcion() + "/Duracion= " + getDuracion() + "/realizado= " + getRealizado();             
	}
	
}
