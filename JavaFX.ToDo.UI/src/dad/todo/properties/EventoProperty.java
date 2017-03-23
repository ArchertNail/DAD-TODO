package dad.todo.properties;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import dad.todo.eventos.listEventos.ConvertFecha;
import dad.todo.services.items.EventoItem;
import dad.todo.services.items.LugarItem;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EventoProperty {

	private LongProperty id;
	private IntegerProperty duracion;
	private StringProperty titulo, descripcion;
	private ObjectProperty<LocalDate> fecha;
	private ObjectProperty<LocalTime> hora;
	private BooleanProperty relizado;
	private ObjectProperty<LugarPropery> lugar;

	public EventoProperty() {
		id = new SimpleLongProperty();
		duracion = new SimpleIntegerProperty();
		titulo = new SimpleStringProperty();
		descripcion = new SimpleStringProperty();
		fecha = new SimpleObjectProperty<>(this, "fecha", LocalDate.now());
		hora = new SimpleObjectProperty<>(this, "hora", LocalTime.now());
		relizado = new SimpleBooleanProperty();
		lugar = new SimpleObjectProperty<LugarPropery>(this, "Lugar", new LugarPropery());
	}

	public LongProperty idProperty() {
		return this.id;
	}

	public long getId() {
		return this.idProperty().get();
	}

	public void setId(final long id) {
		this.idProperty().set(id);
	}

	public StringProperty tituloProperty() {
		return this.titulo;
	}

	public String getTitulo() {
		return this.tituloProperty().get();
	}

	public void setTitulo(final String titulo) {
		this.tituloProperty().set(titulo);
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

	public BooleanProperty relizadoProperty() {
		return this.relizado;
	}

	public boolean isRelizado() {
		return this.relizadoProperty().get();
	}

	public void setRelizado(final boolean relizado) {
		this.relizadoProperty().set(relizado);
	}

	public ObjectProperty<LugarPropery> lugarProperty() {
		return this.lugar;
	}

	public LugarPropery getLugar() {
		return this.lugarProperty().get();
	}

	public void setLugar(final LugarPropery lugar) {
		this.lugarProperty().set(lugar);
	}

	public ObjectProperty<LocalDate> fechaProperty() {
		return this.fecha;
	}

	public LocalDate getFecha() {
		return this.fechaProperty().get();
	}

	public void setFecha(final LocalDate fecha) {
		this.fechaProperty().set(fecha);
	}

	public ObjectProperty<LocalTime> horaProperty() {
		return this.hora;
	}

	public LocalTime getHora() {
		return this.horaProperty().get();
	}

	public void setHora(final LocalTime hora) {
		this.horaProperty().set(hora);
	}

	public IntegerProperty duracionProperty() {
		return this.duracion;
	}

	public int getDuracion() {
		return this.duracionProperty().get();
	}

	public void setDuracion(final int duracion) {
		this.duracionProperty().set(duracion);
	}

	public EventoProperty fromItem(EventoItem evento) {
		setId(evento.getId());
		setTitulo(evento.getTitulo());
		setDescripcion(evento.getDescripcion());
		setDuracion((int) (long) evento.getDuracion());

		setFecha(evento.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		setHora(evento.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
		setRelizado(evento.getRealizado());

		if (evento.getLugar() != null) {
			LugarPropery lugarPropery = new LugarPropery();
			lugarPropery.setDescripcion(evento.getLugar().getDescripcion());
			lugarPropery.setLatitud(evento.getLugar().getLatitud());
			lugarPropery.setLongitud(evento.getLugar().getLongitud());
			setLugar(lugarPropery);
		} else {
			LugarPropery lugarPropery = new LugarPropery();
			lugarPropery.setDescripcion("");
			lugarPropery.setLatitud(0.0);
			lugarPropery.setLongitud(0.0);
			setLugar(lugarPropery);
		}

		return this;
	}

	public EventoItem toItem() {

		EventoItem item = new EventoItem();
		Date f;
		item.setId(getId());
		item.setTitulo(getTitulo());
		item.setDescripcion(getDescripcion());
		Integer dur = getDuracion();
		item.setDuracion(dur.longValue());
		if (getHora() != null) {
			f = ConvertFecha.localDateToDate(getFecha(), getHora());
		} else {
			LocalTime t = LocalTime.now();
			f = ConvertFecha.localDateToDate(getFecha(), t);
		}
		item.setFecha(f);
		item.setRealizado(isRelizado());
		LugarItem lugarItem = new LugarItem();
		lugarItem.setDescripcion(getLugar().getDescripcion());
		lugarItem.setLatitud(getLugar().getLatitud());
		lugarItem.setLongitud(getLugar().getLongitud());
		item.setLugar(lugarItem);
		return item;
	}

	public void setActualizarEvento(EventoProperty eventoEdit) {
		setId(eventoEdit.getId());
		setTitulo(eventoEdit.getTitulo());
		setDescripcion(eventoEdit.getDescripcion());
		setDuracion((int) (long) eventoEdit.getDuracion());

		setFecha(eventoEdit.getFecha());
		setHora(eventoEdit.getHora());
		setRelizado(eventoEdit.isRelizado());

		eventoEdit.getLugar().setDescripcion(eventoEdit.getLugar().getDescripcion());
		eventoEdit.getLugar().setLatitud(eventoEdit.getLugar().getLatitud());
		eventoEdit.getLugar().setLongitud(eventoEdit.getLugar().getLongitud());

	}

	public void emptyEvento() {

		setTitulo("");
		setDescripcion("");
		setDuracion(0);

		setFecha(null);
		setHora(LocalTime.now());
		setRelizado(false);

		getLugar().setDescripcion("");
		getLugar().setLatitud(0);
		getLugar().setLongitud(0);

	}

}
