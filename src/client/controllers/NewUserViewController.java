package client.controllers;

import client.Main;
import models.NewUser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
	private TextField email;
	@FXML
	private PasswordField password;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private Button newUser;
	@FXML
	private Button back;
	
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
	}

	/**
	 * Forwards the users information to the {@link ConnectionController}.
	 * The ConnectionController will attempt to create a new user.
	 */
	public void goNewUser() {
		NewUser newUser = new NewUser(email.getText(), password.getText(), firstName.getText(), lastName.getText());
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
		// NOT FINISHED
		//TODO: Create 'autofill' function?
		System.out.println(newUser.getNewUserStatus());
	}

	/**
	 * Shows the login view.
	 * @throws IOException	Throws exception if the view cannot be presented.
	 */
	public void showLoginView() throws IOException {
		Main.showLoginView();
	}
}