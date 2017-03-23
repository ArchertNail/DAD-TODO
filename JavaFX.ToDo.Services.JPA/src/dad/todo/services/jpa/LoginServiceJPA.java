package dad.todo.services.jpa;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

import javax.persistence.EntityManager;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import dad.todo.services.LoginService;
import dad.todo.services.ServiceException;
import dad.todo.services.ServiceFactory;
import dad.todo.services.items.EventoItem;
import dad.todo.services.items.UsuarioItem;
import dad.todo.services.jpa.dao.DAOFactory;
import dad.todo.services.jpa.entities.EventoEntity;
import dad.todo.services.jpa.entities.PerfilEntity;
import dad.todo.services.jpa.entities.UsuarioEntity;
import dad.todo.services.jpa.utils.EmailUtil;
import dad.todo.services.jpa.utils.JPAUtil;

public class LoginServiceJPA implements LoginService{

	private UsuarioEntity usuarioLogin;
	
	public LoginServiceJPA() {
		usuarioLogin=null;
	}
	
	@Override
	public boolean login(String username,String password) throws ServiceException {
		usuarioLogin = new UsuarioEntity();
		try {
			UsuarioEntity user = DAOFactory.getUsuarioDAO().findByCredentials(username,  encryptPassword(password));
			usuarioLogin = user;
			return (user != null);
		} catch (Exception e) {
			throw new ServiceException("Error al logear con  el usuario: '" + username+ "'", e);
		}
		
	}

	@Override
	public void registerUser(UsuarioItem usuario) throws ServiceException {
		try {
			UsuarioEntity user = UsuarioEntity.fromItem(usuario);
			user.setPassword(encryptPassword(usuario.getPassword()));	
			DAOFactory.getUsuarioDAO().create(user);
	
		} catch (Exception e) {
			throw new ServiceException("Error intentar registrar un usuario '" + usuario.getUsername() + "'", e);
		}
		
	}

	@Override
	public void deleteUser() throws ServiceException {
		try {
			DAOFactory.getUsuarioDAO().delete(usuarioLogin.getUsername());
		} catch (Exception e) {
			throw new ServiceException("Error intentar eliminar al usuario '" + usuarioLogin.getUsername() + "'", e);
		}
	}

	@Override
	public void changePassword(String oldpassword, String newpassword) throws ServiceException {
		try {
			String encryptedNewPassword = encryptPassword(newpassword);
			String encryptedOldPassword = encryptPassword(oldpassword);
			UsuarioEntity user = usuarioLogin;
			if (user == null) {
				throw new IllegalArgumentException("El usuario indicado no existe.");
			}
			if (user.getPassword().equals(encryptedOldPassword)) {
				user.setPassword(encryptedNewPassword);
				DAOFactory.getUsuarioDAO().update(user);
			} else {
				throw new IllegalArgumentException("La contraseña anterior no coincide");
			}
		} catch (IllegalArgumentException e) {
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException("Error al cambiar la contraseña", e);
		}
	}
	
	private String encryptPassword(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = Base64.getEncoder().encode(digest.digest(password.getBytes(StandardCharsets.UTF_8)));
			return new String(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("Error encriptando contraseña", e);
		}
	}

	@Override
	public UsuarioItem getLogueado() throws ServiceException {
		try {
			UsuarioItem item = (usuarioLogin != null) ? usuarioLogin.toItem() : null;
			return item;
		} catch (Exception e) {
			throw new ServiceException("Error al recuperar el usuario: '" + usuarioLogin.getUsername() + "'", e);
		}
	}

	@Override
	public void actualizarUsuario(UsuarioItem usuario) throws ServiceException {
		
		if (usuarioLogin == null) throw new ServiceException("Debe loguearse primero");
	      UsuarioEntity entity = usuarioLogin;
	      entity.getPerfil().setNombre(usuario.getNombre());
	      entity.getPerfil().setEmail(usuario.getEmail());
	      DAOFactory.getUsuarioDAO().update(entity);
	}

	@Override
	public void recuperarPassword(String email) throws ServiceException {
		
		UsuarioEntity usuario = DAOFactory.getUsuarioDAO().findByEmail(email);
		
		if(usuario !=null){
			String newpass= UUID.randomUUID().toString().split("-")[0];
			usuario.setPassword(encryptPassword(newpass));
			
			DAOFactory.getUsuarioDAO().update(usuario);
			try {
				EmailUtil.sendEmail(email, "Contraseña cambiada", newpass);
			} catch (javax.mail.MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			System.out.println("Email incorrecto");
		}
		
	}

}
