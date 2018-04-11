package Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import SharedResources.Login;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		connectionController = ConnectionController.getInstance();
		connectionController.setLoginViewController(this);
		loginError.setVisible(false);
	}
	
	public void goLogin() {
		Login login = new Login(email.getText(), password.getText());
		connectionController.login(login);
	}
	
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
	
	public void showNewUserView() throws IOException {
		Main.showNewUserView();
	}
}