package client.controllers;

import client.Main;
import models.Login;
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

	/* FXML */
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
	
	private ConnectionController connectionController;

	/**
	 * Initializes the login view.
	 * @param url 	The location of the FXML document to use.
	 * @param rb 	Resources for the JavaFX view.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
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
	void validateLogin(Login login) {
		if(!login.getIsLoggedIn()) {
			loginError.setVisible(true);
		} else {
			Main.showMainView();
		}
	}

	/**
	 * Starts the new user view where users can create new accounts.
	 * This method is called when users press 'Ny användare'.
	 * @throws IOException	Throws exception when FXML view cannot be found.
	 */
	public void showNewUserView() throws IOException {
		Main.showNewUserView();
	}
}