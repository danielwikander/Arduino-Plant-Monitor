package client.controllers;

import client.Main;
import client.TestFXBase;
import client.interactionInterfaces.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import server.MainServer;

import static client.JavaFXids.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for running tests on the LoginView.
 *
 * @author Daniel Wikander
 */
public class LoginViewControllerTest extends TestFXBase {

    /* These interfaces enable interactions with the JavaFX scenes */
    private LoginViewInterface lvi = new LoginViewInterface(this);
    private StartViewInterface svi = new StartViewInterface(this);

    /**
     * Constructor that starts a Server so that the tests can without network errors.
     */
    public LoginViewControllerTest() {
        String[] args = null;
        MainServer.main(args);
    }

    /**
     * Runs before each individual test.
     * Used for setting up a testing environment.
     * Isn't currently being used.
     */
    @Before
    public void beforeEachTest() { }

    /**
     * Tries to log in with a valid username.
     * Asserts true if the loginstatuslabel is visible,
     * since the label only becomes visible if the user
     * has successfully logged in.
     */
    @Test
    public void loginValid() {
        lvi.clearInputFields();
        lvi.enterUsername(VALID_USERNAME);
        lvi.enterPassword(VALID_PASSWORD);
        lvi.submit();
        assertFalse("The login was not valid.", lvi.getLoginStatus().isVisible());
    }

    /**
     * Tries to log in with an invalid username.
     * Asserts true if the loginstatuslabel is visible,
     * since the label only becomes visible if the user
     * has successfully logged in.
     */
    @Test
    public void loginInvalidUser() {
        lvi.clearInputFields();
        lvi.enterUsername(INVALID_USERNAME);
        lvi.enterPassword(VALID_USERNAME);
        lvi.submit();
        sleep(1000); // Sleep to allow network communication to finish
        assertTrue("The login was valid.", lvi.getLoginStatus().isVisible());
    }

    /**
     * Tries to log in with an invalid password.
     * Asserts true if the loginstatuslabel is visible,
     * since the label only becomes visible if the user
     * has successfully logged in.
     */
    @Test
    public void loginInvalidPassword() {
        lvi.clearInputFields();
        lvi.enterUsername(VALID_USERNAME);
        lvi.enterPassword(INVALID_PASSWORD);
        lvi.submit();
        sleep(1000); // Sleep to allow network communication to finish
        assertTrue("The login was valid.", lvi.getLoginStatus().isVisible());
    }

    /**
     * Checks the 'Kom ihåg mig' button and logs in with a valid user.
     * Restarts the application and immediately attempts to
     * log in with the saved user information.
     */
    @Test
    public void logInUsingRememberMeFunction() throws Exception {
        lvi.checkRememberMe();
        lvi.login(VALID_USERNAME, VALID_PASSWORD);
        sleep(700);
        // Closes application
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        // Launches application again
        ApplicationTest.launch(Main.class);
        sleep(700);
        lvi.submit();
        sleep(1000);
        assertTrue("The startview did not successfully load", svi.getStartViewLabel().isVisible());
    }

}
