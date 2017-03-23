package dad.todo.login.registrate;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import dad.todo.notication.EmailValidator;
import dad.todo.notication.Notificaciones;
import dad.todo.services.LoginService;
import dad.todo.services.ServiceFactory;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;

public class RegistrateController implements Initializable {

	@FXML
	private JFXTextField usernameText;

	@FXML
	private JFXPasswordField passwordText;

	@FXML
	private JFXTextField nombreText;

	@FXML
	private JFXTextField emailText;

	@FXML
	private JFXButton registrarButton;

	private RegistrateModelo registrateModelo;

	private ValidationSupport validationSupport;

	private LoginService serviceLogin;

	private JFXTabPane tabL;

	private Tab tabI;

	public RegistrateController() {
		registrateModelo = new RegistrateModelo();
		validationSupport = new ValidationSupport();
		serviceLogin = ServiceFactory.getLoginService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//css a los texfield
		usernameText.getStyleClass().add("textFieldLogin");
		passwordText.getStyleClass().add("textFieldLogin");
		nombreText.getStyleClass().add("textFieldLogin");
		emailText.getStyleClass().add("textFieldLogin");
		
		//bindeos
		registrateModelo.usernameProperty().bindBidirectional(usernameText.textProperty());
		registrateModelo.passwordProperty().bindBidirectional(passwordText.textProperty());
		registrateModelo.nombreProperty().bindBidirectional(nombreText.textProperty());
		registrateModelo.emailProperty().bindBidirectional(emailText.textProperty());

		validationSupport.registerValidator(usernameText, false,
				Validator.createEmptyValidator("Introduce tu identificador para tu usuario"));
		validationSupport.registerValidator(passwordText, false,
				Validator.createEmptyValidator("Introduce una contraseña"));
		validationSupport.registerValidator(nombreText, false,
				Validator.createEmptyValidator("Introduce un nombre para tu perfil"));
		validationSupport.registerValidator(emailText, false, Validator.createEmptyValidator("Introduce un email"));
		validationSupport.initInitialDecoration();
		registrarButton.disableProperty().bind(validationSupport.invalidProperty());
	}

	@FXML
	public void onRegistrarAction() {

		if (EmailValidator.validate(registrateModelo.getEmail())) {
			Task<Void> tarea = new Task<Void>() {
				protected Void call() throws Exception {
					serviceLogin.registerUser(registrateModelo.toItem());
					return null;
				}
			};
			tarea.stateProperty().addListener((observable, oldvalue, newValue) -> {
				if (newValue.equals(State.SUCCEEDED)) {
					Notificaciones.correctaNotificacion("Correcto", "Nuevo usuario registardo");
					registrateModelo.emptyRegistro();
					tabL.getSelectionModel().select(tabI);
				} else if (newValue.equals(State.FAILED)) {
					Notificaciones.errorNotificacion("Error", "El usuario ya existe, por favor cambie su username");
				}
			});
			new Thread(tarea).start();
		} else {
			Notificaciones.errorNotificacion("Email no valido", "compruebe el email insertado");
		}
	}

	public void setTabs(JFXTabPane tabLogin, Tab tabIdentificate) {
		this.tabL = tabLogin;
		this.tabI = tabIdentificate;

	}

}
