package client.interactionInterfaces;

import client.TestFXBase;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import static client.JavaFXids.*;

/**
 * Interface for interacting with the LoginView.
 *
 * @author Daniel Wikander
 */
public class LoginViewInterface {

    private final TestFXBase driver;

    /**
     * Constructor that sets the base driver for user
     * interactions such as clicking on buttons etc.
     *
     * @param driver The driver to set.
     */
    public LoginViewInterface(TestFXBase driver) {
        this.driver = driver;
    }

    /**
     * Logs in to the application.
     */
    public LoginViewInterface login(String username, String password) {
        clearInputFields();
        enterUsername(username).enterPassword(password);
        submit();
        return this;
    }

    /**
     * Enters the username in the username field.
     *
     * @param username The username to enter.
     * @return The action.
     */
    public LoginViewInterface enterUsername(String username) {
        driver.clickOn(USERNAME_FIELD_ID).write(username);
        return this;
    }

    /**
     * Enters the username in the username field.
     *
     * @param password The password to enter.
     * @return The action.
     */
    public LoginViewInterface enterPassword(String password) {
        driver.clickOn(PASSWORD_FIELD_ID).write(password);
        return this;
    }

    /**
     * Clicks on 'Logga in'
     *
     * @return The action.
     */
    public LoginViewInterface submit() {
        driver.clickOn(LOGIN_BUTTON_ID);
        return this;
    }

    /**
     * Returns the error label that is displayed
     * if the user could not log in.
     *
     * @return The action.
     */
    public Label getLoginStatus() {
        return driver.find(LOGIN_ERROR_LABEL);
    }

    /**
     * Clears the input fields.
     *
     * @return The action.
     */
    public LoginViewInterface clearInputFields() {
        clearUsername().clearPassword();
        return this;
    }

    /**
     * Clears the username field.
     *
     * @return The action.
     */
    public LoginViewInterface clearUsername() {
        TextField username = driver.find(USERNAME_FIELD_ID);
        username.clear();
        return this;
    }

    /**
     * Clears the password field.
     *
     * @return The action.
     */
    public LoginViewInterface clearPassword() {
        TextField password = driver.find(PASSWORD_FIELD_ID);
        password.clear();
        return this;
    }

    /**
     * Clicks on 'Ny användare'.
     *
     * @return The action.
     */
    public LoginViewInterface pressNewUser() {
        driver.clickOn(NEW_USER_BUTTON_ID);
        return this;
    }

    /**
     * Checks the 'Kom ihåg mig' checkbox.
     *
     * @return The action.
     */
    public LoginViewInterface checkRememberMe() {
        CheckBox rememberMe = driver.find(REMEMBER_USER_CHECKBOX_ID);
        if (!rememberMe.isSelected()) {
            driver.clickOn(REMEMBER_USER_CHECKBOX_ID);
        }
        return this;
    }

    /**
     * Unchecks the 'Kom ihåg mig' checkbox.
     *
     * @return The action.
     */
    public LoginViewInterface uncheckRememberMe() {
        CheckBox rememberMe = driver.find(REMEMBER_USER_CHECKBOX_ID);
        if (rememberMe.isSelected()) {
            driver.clickOn(REMEMBER_USER_CHECKBOX_ID);
        }
        return this;
    }

}
