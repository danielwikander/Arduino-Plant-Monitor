package client.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import client.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import models.User;

/**
 * The login view that the user is presented with when they start the
 * application.
 * @author David, Daniel.
 */
public class LoginViewController implements Initializable {

	@FXML
	private Label titleLabel;
	@FXML
	private TextField emailTextField;
	@FXML
	private PasswordField passwordPasswordField;
	@FXML
	private Label loginErrorLabel;
	@FXML
	private CheckBox rememberUserCheckBox;
	@FXML
	private Button loginButton;
	@FXML
	private Button newUserButton;
	@FXML
	private VBox topPanelVBox;
	private ConnectionController connectionController;

	/**
	 * Initializes the login view.
	 * 
	 * @param url
	 *            The location of the FXML document to use.
	 * @param rb
	 *            Resources for the JavaFX view.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// titleLabel.setGraphic(new ImageView(titleIcon));
		topPanelVBox.setStyle("-fx-background-color: #a8cb9c;");
		connectionController = ConnectionController.getInstance();
		connectionController.setLoginViewController(this);
		loginErrorLabel.setVisible(false);
		loginButton.setDisable(true);
		initializeTextFieldListeners();

		// Attempts to log in if the user presses enter on the password field.
		passwordPasswordField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				goLogin();
			}
		});
		if (!connectionController.isServerAvailable()) {
			loginErrorLabel.setText("Server ej tillgänglig");
			loginErrorLabel.setVisible(true);
			loginButton.setDisable(true);
			newUserButton.setDisable(true);
			passwordPasswordField.setDisable(true);
			emailTextField.setDisable(true);
			rememberUserCheckBox.setDisable(true);
		} else {
			readUserData();
		}
	}

	/**
	 * Retrieves the last logged in user and populates its values in the emailTextField and passwordPasswordTextField.
	 */
	private void readUserData() {
		try (BufferedReader br = new BufferedReader(new FileReader("files/userdata.txt"))) {
			if (br.readLine().equals("1")) {
				rememberUserCheckBox.setSelected(true);
				emailTextField.setText(br.readLine());
				passwordPasswordField.setText(br.readLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes the current logged in user to the userdata.txt file.
	 */
	private void writeUserData(){
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("files/userdata.txt"))){
			if (rememberUserCheckBox.isSelected()) {
				bw.write(1 + "\n");
				bw.write(emailTextField.getText() + "\n");
				bw.write(passwordPasswordField.getText());
			} else {
				bw.write(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes textfield listeners that check if the user has written
	 * anything in the email and password fields. The user has to write
	 * something in the fields to be able to log in.
	 */
	void initializeTextFieldListeners() {
		emailTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
				if (t1.equals("")) {
					loginButton.setDisable(true);
				} else if (!passwordPasswordField.getText().isEmpty()) {
					loginButton.setDisable(false);
				}
			}
		});
		passwordPasswordField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
				if (t1.equals("")) {
					loginButton.setDisable(true);
				} else if (!emailTextField.getText().isEmpty()) {
					loginButton.setDisable(false);
				}
			}
		});
	}

	/**
	 * Attempts to log in the user. This method is called when users press
	 * 'Logga in'
	 */
	public void goLogin() {
		User user = new User(emailTextField.getText(), passwordPasswordField.getText());
		Main.setLoggedInUser(user.getEmail());
		connectionController.login(user);
	}

	/**
	 * Prints out the login status (If the login succeeded or failed).
	 * 
	 * @param user
	 *            The login information to check.
	 */
	void validateLogin(User user) {
		if (!user.isLoggedIn()) {
			loginErrorLabel.setVisible(true);
		} else {
			writeUserData();
			Main.showMainView();
		}
	}

	/**
	 * Starts the new user view where users can create new accounts. This method
	 * is called when users press 'Ny användare'.
	 * 
	 * @throws IOException
	 *             Throws exception when FXML view cannot be found.
	 */
	public void showNewUserView() throws IOException {
		Main.showNewUserView();
	}
}