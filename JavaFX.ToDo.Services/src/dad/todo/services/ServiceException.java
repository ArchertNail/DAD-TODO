package dad.todo.services;

public class ServiceException extends Exception {
	private static final long serialVersionUID = 6670835078891777093L;

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
