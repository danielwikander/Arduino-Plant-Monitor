package SharedResources;

import java.io.Serializable;

public class Login implements Serializable {

	private static final long serialVersionUID = 3667940984279025940L;
	private String email;
	private String password;
	private boolean loginStatus;
	
	public Login(String email, String password) {
		this.email = email;
		this.password = password;
		this.loginStatus = false;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public boolean getLoginStatus() {
		return this.loginStatus;
	}
	
	public void setLoginStatus(boolean loginStatus) {
		this.loginStatus = loginStatus;
	}
}