package dad.todo.services.jpa.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import dad.todo.services.items.LugarItem;

@Entity
@Table(name = "lugar")
public class LugarEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true)
	private Double longitud;

	@Column(nullable = true)
	private Double latitud;

	@Column(nullable = true)
	private String descripcion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

//	public EventoEntity getEvento() {
//		return evento;
//	}
//
//	public void setEvento(EventoEntity evento) {
//		this.evento = evento;
//	}

	public LugarItem toItem() {
		LugarItem item = new LugarItem();
		item.setLongitud(getLongitud());
		item.setLatitud(getLatitud());
		item.setDescripcion(getDescripcion());
		return item;
	}

	public static LugarEntity fromItem(LugarItem item) {
		LugarEntity entity = new LugarEntity();
		entity.setLongitud(item.getLongitud());
		entity.setLatitud(item.getLatitud());
		entity.setDescripcion(item.getDescripcion());
		return entity;
	}

}
