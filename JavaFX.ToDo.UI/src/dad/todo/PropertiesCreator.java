package dad.todo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesCreator {

	private static Properties properties = new Properties();
	private static OutputStream output;
	private static InputStream input;
	private static File file;
	
	public static void guardar(ConfiguracionObject cfg) {
		try {
			File home = new File(System.getProperty("user.home"));
			File config = new File(home, ".todo");
			if (!config.exists()) config.mkdirs();
			
			file = new File(config, "config.properties");
			if(!file.exists())
				file.createNewFile();
			
			output = new FileOutputStream(file);
			
			properties.setProperty("sesion", String.valueOf(cfg.isSesion()));
			properties.setProperty("usuario", cfg.getUsername());
			properties.setProperty("password", cfg.getPassword());
			properties.setProperty("tema", String.valueOf(cfg.getTema()));
			properties.setProperty("xPosScreen", String.valueOf(cfg.getXPosScreen()));
			properties.setProperty("yPosScreen", String.valueOf(cfg.getYPosScreen()));
			properties.setProperty("widthScreen", String.valueOf(cfg.getWidthScreen()));
			properties.setProperty("heightScreen", String.valueOf(cfg.getHeigthScreen()));
			properties.store(output, null);
			
			output.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ConfiguracionObject cargar(){
		
		ConfiguracionObject cfg = new ConfiguracionObject();
		File home = new File(System.getProperty("user.home"));
		File config = new File(home, ".todo");
		if (!config.exists()) config.mkdirs();
		file = new File(config, "config.properties");
		if (!file.exists()) {
			try {
				
				file.createNewFile();
				output = new FileOutputStream(file);
				
				properties.setProperty("sesion", "false");
				properties.setProperty("usuario", "");
				properties.setProperty("password", "");
				properties.setProperty("tema", "1");
				properties.setProperty("xPosScreen", String.valueOf(596.0));
				properties.setProperty("yPosScreen", String.valueOf(161.0));
				properties.setProperty("widthScreen", String.valueOf(850.0));
				properties.setProperty("heightScreen", String.valueOf(550.0));

				properties.store(output, null);
				
				output.close();	
				
				input = new FileInputStream(file);
				properties.load(input);
				
				
				cfg.setUsername(properties.getProperty("usuario"));
				cfg.setPassword(properties.getProperty("password"));
				cfg.setSesion(Boolean.valueOf(properties.getProperty("sesion")));
				cfg.setTema(Integer.parseInt(properties.getProperty("tema")));
				cfg.setXPosScreen(Double.parseDouble(properties.getProperty("xPosScreen")));
				cfg.setYPosScreen(Double.parseDouble(properties.getProperty("yPosScreen")));
				cfg.setWidthScreen(Double.parseDouble(properties.getProperty("widthScreen")));
				cfg.setHeigthScreen(Double.parseDouble(properties.getProperty("heightScreen")));
				
				input.close();
	
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			
			try {
				
				input = new FileInputStream(file);
				properties.load(input);
				
				cfg.setUsername(properties.getProperty("usuario"));
				cfg.setPassword(properties.getProperty("password"));
				cfg.setSesion(Boolean.valueOf(properties.getProperty("sesion")));
				cfg.setTema(Integer.parseInt(properties.getProperty("tema")));
				cfg.setXPosScreen(Double.parseDouble(properties.getProperty("xPosScreen")));
				cfg.setYPosScreen(Double.parseDouble(properties.getProperty("yPosScreen")));
				cfg.setWidthScreen(Double.parseDouble(properties.getProperty("widthScreen")));
				cfg.setHeigthScreen(Double.parseDouble(properties.getProperty("heightScreen")));
				
				input.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return cfg;
	}
}
