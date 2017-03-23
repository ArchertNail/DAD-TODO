package dad.todo.eventos.crear;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import dad.todo.notication.Notificaciones;
import dad.todo.properties.EventoProperty;
import dad.todo.services.ServiceFactory;
import dad.todo.services.items.EventoItem;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.util.converter.NumberStringConverter;

public class EventoCrearController implements Initializable {

	@FXML
    private JFXTextField tituloText;

    @FXML
    private JFXTextField descripcionText;

    @FXML
    private Spinner<Integer> duracionSpinner;

    @FXML
    private JFXDatePicker fechaPicker;

    @FXML
    private JFXDatePicker horaPicker;

    @FXML
    private JFXTextField lugarText;

    @FXML
    private JFXTextField longitudText;

    @FXML
    private JFXTextField latitudText;

    @FXML
    private JFXCheckBox realizadoCheck;

    @FXML
    private JFXButton guardarBtn;
    
	private JFXTabPane tabPaneContainer;

	private ListProperty<EventoProperty> listEventosProperty;
	
	private ValidationSupport validationSupport;
	
	private EventoProperty evento;
	
	List<EventoItem> listEventosItem;

	private ObjectProperty<LocalDate> pickerFindFecha;
	
	public EventoCrearController() {
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Pattern pattern = Pattern.compile("[0-9]+(\\,[0-9][0-9]?)?||^$");
		
		horaPicker.setValue(LocalDate.now());
		validationSupport = new ValidationSupport();
		validationSupport.registerValidator(tituloText, false, Validator.createEmptyValidator("El titulo es obligatorio"));
		validationSupport.registerValidator(fechaPicker, false, Validator.createEmptyValidator("La fecha es obligatoria"));
//		validationSupport.registerValidator(horaPicker, false, Validator.createRegexValidator("vacioo", pattern2, null));
		validationSupport.registerValidator(longitudText, false, Validator.createRegexValidator("solo se acepta un valor numerico", pattern, null));
		validationSupport.registerValidator(latitudText, false, Validator.createRegexValidator("solo se acepta un valor numerico", pattern, null));
		validationSupport.initInitialDecoration();
		guardarBtn.disableProperty().bind(validationSupport.invalidProperty());

		bind();
	}
	
	public void bind() {
		
		evento = new EventoProperty();
		evento.tituloProperty().bindBidirectional(tituloText.textProperty()); 	
		evento.descripcionProperty().bindBidirectional(descripcionText.textProperty()); 

		evento.getLugar().descripcionProperty().bindBidirectional(lugarText.textProperty());
		Bindings.bindBidirectional(longitudText.textProperty(), evento.getLugar().longitudProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(latitudText.textProperty(), evento.getLugar().latitudProperty(), new NumberStringConverter());

		evento.fechaProperty().bindBidirectional(fechaPicker.valueProperty());
		evento.horaProperty().bindBidirectional(horaPicker.timeProperty());

		duracionSpinner.getValueFactory().valueProperty().addListener((o,ov,nv) -> {
			if (ov != nv)
				evento.setDuracion(nv);
		});
		evento.duracionProperty().addListener((o,ov,nv) -> {
			if (ov != nv)
				duracionSpinner.getValueFactory().setValue(nv.intValue());
		});
		
		evento.relizadoProperty().bindBidirectional(realizadoCheck.selectedProperty());
	}

	@FXML 
	private void onVolverListEventos(){
		tabPaneContainer.getSelectionModel().select(0);
	}

	
	@FXML
	private void onGuardarEvento(){

		Task<Void> tarea = new Task<Void>() {
			protected Void call() throws Exception {
				EventoItem item = evento.toItem();
				item.setId(null);
				ServiceFactory.getEventoService().addEvento(item);
				listEventosItem = ServiceFactory.getEventoService().getEventos();
				return null;
			}
		};
		tarea.stateProperty().addListener((observable,oldValue,newValue) ->{
			if(newValue.equals(State.SUCCEEDED)){
				Notificaciones.correctaNotificacion("Evento creado", "El evento ha sido creado correctamente");
				listEventosProperty.setAll(listEventosItem.stream().map(a -> new EventoProperty().fromItem(a)).collect(Collectors.toList()));
				tabPaneContainer.getSelectionModel().select(0);
				evento.emptyEvento();
				pickerFindFecha.set(null);
			}
			else if(newValue.equals(State.FAILED)){
				Notificaciones.errorNotificacion("Error", "Fallo en al conexion");
			}
		});
		new Thread(tarea).start();
		
	}

	
	
	public void unbind() {
		evento.tituloProperty().unbindBidirectional(tituloText.textProperty()); 	
		evento.descripcionProperty().unbindBidirectional(descripcionText.textProperty()); 

		evento.getLugar().descripcionProperty().unbindBidirectional(lugarText.textProperty());
		Bindings.unbindBidirectional(longitudText.textProperty(), evento.getLugar().longitudProperty());
		Bindings.unbindBidirectional(latitudText.textProperty(), evento.getLugar().latitudProperty());

		evento.fechaProperty().unbindBidirectional(fechaPicker.valueProperty());
		evento.horaProperty().unbindBidirectional(horaPicker.timeProperty());
		
		duracionSpinner.getValueFactory().valueProperty().unbindBidirectional(evento.duracionProperty().asObject());
		evento.relizadoProperty().unbindBidirectional(realizadoCheck.selectedProperty());
		
	}

	public void setTabPane(JFXTabPane tabPaneContainer) {
		this.tabPaneContainer = tabPaneContainer;
		
	}

	public void setListEventos(ListProperty<EventoProperty> listEventosPropery) {
		this.listEventosProperty = listEventosPropery;
		
	}

	public void setFindFecha(ObjectProperty<LocalDate> findFecha) {
		this.pickerFindFecha = findFecha;
		
	}
	
}
