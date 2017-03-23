package dad.todo.services.jpa.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import dad.todo.services.items.EventoItem;
import dad.todo.services.items.LugarItem;


@Entity
@Table(name="eventos")
public class EventoEntity{

	@Id 
	@GeneratedValue
	private Long id;

	@Column(nullable=false)
	private String titulo;
	
	@Column(nullable=true)
	private Date fecha;
	
	@Column(nullable=false)
	private String descripcion;
	
	@Column(nullable=false)
	private Long duracion;
	
	@Column(nullable=false)
	private Boolean realizado;
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "username")
	private UsuarioEntity usuario;
	
	@OneToOne (cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private LugarEntity lugar;
	
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

	public LugarEntity getLugar() {
		return lugar;
	}

	public void setLugar(LugarEntity lugar) {
		this.lugar = lugar;
	}

	public UsuarioEntity getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEntity usuario) {
		this.usuario = usuario;
	}

	@Override
	public boolean equals(Object obj) {
		if (id != null && obj != null && obj instanceof EventoEntity) {
			EventoEntity evento = (EventoEntity) obj;
			return id.equals(evento.getId());
		}
		return false;
	}
	
	public EventoItem toItem() {
		EventoItem item = new EventoItem();
		item.setId(getId());
		item.setTitulo(getTitulo());
		item.setFecha(getFecha());
		item.setDescripcion(getDescripcion());
		item.setDuracion(getDuracion());
		item.setRealizado(getRealizado());
		
		if(getLugar()!=null){
			item.setLugar(getLugar().toItem());
		}
		else{
			item.setLugar(null);
		}
	
		//item.setUsuario(getUsuario().toItem());
		return item;
	}
	
	public static EventoEntity fromItem(EventoItem item) {
		return fromItem(new EventoEntity(), item);
	}
	
	public static EventoEntity fromItem(EventoEntity entity, EventoItem item) {
		
		entity.setId(item.getId());
		entity.setTitulo(item.getTitulo());
		entity.setFecha(item.getFecha());
		entity.setDescripcion(item.getDescripcion());
		entity.setDuracion(item.getDuracion());
		entity.setRealizado(item.getRealizado());
		
		if (entity.getLugar() != null && item.getLugar() == null) {
			entity.setLugar(null);
		} else if (entity.getLugar() == null && item.getLugar() != null) {
			entity.setLugar(new LugarEntity());
		}
		
		if (item.getLugar() != null){
			entity.getLugar().setDescripcion(item.getLugar().getDescripcion());
			entity.getLugar().setLongitud(item.getLugar().getLongitud());
			entity.getLugar().setLatitud(item.getLugar().getLatitud());
		}
		
		return entity;
	}
	
	
	
	
}
