package dad.todo.login;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTabPane;

import dad.todo.ConfiguracionObject;
import dad.todo.MainApp;
import dad.todo.login.identificate.IdentificateController;
import dad.todo.login.registrate.RegistrateController;
import dad.todo.properties.UsuarioProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;

public class LoginController implements Initializable {

	@FXML
	private IdentificateController identificateController;

	@FXML
	private RegistrateController registrateController;

	@FXML
	private JFXTabPane tabLogin;

	@FXML
	private Tab tabIdentificate;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		registrateController.setTabs(tabLogin, tabIdentificate);
	}

	public void setApp(MainApp mainApp, UsuarioProperty usuario, ConfiguracionObject config) {
		this.identificateController.setApp(mainApp, usuario, config);
	}

}
