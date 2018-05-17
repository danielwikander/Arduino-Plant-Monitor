package models;

import java.io.Serializable;

/**
 * This class holds log in information.
 * It holds the users email, password and current login status.
 * @author Eric.
 */
public class User implements Serializable {

	private static final long serialVersionUID = 3667940984279025940L;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private boolean isLoggedIn;

	/**
	 * Sets the email and password of the user object.
	 * @param email		The users email-address.
	 * @param password	The users password.
	 */
	public User(String email, String password) {
		this.email = email;
		this.password = password;
		this.isLoggedIn = false;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public void setLastName(String lastName){
		this.lastName= lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
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
	public boolean isLoggedIn() {
		return this.isLoggedIn;
	}

	/**
	 * Sets the login objects login status.
	 * True 	= Logged in.
	 * False 	= Logged out.
	 * @param loggedIn	The login status to set.
	 */
	public void setIsLoggedIn(boolean loggedIn) {
		this.isLoggedIn = loggedIn;
	}
}