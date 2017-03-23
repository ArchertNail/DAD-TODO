package dad.todo.login.identificate;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import org.controlsfx.control.Notifications;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import dad.todo.ConfiguracionObject;
import dad.todo.MainApp;
import dad.todo.notication.EmailValidator;
import dad.todo.notication.Notificaciones;
import dad.todo.properties.UsuarioProperty;
import dad.todo.services.ServiceException;
import dad.todo.services.ServiceFactory;
import dad.todo.services.items.UsuarioItem;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextInputDialog;

public class IdentificateController implements Initializable {

	@FXML
	private JFXTextField usernameText;

	@FXML
	private JFXPasswordField passwordText;

	@FXML
	private JFXButton identificarButton;

	@FXML
	private JFXCheckBox recordarCheck;
	
	private IdentificateModel idenficateModel;

	private ValidationSupport validationSupport;

	private UsuarioProperty usuario;

	private MainApp mainApp;

	private ConfiguracionObject config;

	private String email = null;
	
	public IdentificateController() {

		idenficateModel = new IdentificateModel();
		validationSupport = new ValidationSupport();
		

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idenficateModel.usernameProperty().bind(usernameText.textProperty());
		idenficateModel.passwordProperty().bind(passwordText.textProperty());

		validationSupport.registerValidator(usernameText, false,
				Validator.createEmptyValidator("Debe poner algo en el cuadro de texto"));
		identificarButton.disableProperty().bind(validationSupport.invalidProperty());
		
		usernameText.getStyleClass().add("textFieldLogin");
		passwordText.getStyleClass().add("textFieldLogin");
	}

	public void onOlvidarContraseniaAction() {

		
		TextInputDialog dialog = new TextInputDialog("Introduce tu email");
		dialog.setTitle("Recuperar contraseña");
		dialog.setHeaderText("Introduzca el email");
		dialog.setContentText("Email:");
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    email = result.get();
		    
		    if(EmailValidator.validate(email)){
				Task<Void> tarea = new Task<Void>() {

					protected Void call() throws Exception {
						ServiceFactory.getLoginService().recuperarPassword(email);
						return null;
					}
				};
				tarea.stateProperty().addListener((observable, oldvalue, newValue) -> {
					if (newValue.equals(State.SUCCEEDED)) {
						Notificaciones.correctaNotificacion("Email enviado", "Le hemos mandado su contraseña al correo electronico");
						
					} else if (newValue.equals(State.FAILED)) {
						Notificaciones.errorNotificacion("Error", "Problemas recuperar su contrasenia");
					}
				});
				new Thread(tarea).start();
			}
		   
			else{
				Notificaciones.correctaNotificacion("Correo no valido", "Compruebe que lo ha escrito correctamente");
			}
		}
		
	}

	@FXML
	public void onLoginAction() {
		
		Task<UsuarioItem> tarea = new Task<UsuarioItem>() {

			protected UsuarioItem call() throws Exception {
				UsuarioItem item = null;
				if (ServiceFactory.getLoginService().login(idenficateModel.getUsername(),idenficateModel.getPassword())) {
					item = ServiceFactory.getLoginService().getLogueado();
				} else {
					
					throw new Exception("No se pudo inciar sesión");
				}
				return item;
			}
		};

		tarea.stateProperty().addListener((obs, old, nuevo) -> {
			if (nuevo.equals(State.SUCCEEDED)) {
				try {
					usuario.fromItem(tarea.get());
					mainApp.changeEventView();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			} else if (nuevo.equals(State.RUNNING)) {
				System.out.println("cargando...");
			} else if (nuevo.equals(State.FAILED)) {
				Notificaciones.errorNotificacion("Usuario incorrecto", "Revise su usuario y contraseña");
			}
		});

		new Thread(tarea).start();

	}

	public void setApp(MainApp mainApp, UsuarioProperty usuario, ConfiguracionObject config) {
		this.mainApp = mainApp;
		this.usuario = usuario;
		this.config = config;
		
		config.usernameProperty().bind(usernameText.textProperty());
		config.passwordProperty().bind(passwordText.textProperty());
		config.sesionProperty().bind(recordarCheck.selectedProperty());
	}

}
