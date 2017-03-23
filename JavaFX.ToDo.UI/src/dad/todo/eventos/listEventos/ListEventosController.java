package dad.todo.eventos.listEventos;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.controlsfx.validation.ValidationSupport;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;

import dad.todo.eventos.crear.EventoCrearController;
import dad.todo.jasper.EventosJasper;
import dad.todo.notication.Notificaciones;
import dad.todo.properties.EventoProperty;
import dad.todo.services.ServiceException;
import dad.todo.services.ServiceFactory;
import dad.todo.services.items.EventoItem;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ListEventosController implements Initializable{

	private ListProperty<EventoProperty> listEventosPropery;

	@FXML 
	private TableView<EventoProperty>tablaEventos;
	
	@FXML
	private TableColumn<EventoProperty, String> columTitulo;
	
	@FXML
	private TableColumn<EventoProperty, LocalDate> columFecha;
	
	@FXML
	private TableColumn<EventoProperty, Boolean> columRealizado;
	
	private VBox vacioPane;
	
	@FXML
	private VBox editPaneContain;
	
	@FXML
	private JFXDatePicker buscarFechaPicker;
	
	private VBox editEventoView;
	
	private EditEventoController editEventoController;

	private JFXTabPane tabPaneContainer;

	private EventoCrearController eventoCrearController;
	
	private List<EventoItem> listItemByDate;;

	private ObjectProperty<LocalDate> findFecha;
	
	private Label labelDefault;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelDefault = new Label("No hay ningún evento seleccionado.");
		labelDefault.getStyleClass().add("labelRandom");
		findFecha = new SimpleObjectProperty<>(this, "fecha", LocalDate.now());
		vacioPane = new VBox(new HBox(labelDefault));
		vacioPane.setAlignment(Pos.CENTER);
		editPaneContain.getChildren().add(vacioPane);
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EditEventoView.fxml"));
			editEventoView = loader.load();
			editEventoController = loader.getController();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			listEventosPropery = new SimpleListProperty<>(FXCollections.observableArrayList());
			List<EventoItem> listEventosItem = ServiceFactory.getEventoService().getEventos();
		
			for (EventoItem eventoItem : listEventosItem) {
				EventoProperty eventoProperty = new EventoProperty();
				eventoProperty.fromItem(eventoItem);
				listEventosPropery.add(eventoProperty);
			}
			
			tablaEventos.itemsProperty().bind(listEventosPropery);
			columTitulo.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
			columFecha.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
			columRealizado.setCellFactory(data -> new CheckBoxTableCell<>());
			columRealizado.setCellValueFactory(cellData -> cellData.getValue().relizadoProperty());
			
			columTitulo.prefWidthProperty().bind(tablaEventos.widthProperty().multiply(0.4));
			columFecha.prefWidthProperty().bind(tablaEventos.widthProperty().multiply(0.4));
			columRealizado.prefWidthProperty().bind(tablaEventos.widthProperty().multiply(0.2));
			
			findFecha.bindBidirectional(buscarFechaPicker.valueProperty());

			tablaEventos.getSelectionModel().selectedItemProperty().addListener(
					(observable, anterior, nuevo) -> onEventoTableItemSelected(anterior, nuevo)
				);
			
			buscarFechaPicker.valueProperty().addListener((obs,old,nValue) ->{
				filtrarPorFecha(nValue);
			});
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private void filtrarPorFecha(LocalDate nValue) {
		
		if(nValue!=null){
			Date fecha = ConvertFecha.localDateToDate(nValue, LocalTime.now());
	
			Task<Void> tarea = new Task<Void>() {
				protected Void call() throws Exception {
					listItemByDate = ServiceFactory.getEventoService().buscarEventoPorFecha(fecha);
					return null;
				}
			};
			tarea.stateProperty().addListener((observable,oldValue,newValue) ->{
				if(newValue.equals(State.SUCCEEDED)){
					listEventosPropery.setAll(listItemByDate.stream().map(a -> new EventoProperty().fromItem(a)).collect(Collectors.toList()));
					
				}
				else if(newValue.equals(State.FAILED)){
					Notificaciones.errorNotificacion("Error", "Fallo en al conexion");
				}
			});
			new Thread(tarea).start();
			
		}
		else{
			Task<Void> tarea = new Task<Void>() {
				protected Void call() throws Exception {
					listItemByDate = ServiceFactory.getEventoService().getEventos();
					return null;
				}
			};
			tarea.stateProperty().addListener((observable,oldValue,newValue) ->{
				if(newValue.equals(State.SUCCEEDED)){
					listEventosPropery.setAll(listItemByDate.stream().map(a -> new EventoProperty().fromItem(a)).collect(Collectors.toList()));
				}
				else if(newValue.equals(State.FAILED)){
					Notificaciones.errorNotificacion("Error", "Fallo en al conexion");
				}
			});
			new Thread(tarea).start();
		}
		
		
	}

	private void onEventoTableItemSelected(EventoProperty anterior, EventoProperty nuevo) {
		editPaneContain.getChildren().clear();
		editEventoController.unBind(anterior);
		if (nuevo == null) {
			editPaneContain.getChildren().add(vacioPane);
		} else {
			editPaneContain.getChildren().add(editEventoView);
			editEventoController.setEvento(nuevo);
			editEventoController.setLista(listEventosPropery);
			editEventoController.setFecha(findFecha);
		}
		
	}
	
	@FXML
	public void onInformeGeneral(){
		
		Task<Void> tarea = new Task<Void>() {

			protected Void call() throws Exception {
				Map<String, Object> parametros = new HashMap<>();
				try {
					parametros.put("Usuario", ServiceFactory.getLoginService().getLogueado().getNombre());
					parametros.put("FechaInforme", LocalDate.now().toString());
				} catch (ServiceException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				parametros.put("IMAGEN",this.getClass().getResource("logo.png"));
				
				InputStream is =this.getClass().getResourceAsStream("Eventos.jasper");
				
				List<EventosJasper> listEventosJasper = EventosProvider.getEventos();
				
				JasperPrint jasperPrint;
				try {
					jasperPrint = JasperFillManager.fillReport(
						is,
						parametros,
						new JRBeanCollectionDataSource(listEventosJasper)
					);
					
					JasperViewer.viewReport(jasperPrint, false);
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
	
	@FXML 
	public void onResetFecha(){
		findFecha.set(null);
	}

	@FXML
	private void onIrCrearEvento(){
		tabPaneContainer.getSelectionModel().select(1);
		
	}

	

	public void setTabPane(JFXTabPane tabPaneContainer) {
		this.tabPaneContainer = tabPaneContainer;
		
	}



	public void setCrearController(EventoCrearController eventoCrearController) {
		this.eventoCrearController = eventoCrearController;
		eventoCrearController.setListEventos(listEventosPropery);
		eventoCrearController.setFindFecha(findFecha);
	}
	
}
