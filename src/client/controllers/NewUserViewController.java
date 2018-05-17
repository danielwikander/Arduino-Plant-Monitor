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
import javafx.scene.layout.VBox;
import models.NewUserRequest;

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
	@FXML
	private VBox topPanelVBox;
	
	private ConnectionController connectionController;

	/**
	 * Initializes the view.
	 * @param url 	The location of the FXML document to use.
	 * @param rb 	Resources for the JavaFX view.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		topPanelVBox.setStyle("-fx-background-color: #a8cb9c;");
		connectionController = ConnectionController.getInstance();
		connectionController.setNewUserViewController(this);
		emailErrorLabel.setVisible(false);
		newUserButton.setDisable(true);
		initializeTextFieldListeners();
	}

	/**
	 * Forwards the users information to the {@link ConnectionController}.
	 * The ConnectionController will attempt to create a new user.
	 */
	public void goNewUser() {
		NewUserRequest newUserRequest = new NewUserRequest(emailTextField.getText(), passwordPasswordField.getText(), firstNameTextField.getText(), lastNameTextField.getText());
		Main.setLoggedInUser(newUserRequest.getUser().getEmail());
		connectionController.newUser(newUserRequest);
		
	}

	/**
	 * Validates the new users status in the database.
	 * If the new user was created in the database,
	 * the login view will automatically fill in the
	 * login information for the user.
	 * @param newUserRequest	The user to check.
	 */
	void validateNewUser(NewUserRequest newUserRequest) {
		if(newUserRequest.getNewUserStatus()) {
			Main.showMainView();
		} else {
			emailErrorLabel.setVisible(true);
		}
	}
	
	/**
	 * Initializes textfield listeners that check if the user has written
	 * anything in the email, password, first name and last name textfields. The user has to write
	 * something in all of these fields to be able to create a new user.
	 */
	void initializeTextFieldListeners() {
		emailTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
				if (t1.equals("")) {
					newUserButton.setDisable(true);
				} else if (!passwordPasswordField.getText().isEmpty() && !firstNameTextField.getText().isEmpty() && !lastNameTextField.getText().isEmpty()) {
					newUserButton.setDisable(false);
				}
			}
		});
		passwordPasswordField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
				if (t1.equals("")) {
					newUserButton.setDisable(true);
				} else if (!emailTextField.getText().isEmpty() && !firstNameTextField.getText().isEmpty() && !lastNameTextField.getText().isEmpty()) {
					newUserButton.setDisable(false);
				}
			}
		});
		firstNameTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
				if (t1.equals("")) {
					newUserButton.setDisable(true);
				} else if (!emailTextField.getText().isEmpty() && !passwordPasswordField.getText().isEmpty() && !lastNameTextField.getText().isEmpty()) {
					newUserButton.setDisable(false);
				}
			}
		});
		lastNameTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
				if (t1.equals("")) {
					newUserButton.setDisable(true);
				} else if (!emailTextField.getText().isEmpty() && !passwordPasswordField.getText().isEmpty() && !firstNameTextField.getText().isEmpty()) {
					newUserButton.setDisable(false);
				}
			}
		});
	}

	/**
	 * Shows the login view.
	 * @throws IOException	Throws exception if the view cannot be presented.
	 */
	public void showLoginView() throws IOException {
		Main.showLoginView();
	}
}