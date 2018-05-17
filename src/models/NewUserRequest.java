package models;

import java.io.Serializable;

/**
 * Holds information about a new user.
 * Information such as email, password, name and user
 * @author David.
 */
public class NewUserRequest implements Serializable {

	private static final long serialVersionUID = -595827879522136821L;
	private User user;
	private boolean newUserStatus;

	/**
	 * Constructor that sets all the new user information.
	 * @param email		The users email-address.
	 * @param password	The users password.
	 * @param firstName	The users first name.
	 * @param lastName	The users last name.
	 */
	public NewUserRequest(String email, String password, String firstName, String lastName) {
		user = new User(email, password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		this.newUserStatus = false;
	}

	public User getUser() {
		return user;
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
	 */
	public void setNewUserStatus(boolean newUserStatus) {
		this.newUserStatus = newUserStatus;
	}
}