package client.controllers;

import client.TestFXBase;
import client.interactionInterfaces.LoginViewInterface;
import client.interactionInterfaces.NewUserViewInterface;
import org.junit.Test;
import server.MainServer;

import static client.JavaFXids.*;
import static client.JavaFXids.NEW_USER_LAST_NAME;
import static org.junit.Assert.*;

/**
 * Testclass for running tests on the NewUserView.
 *
 * @author Daniel Wikander
 */
public class NewUserViewControllerTest extends TestFXBase {

    private LoginViewInterface lvi = new LoginViewInterface(this);
    private NewUserViewInterface nuvi = new NewUserViewInterface(this);

    /**
     * Constructor that starts a server in preparation for the tests.
     */
    public NewUserViewControllerTest() {
        String[] args = null;
        MainServer.main(args);
    }

    /**
     * Creates a valid new user and attempts to log in.
     * Asserts that the emailErrorLabel is not visible since
     * the label is only visible if the user entered invalid info.
     */
    @Test
    public void createValidNewUser() {
        lvi.pressNewUser();
        nuvi.createUser(RANDOM_NEW_USERNAME, RANDOM_NEW_PASSWORD, NEW_USER_FIRST_NAME, NEW_USER_LAST_NAME);
        sleep(1000); // Sleep to allow network communication to finish
        assertFalse("The email is already in use.", nuvi.getEmailAvailabilityStatus().isVisible());
    }

    /**
     * Creates an invalid new user and attempts to log in.
     * Asserts that the emailErrorLabel is visible since the
     * label is only visible if the user entered invalid info.
     */
    @Test
    public void createInvalidNewUser() {
        lvi.pressNewUser();
        nuvi.createUser(NEW_USER_INVALID_USERNAME, VALID_PASSWORD, NEW_USER_FIRST_NAME, NEW_USER_LAST_NAME);
        sleep(1000); // Sleep to allow network communication to finish
        assertTrue("The login was valid.", nuvi.getEmailAvailabilityStatus().isVisible());
    }
}