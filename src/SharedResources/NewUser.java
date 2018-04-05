package SharedResources;

import java.io.Serializable;

public class NewUser implements Serializable {

	private static final long serialVersionUID = -595827879522136821L;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private boolean newUserStatus;
	
	public NewUser(String email, String password, String firstName, String lastName) {
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.newUserStatus = false;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public boolean getNewUserStatus() {
		return newUserStatus;
	}
	
	public void setNewUserStatus(boolean newUserStatus) {
		this.newUserStatus = newUserStatus;
	}
}