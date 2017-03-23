package dad.todo.eventos;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;

import dad.todo.ConfiguracionObject;
import dad.todo.MainApp;
import dad.todo.eventos.admin.AdminController;
import dad.todo.eventos.admin.PassController;
import dad.todo.eventos.crear.EventoCrearController;
import dad.todo.eventos.listEventos.ListEventosController;

import dad.todo.properties.UsuarioProperty;
import dad.todo.services.ServiceException;
import dad.todo.services.ServiceFactory;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


public class MenuController implements Initializable{
	
    @FXML 
    private JFXTabPane tabPaneContainer;
    
    @FXML
    private Label nombreText;
    
    @FXML
    private JFXButton perfilBtn, eventosBtn, salirBtn;
    
	private MainApp mainApp;
	
	@FXML
	private AdminController adminController;
	
	@FXML
	private PassController passController; 
	
	@FXML
	private ListEventosController listEventosController;
	
	@FXML 
	private EventoCrearController eventoCrearController;

	private UsuarioProperty usuario;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//cargamos el usuario para que podamos usar sus datos en el menu y en la zona de Admin
		usuario = new UsuarioProperty();
		try {
			usuario.fromItem(ServiceFactory.getLoginService().getLogueado());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//bindeamos con el label para mostrar nuestro nombre de usuario
		nombreText.textProperty().bind(usuario.nombreProperty());
	
		perfilBtn.getStyleClass().add("botonesMenu");
		eventosBtn.getStyleClass().add("botonesMenu");
		salirBtn.getStyleClass().add("botonesMenu");
		eventosBtn.getStyleClass().add("botonMenuSelect");
		
		adminController.setTabPane(tabPaneContainer);
		adminController.setUsuario(usuario);
		
		passController.setTabPane(tabPaneContainer);
		passController.setUsuario(usuario);
		
		listEventosController.setTabPane(tabPaneContainer);
		eventoCrearController.setTabPane(tabPaneContainer);
		
		listEventosController.setCrearController(eventoCrearController);
		
		
	}

	@FXML
	public void onAdminAction(){
		
		perfilBtn.getStyleClass().add("botonMenuSelect");
		eventosBtn.getStyleClass().removeAll("botonMenuSelect");
		salirBtn.getStyleClass().removeAll("botonMenuSelect");
		tabPaneContainer.getSelectionModel().select(2);	
	}
	
	
	@FXML
	public void onEventosAction(){
		eventosBtn.getStyleClass().add("botonMenuSelect");
		perfilBtn.getStyleClass().removeAll("botonMenuSelect");	
		salirBtn.getStyleClass().removeAll("botonMenuSelect");
		tabPaneContainer.getSelectionModel().select(0);
	}
	
	@FXML
	public void onLogoutAction(){
		mainApp.iniciarLogin();
	}
	
	
	public void setApp(MainApp mainApp) {
		this.mainApp = mainApp;
		adminController.setApp(mainApp);
		
	}

	public void setVista(BorderPane eventosView) {
		adminController.setVista(eventosView);
		
	}

	public void setConfig(ConfiguracionObject config) {
		adminController.setConfig(config);
		
	}
	
	

}
