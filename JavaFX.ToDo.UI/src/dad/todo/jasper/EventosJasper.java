package dad.todo.jasper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import dad.todo.services.items.EventoItem;

public class EventosJasper {
	
	private String titulo, descripcion, lugar, usuario;
	private Long duracion;
	private Double latitud, longitud;
	private Date fecha;
	private Boolean realizado;
	
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Long getDuracion() {
		return duracion;
	}
	public void setDuracion(Long duracion) {
		this.duracion = duracion;
	}
	public Double getLatitud() {
		return latitud;
	}
	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}
	public Double getLongitud() {
		return longitud;
	}
	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Boolean getRealizado() {
		return realizado;
	}
	public void setRealizado(Boolean realizado) {
		this.realizado = realizado;
	}
	public static EventosJasper fromItem(EventoItem item) {
		
		EventosJasper eventJasp = new EventosJasper();
		eventJasp.setTitulo(item.getTitulo());
		eventJasp.setDescripcion(item.getDescripcion());
		eventJasp.setDuracion(item.getDuracion());
		eventJasp.setFecha(item.getFecha());
		eventJasp.setLugar(item.getLugar().getDescripcion());
		eventJasp.setLatitud(item.getLugar().getLatitud());
		eventJasp.setLongitud(item.getLugar().getLongitud());
		eventJasp.setRealizado(item.getRealizado());
		return eventJasp;
	}
	
	
}
