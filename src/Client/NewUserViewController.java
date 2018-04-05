package Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import SharedResources.NewUser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		connectionController = ConnectionController.getInstance();
		connectionController.setNewUserViewController(this);
	}
	
	public void goNewUser() {
		NewUser newUser = new NewUser(email.getText(), password.getText(), firstName.getText(), lastName.getText());
		connectionController.newUser(newUser);
	}
	
	public void validateNewUser(NewUser newUser) {
		System.out.println(newUser.getNewUserStatus());
	}
	
	public void showLoginView() throws IOException {
		Main.showLoginView();
	}
}