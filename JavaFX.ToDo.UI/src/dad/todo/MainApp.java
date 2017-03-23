package dad.todo;


import java.io.IOException;

import dad.todo.eventos.MenuController;
import dad.todo.login.LoginController;
import dad.todo.properties.UsuarioProperty;
import dad.todo.services.ServiceException;
import dad.todo.services.ServiceFactory;
import dad.todo.services.jpa.utils.JPAUtil;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	Stage primaryStage;
	Scene sceneLogin;
	UsuarioProperty usuario;
	ConfiguracionObject config;
	LoginController loginController;
	String temaOffice, temaBee, temaDark;
	@Override
	public void start(Stage primaryStage) throws Exception {
		config = PropertiesCreator.cargar();
		temaOffice = "resources/office.css";
		temaBee ="resources/bee.css";
		temaDark = "resources/sunset.css";
		
		usuario = new UsuarioProperty();
		
		this.primaryStage = primaryStage;
		
		Task<Void> tarea = new Task<Void>() {	
			@Override
			protected Void call() throws Exception {
				JPAUtil.initEntityManagerFactory("todo");
				return null;
			}
		};
		
		new Thread(tarea).start();

		tarea.stateProperty().addListener((o,old,newV) -> {
			if(newV.equals(State.SUCCEEDED)){
			
				if(config.isSesion()){

					changeEventView();
				}
				else{
					iniciarLogin();
				}
			}
		});
		
	}
	
	public void iniciarLogin(){
		String tema;
		if(config.getTema() == 1){
			System.out.println("tema1");
			tema = temaOffice;
		}else if(config.getTema() == 2){
			System.out.println("tema2");
			tema = temaBee;
		}else{
			System.out.println("tema3");
			tema = temaDark;
		}
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("login/LoginView.fxml"));
			BorderPane loginView = loader.load();
			loginView.getStylesheets().add(getClass().getResource(tema).toExternalForm());
			loginController = loader.getController();
			loginController.setApp(this,usuario, config);
			sceneLogin = new Scene(loginView, config.getWidthScreen(),config.getHeigthScreen());
			primaryStage.setScene(sceneLogin);
			primaryStage.setTitle("Login");
			primaryStage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void changeEventView(){
		
		String tema;
		if(config.getTema() == 1){
			System.out.println("tema1");
			tema = temaOffice;
		}else if(config.getTema() == 2){
			System.out.println("tema2");
			tema = temaBee;
		}else{
			System.out.println("tema3");
			tema = temaDark;
		}
		
		Boolean login = null;
		try {
			login = ServiceFactory.getLoginService().login(config.getUsername(), config.getPassword());
		} catch (ServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(login){
			try {
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("eventos/MenuView.fxml"));
				BorderPane eventosView = loader.load();
				eventosView.getStylesheets().add(getClass().getResource(tema).toExternalForm());
				
				MenuController menuController = loader.getController();
				menuController.setApp(this);
				menuController.setVista(eventosView);
				menuController.setConfig(config);
				primaryStage.setScene(new Scene(eventosView, config.getWidthScreen(),config.getHeigthScreen()));
				primaryStage.centerOnScreen();
				primaryStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			iniciarLogin();
		}
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();;
		config.setWidthScreen(primaryStage.getWidth());
		config.setHeigthScreen(primaryStage.getHeight());
		PropertiesCreator.guardar(config);
		JPAUtil.closeEntityManagerFactory();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
