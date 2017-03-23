package dad.todo.services;

import dad.todo.services.items.UsuarioItem;

public interface LoginService {
	public boolean login(String username, String password) throws ServiceException;
	public void registerUser(UsuarioItem usuario) throws ServiceException;
	public void deleteUser() throws ServiceException;
	public void changePassword(String oldPassword, String newpassword) throws ServiceException;
	public UsuarioItem getLogueado() throws ServiceException;
	public void actualizarUsuario(UsuarioItem usuario) throws ServiceException;
	public void recuperarPassword(String email) throws ServiceException;

}
