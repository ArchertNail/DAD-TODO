package dad.todo.eventos.listEventos;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.Format;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import dad.todo.jasper.EventosJasper;
import dad.todo.notication.Notificaciones;
import dad.todo.properties.EventoProperty;
import dad.todo.services.ServiceException;
import dad.todo.services.ServiceFactory;
import dad.todo.services.items.EventoItem;
import dad.todo.services.items.LugarItem;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;


public class EditEventoController implements Initializable {

	@FXML
	private JFXTextField tituloText,descripcionText,latitudText,longitudText;
	
	@FXML
	private JFXTextField lugarText;
	
	@FXML
	private JFXDatePicker fechaPicker, horaPicker;

	@FXML
	private Spinner<Integer> duracionSpinner;
	
	@FXML
	private JFXCheckBox realizadoCheck;
	
	@FXML
	private JFXButton guardarBtn;
	
	private EventoProperty eventoEdit;

	private EventoProperty eventoSelect;
	
	private ValidationSupport validationSupport;

	private ListProperty<EventoProperty> listaEventos;

	private ObjectProperty<LocalDate> findFecha;
	
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
		
		guardarBtn.disableProperty().bind(validationSupport.invalidProperty());
	}

	public void setEvento(EventoProperty nuevo) {
		this.eventoSelect = nuevo;

			eventoEdit = new EventoProperty();
			try{
			eventoEdit.fromItem(ServiceFactory.getEventoService().getEvento(nuevo.getId())); 
//			eventoEdit.setActualizarEvento(nuevo);
			
			eventoEdit.idProperty().set(eventoSelect.getId()); 
			tituloText.textProperty().bindBidirectional(eventoEdit.tituloProperty());
			descripcionText.textProperty().bindBidirectional(eventoEdit.descripcionProperty());
			lugarText.textProperty().bindBidirectional(eventoEdit.getLugar().descripcionProperty());
			
			Bindings.bindBidirectional(latitudText.textProperty(), eventoEdit.getLugar().latitudProperty(), new NumberStringConverter());
			Bindings.bindBidirectional(longitudText.textProperty(), eventoEdit.getLugar().longitudProperty(), new NumberStringConverter());	
		
			fechaPicker.valueProperty().bindBidirectional(eventoEdit.fechaProperty());
			horaPicker.timeProperty().bindBidirectional(eventoEdit.horaProperty());
			duracionSpinner.getValueFactory().valueProperty().bindBidirectional(eventoEdit.duracionProperty().asObject());
			
			realizadoCheck.selectedProperty().bindBidirectional(eventoEdit.relizadoProperty());
			}catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
	}
	
	@FXML
	public void onGuardarEvento(){
		
		EventoItem item = eventoEdit.toItem();
		
		try {
			ServiceFactory.getEventoService().updateEvento(item);
			eventoSelect.setActualizarEvento(eventoEdit);
			Notificaciones.correctaNotificacion("Evento actualizado", "Se ha actualizado el evento correctamente");
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void onBorrarEvento(){
		
		Task<Void> tarea = new Task<Void>() {
			protected Void call() throws Exception {
				ServiceFactory.getEventoService().deleteEvento(eventoSelect.getId());
				return null;
			}
		};
	
		tarea.stateProperty().addListener((observable,oldValue,newValue) ->{
			if(newValue.equals(State.SUCCEEDED)){
				listaEventos.remove(eventoSelect);
				findFecha.set(null);
				Notificaciones.correctaNotificacion("Evento borrado", "El evento ha sido borrado correctamente");
			}
			else if(newValue.equals(State.FAILED)){
				Notificaciones.errorNotificacion("Error", "Fallo en al conexion");
			}
		});
		new Thread(tarea).start();
		
	}
	@FXML
	public void onInformeJasper(){
		
		
		Task<Void> tarea = new Task<Void>() {
			
			protected Void call() throws Exception {
				Map<String, Object> parametros = new HashMap<>();
				parametros.put("Usuario", ServiceFactory.getLoginService().getLogueado().getNombre());
				parametros.put("FechaInforme", LocalDate.now().toString());
				
				InputStream is =this.getClass().getResourceAsStream("EventoUnico.jasper");
				
				List<EventosJasper> listEventosJasper = new ArrayList<>();
				
				try {
					EventosJasper eventoJasp = EventosJasper.fromItem(ServiceFactory.getEventoService().getEvento(eventoSelect.getId()));
					listEventosJasper.add(eventoJasp);
				} catch (ServiceException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				JasperPrint jasperPrint;
				try {
					jasperPrint = JasperFillManager.fillReport(
						is,
						parametros,
						new JRBeanCollectionDataSource(listEventosJasper)
					);
					
					JasperViewer.viewReport(jasperPrint,false);
					JasperExportManager.exportReportToPdfFile(jasperPrint, "./informe.pdf");
				} catch (JRException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		};
		
		new Thread(tarea).start();
		
	}

	public void setLista(ListProperty<EventoProperty> listEventosPropery) {
		this.listaEventos = listEventosPropery;
		
	}

	public void unBind(EventoProperty eventoAnterior) {
		if (eventoAnterior == null) return;
		tituloText.textProperty().unbindBidirectional(eventoAnterior.tituloProperty());
		descripcionText.textProperty().unbindBidirectional(eventoAnterior.descripcionProperty());
		lugarText.textProperty().unbindBidirectional(eventoAnterior.getLugar().descripcionProperty());
		
		Bindings.unbindBidirectional(latitudText.textProperty(), eventoAnterior.getLugar().latitudProperty());
		Bindings.unbindBidirectional(longitudText.textProperty(), eventoAnterior.getLugar().longitudProperty());	
	
		fechaPicker.valueProperty().unbindBidirectional(eventoAnterior.fechaProperty());
		horaPicker.timeProperty().unbindBidirectional(eventoAnterior.horaProperty());
		duracionSpinner.getValueFactory().valueProperty().unbindBidirectional(eventoAnterior.duracionProperty().asObject());
		
		realizadoCheck.selectedProperty().unbindBidirectional(eventoAnterior.relizadoProperty());
		
	}

	public void setFecha(ObjectProperty<LocalDate> findFecha) {
		this.findFecha = findFecha;
		
	}
}
