package dad.todo.eventos.admin;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import dad.todo.ConfiguracionObject;
import dad.todo.MainApp;
import dad.todo.eventos.MenuController;
import dad.todo.notication.Notificaciones;
import dad.todo.properties.UsuarioProperty;
import dad.todo.services.ServiceException;
import dad.todo.services.ServiceFactory;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ButtonType;

public class AdminController implements Initializable {

	@FXML
	private JFXTextField nombreText, emailText;
	
	private UsuarioProperty usuarioPerfil;

	private UsuarioProperty usuario;

	private JFXTabPane tabPaneContainer;

	private MainApp mainApp;

	private BorderPane vistaEvento;

	private ConfiguracionObject config;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		usuarioPerfil = new UsuarioProperty();
		
		
		
		nombreText.textProperty().bindBidirectional(usuarioPerfil.nombreProperty());
		emailText.textProperty().bindBidirectional(usuarioPerfil.emailProperty());
		
	}
	
	
	
	@FXML
	public void onCambiarContrasenia(){
		tabPaneContainer.getSelectionModel().select(3);
	
	}
	
	@FXML 
	public void onGuardarPerfil(){
		
		usuario.setNombre(usuarioPerfil.getNombre());
		usuario.setEmail(usuarioPerfil.getEmail());
		try {
			ServiceFactory.getLoginService().actualizarUsuario(usuario.toItem());
			Notificaciones.correctaNotificacion("Perfil actualizado", "El perfil se ha actualizado correctamente");
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void onDarBajaUsuario(){
		Alert alerta=new Alert(AlertType.CONFIRMATION);
	    alerta.setTitle("Baja usuario");
	    alerta.setHeaderText("Esta apunto de borra su usuario y todos su eventos.");
	    alerta.setContentText("¿Desea Continuar?");
	    
	    Optional<ButtonType> result = alerta.showAndWait();
	    if (result.get() == ButtonType.OK){

	    	Task<Void> tarea = new Task<Void>() {
				
				protected Void call() throws Exception {
					ServiceFactory.getLoginService().deleteUser();
					return null;
				}
			};
			tarea.stateProperty().addListener((observable, oldvalue, newValue) -> {
				if (newValue.equals(State.SUCCEEDED)) {
					Notificaciones.correctaNotificacion("Baja confirmada", "Ha sido dado de baja");
					
					mainApp.iniciarLogin();
				} else if (newValue.equals(State.FAILED)) {
					Notificaciones.errorNotificacion("Error", "Problemas con la conexion");
				}
			});
			new Thread(tarea).start();
			
			
	    }else{
	    	System.out.println("operacion abortada");
	    }

	}
	
	@FXML
	public void onTemaBee(){
		vistaEvento.getStylesheets().clear();
		vistaEvento.getStylesheets().add(getClass().getResource("../../resources/bee.css").toExternalForm());
		config.temaProperty().set(2);
	}
	
	@FXML
	public void onOfficeTema(){
		vistaEvento.getStylesheets().clear();
		vistaEvento.getStylesheets().add(getClass().getResource("../../resources/office.css").toExternalForm());
		config.temaProperty().set(1);
	}
	
	@FXML
	public void onDarkTema(){
		vistaEvento.getStylesheets().clear();
		vistaEvento.getStylesheets().add(getClass().getResource("../../resources/sunset.css").toExternalForm());
		config.temaProperty().set(3);
	}
	
	public void setUsuario(UsuarioProperty usuario) {
		this.usuario = usuario;
		this.usuarioPerfil.setEmail(usuario.getEmail());
		this.usuarioPerfil.setNombre(usuario.getNombre());
		this.usuarioPerfil.setUsername(usuario.getUsername());
		this.usuarioPerfil.setPassword(usuario.getPassword());

		
	}



	public void setTabPane(JFXTabPane tabPaneContainer) {
		this.tabPaneContainer = tabPaneContainer;
	
	}



	public void setApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
	}


	public void setVista(BorderPane eventosView) {
		this.vistaEvento = eventosView;
		
	}



	public void setConfig(ConfiguracionObject config) {
		this.config = config;
		
	}
	
	
	
}
