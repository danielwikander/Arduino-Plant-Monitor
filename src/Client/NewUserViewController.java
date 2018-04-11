package Client;

import SharedResources.NewUser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The view that is presented when a user wishes to create a new user.
 */
public class NewUserViewController implements Initializable {

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
	 * @param location 	//TODO: Explain FXML stuff?
	 * @param resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
	 * Prints the new users status in the database.
	 * @param newUser	The user to check.
	 */
	public void validateNewUser(NewUser newUser) {
		System.out.println(newUser.getNewUserStatus());
		//TODO: Create 'autofill' function?
	}

	/**
	 * Shows the login view.
	 * @throws IOException	Throws exception if the view cannot be presented.
	 */
	public void showLoginView() throws IOException {
		Main.showLoginView();
	}
}