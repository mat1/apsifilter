package ch.fhnw.apsifilter;

import java.security.Principal;

import javax.annotation.CheckReturnValue;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public final class SecurityHelper {
	
	private SecurityHelper(){}
	
	private static final String UNIX_ROOT_GROUP = "0";
	private static final String WINDOWS_ADMIN_GROUP = "S-1-5-32-544";

	@CheckReturnValue
	public static boolean checkAdmin() {
		if (runningOnWindows()) {
			return tryNTLogin();
		} else {
			return tryUnixLogin();
		}
	}

	@CheckReturnValue
	private static boolean tryUnixLogin() {
		try {
			LoginContext context = new LoginContext("Unix");
			context.login();

			Subject user = context.getSubject();
			for (Principal p : user.getPrincipals()) {
				if (UNIX_ROOT_GROUP.equals(p.getName()))
					return true;
			}

			return false;
		} catch (LoginException ex) {
			System.err.println("Error while authenticating: " + ex);
			return false;
		}
	}

	@CheckReturnValue
	private static boolean tryNTLogin() {
		try {
			LoginContext context = new LoginContext("Windows");
			context.login();

			Subject user = context.getSubject();
			for (Principal p : user.getPrincipals()) {
				if (WINDOWS_ADMIN_GROUP.equals(p.getName()))
					return true;
			}

			return false;
		} catch (LoginException ex) {
			System.err.println("Error while authenticating: " + ex);
			return false;
		}
	}

	@CheckReturnValue
	private static boolean runningOnWindows() {
		final String os = System.getProperty("os.name");

		return os.toLowerCase().contains("windows");
	}

}
