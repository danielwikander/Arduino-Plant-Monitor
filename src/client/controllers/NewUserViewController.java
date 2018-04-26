package client.controllers;

import client.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.NewUser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller which handles the logic for the new user view that is presented
 * when a user wishes to create a new user.
 */
public class NewUserViewController implements Initializable {

	/* FXML */
	@FXML
	private TextField emailTextField;
	@FXML
	private PasswordField passwordPasswordField;
	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField lastNameTextField;
	@FXML
	private Label emailErrorLabel;
	@FXML
	private Button newUserButton;
	@FXML
	private Button backButton;
	
	private ConnectionController connectionController;

	/**
	 * Initializes the view.
	 * @param url 	The location of the FXML document to use.
	 * @param rb 	Resources for the JavaFX view.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		connectionController = ConnectionController.getInstance();
		connectionController.setNewUserViewController(this);
		emailErrorLabel.setVisible(false);
	}

	/**
	 * Forwards the users information to the {@link ConnectionController}.
	 * The ConnectionController will attempt to create a new user.
	 */
	public void goNewUser() {
		NewUser newUser = new NewUser(emailTextField.getText(), passwordPasswordField.getText(), firstNameTextField.getText(), lastNameTextField.getText());
		Main.setLoggedInUser(newUser.getEmail());
		connectionController.newUser(newUser);
		
	}

	/**
	 * Validates the new users status in the database.
	 * If the new user was created in the database,
	 * the login view will automatically fill in the
	 * login information for the user.
	 * @param newUser	The user to check.
	 */
	void validateNewUser(NewUser newUser) {
		if(newUser.getNewUserStatus()) {
			Main.showMainView();
		} else {
			emailErrorLabel.setVisible(true);
		}
	}

	/**
	 * Shows the login view.
	 * @throws IOException	Throws exception if the view cannot be presented.
	 */
	public void showLoginView() throws IOException {
		Main.showLoginView();
	}
}