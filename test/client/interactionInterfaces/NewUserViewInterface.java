package client.interactionInterfaces;

import client.TestFXBase;
import javafx.scene.control.Label;

import static client.JavaFXids.*;

/**
 * Interface for interacting with the NewUserView.
 *
 * @author Daniel Wikander
 */
public class NewUserViewInterface {

    private final TestFXBase driver;

    /**
     * Constructor that sets the base driver for user
     * interactions such as clicking on buttons etc.
     * @param driver    The driver to set.
     */
    public NewUserViewInterface(TestFXBase driver) {
        this.driver = driver;
    }

    /**
     * Enters info in all fields and clicks 'skapa ny användare'.
     * @param username  The username to enter.
     * @param password  The password to enter.
     * @param firstname The first name to enter.
     * @param lastname  The last name to enter.
     * @return          The action.
     */
    public NewUserViewInterface createUser(String username, String password, String firstname, String lastname) {
        driver.clickOn(NEW_USER_EMAIL_FIELD).write(username);
        driver.clickOn(NEW_USER_PASSWORD_FIELD).write(password);
        driver.clickOn(NEW_USER_FIRST_NAME_FIELD).write(firstname);
        driver.clickOn(NEW_USER_LAST_NAME_FIELD).write(lastname);
        submit();
        return this;
    }

    /**
     * Enters a username in the username field.
     * @param username  The username to enter.
     * @return          The action.
     */
    public NewUserViewInterface enterUsername(String username) {
        driver.clickOn(NEW_USER_EMAIL_FIELD).write(username);
        return this;
    }

    /**
     * Enters a password in the password field.
     * @param password  The username to enter.
     * @return          The action.
     */
    public NewUserViewInterface enterPassword(String password) {
        driver.clickOn(NEW_USER_PASSWORD_FIELD).write(password);
        return this;
    }

    /**
     * Clicks on the 'Ny användare' button.
     * @return          The action.
     */
    public NewUserViewInterface submit() {
        driver.clickOn(NEW_USER_SUBMIT_BUTTON);
        return this;
    }

    /**
     * Returns the error label that is displayed
     * when the user enters an invalid email.
     * @return          The action.
     */
    public Label getEmailAvailabilityStatus() {
        return driver.find(NEW_USER_EMAIL_ERROR_FIELD);
    }

    /**
     * Clicks on the 'Tillbaka' button.
     * @return          The action.
     */
    public NewUserViewInterface back() {
        driver.clickOn(NEW_USER_BACK_BUTTON);
        return this;
    }
}
