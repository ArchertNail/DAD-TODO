package dad.todo.notication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

	
	private static Pattern pattern;
	private static Matcher matcher;

	private static String EMAIL_PATTERN =
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public EmailValidator() {
		
	}

	public static Boolean validate(String hex) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(hex);
		return matcher.matches();

	}
}
