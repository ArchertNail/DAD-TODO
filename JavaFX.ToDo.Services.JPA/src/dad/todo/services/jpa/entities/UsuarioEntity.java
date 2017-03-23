package dad.todo.services.jpa.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import dad.todo.services.items.EventoItem;
import dad.todo.services.items.UsuarioItem;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity{

	@Id
	private String username;

	private String password;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="username")
	private List<EventoEntity> eventos;
	
	@OneToOne (cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	private PerfilEntity perfil;
	
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
	
	public PerfilEntity getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilEntity perfil) {
		this.perfil = perfil;
	}
	
	public List<EventoEntity> getEvento() {
		return eventos;
	}

	public void setEvento(List<EventoEntity> evento) {
		eventos = evento;
	}

	@Override
	public boolean equals(Object obj) {
		if (username != null && obj != null && obj instanceof UsuarioItem) {
			UsuarioItem user = (UsuarioItem) obj;
			return username.equals(user.getUsername());
		}
		return false;
	}
	
	public UsuarioItem toItem() {
		UsuarioItem item = new UsuarioItem();
		item.setUsername(getUsername());
		item.setPassword(getPassword());
        item.setNombre(getPerfil().getNombre());
		item.setEmail(getPerfil().getEmail());
		return item;
	}
	
	public static UsuarioEntity fromItem(UsuarioItem item) {
		UsuarioEntity entity = new UsuarioEntity();
		entity.setUsername(item.getUsername());
		entity.setPassword(item.getPassword());
		PerfilEntity perfil = new PerfilEntity();
		perfil.setNombre(item.getNombre());
		perfil.setEmail(item.getEmail());
		perfil.setUsuario(entity);
		entity.setPerfil(perfil);	
		return entity;
	}

	
	

}
