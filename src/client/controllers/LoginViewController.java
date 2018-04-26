package client.controllers;

import client.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import models.Login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The login view that the user is presented with when they start the application.
 */
public class LoginViewController implements Initializable {

	/* FXML */
	@FXML
	private TextField emailTextField;
	@FXML
	private PasswordField passwordPasswordField;
	@FXML
	private Label loginErrorLabel;
	@FXML
	private Button loginButton;
	@FXML
	private Button newUserButton;
	
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
		loginErrorLabel.setVisible(false);
		loginButton.setDisable(true);
		initializeTextFieldListeners();

		// Attempts to log in if the user presses enter on the password field.
		passwordPasswordField.setOnKeyPressed(event -> {
			if(event.getCode() == KeyCode.ENTER){
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
        }
	}

	void initializeTextFieldListeners() {
	    emailTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.equals("") ) {
                    loginButton.setDisable(true);
                } else if (!passwordPasswordField.getText().isEmpty() ){
                    loginButton.setDisable(false);
                }
            }
        });
        passwordPasswordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.equals("")) {
                    loginButton.setDisable(true);
                } else if (!emailTextField.getText().isEmpty() ){
                    loginButton.setDisable(false);
                }
            }
        });
    }

	/**
	 * Attempts to log in the user.
	 * This method is called when users press 'Logga in'
	 */
	public void goLogin() {
		Login login = new Login(emailTextField.getText(), passwordPasswordField.getText());
		Main.setLoggedInUser(login.getEmail());
		connectionController.login(login);
	}

	/**
	 * Prints out the login status (If the login succeeded or failed).
	 * @param login	The login information to check.
	 */
	void validateLogin(Login login) {
		if(!login.isLoggedIn()) {
			loginErrorLabel.setVisible(true);
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