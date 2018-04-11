package Client;

import SharedResources.Login;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The login view that the user is presented with when they start the application.
 */
public class LoginViewController implements Initializable {

	@FXML
	private TextField email;
	@FXML
	private PasswordField password;
	@FXML
	private Label loginError;
	@FXML
	private Button login;
	@FXML
	private Button newUser;
	
	ConnectionController connectionController;

	/**
	 * Initializes the login view.
	 * @param arg0 //TODO: Explain FXML stuff?
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		connectionController = ConnectionController.getInstance();
		connectionController.setLoginViewController(this);
		loginError.setVisible(false);
	}

	/**
	 * Attempts to log in the user.
	 * This method is called when users press 'Logga in'
	 */
	public void goLogin() {
		Login login = new Login(email.getText(), password.getText());
		connectionController.login(login);
	}

	/**
	 * Prints out the login status (If the login succeeded or failed).
	 * @param login	The login information to check.
	 */
	public void validateLogin(Login login) {
		if(!login.getLoginStatus()) {
			loginError.setVisible(true);
		} else {
			try {
				Main.showMainView();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Starts the new user view where users can create new accounts.
	 * This method is called when users press 'Ny användare'.
	 * @throws IOException
	 */
	public void showNewUserView() throws IOException {
		Main.showNewUserView();
	}
}