package SharedResources;

import java.io.Serializable;

/**
 * This class holds log in information.
 * It holds the users email, password and current login status.
 */
public class Login implements Serializable {

	private static final long serialVersionUID = 3667940984279025940L;
	private String email;
	private String password;
	private boolean loginStatus;

	/**
	 * Sets the email and password of the user object.
	 * @param email		The users email-address.
	 * @param password	The users password.
	 */
	public Login(String email, String password) {
		this.email = email;
		this.password = password;
		this.loginStatus = false;
	}

	/**
	 * Returns the login objects email-address.
	 * @return	Returns the email-address.
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Returns the login objects password.
	 * @return 	Returns the password.
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Returns the login objects login status.
	 * True 	= Logged in.
	 * False 	= Logged out.
	 * @return	  Returns the users login status.
	 */
	public boolean getLoginStatus() {
		return this.loginStatus;
	}

	/**
	 * Sets the login objects login status.
	 * True 	= Logged in.
	 * False 	= Logged out.
	 * @param loginStatus	The login status to set.
	 */
	public void setLoginStatus(boolean loginStatus) {
		this.loginStatus = loginStatus;
	}
}