package server.models;

import java.io.Serializable;

/**
 * Holds information about a new user.
 * Information such as email, password, name and user
 */
public class NewUser implements Serializable {

	private static final long serialVersionUID = -595827879522136821L;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private boolean newUserStatus;

	/**
	 * Constructor that sets all the new user information.
	 * @param email		The users email-address.
	 * @param password	The users password.
	 * @param firstName	The users first name.
	 * @param lastName	The users last name.
	 */
	public NewUser(String email, String password, String firstName, String lastName) {
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.newUserStatus = false;
	}

	/**
	 * Returns the users email-address.
	 * @return	Returns the email-address.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns the users password.
	 * @return	Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Returns the users first name.
	 * @return Returns the first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns the users last name.
	 * @return Returns the last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns a boolean that holds information about the new users status in the database.
	 * If the new user was validly created in the database, the boolean is true.
	 * @return The new users status. Returns true if the user was inserted into the database.
	 */
	public boolean getNewUserStatus() {
		return newUserStatus;
	}

	/**
	 * Boolean that checks if the new user was validly created in the database.
	 * If the user information was valid and a new user was created, the boolean is true.
	 * The value is used to automatically login newly created users to the client.
	 * //TODO: Save new users information in the loginview if this boolean returns true.
	 */
	public void setNewUserStatus(boolean newUserStatus) {
		this.newUserStatus = newUserStatus;
	}
}