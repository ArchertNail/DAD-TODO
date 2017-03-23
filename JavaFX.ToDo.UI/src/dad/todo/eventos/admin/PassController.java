package dad.todo.eventos.admin;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import dad.todo.notication.Notificaciones;
import dad.todo.properties.UsuarioProperty;
import dad.todo.services.ServiceException;
import dad.todo.services.ServiceFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class PassController implements Initializable{

	@FXML
	private JFXPasswordField oldPassword, newPassword;
	
	private JFXTabPane tabPaneContainer;
	
	private StringProperty oldUserPass, newUserPass;

	private UsuarioProperty usuario;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		oldUserPass = new SimpleStringProperty();
		newUserPass = new SimpleStringProperty();
		
		oldUserPass.bindBidirectional(oldPassword.textProperty());
		newUserPass.bindBidirectional(newPassword.textProperty());
		
	}
	
	
	
	@FXML
	public void onVolverPerfil(){
		
		tabPaneContainer.getSelectionModel().select(2);
	}
	
	@FXML
	public void onGuardarContrasenia(){
	
			try {
				ServiceFactory.getLoginService().changePassword(oldUserPass.get(), newUserPass.get());
				usuario.setPassword(newUserPass.get());
				Notificaciones.correctaNotificacion("Contraseña cambiada", "La contraseña ha sido cambiada satisfactoriamente");
				tabPaneContainer.getSelectionModel().select(1);
				newUserPass.set("");
				oldUserPass.set("");
			} catch (ServiceException e) {
				Notificaciones.errorNotificacion("Contraseña distinta", "Compruebe que ha introducido bien su contraseña");
			}
		

	}



	public void setTabPane(JFXTabPane tabPaneContainer) {
		this.tabPaneContainer = tabPaneContainer;
		
	}



	public void setUsuario(UsuarioProperty usuario) {
		this.usuario = usuario;
		
	}

	
}
