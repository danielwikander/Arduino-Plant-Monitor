package client.controllers;

import client.TestFXBase;
import client.interactionInterfaces.LoginViewInterface;
import client.interactionInterfaces.NewUserViewInterface;
import client.interactionInterfaces.StartViewInterface;
import org.junit.Test;
import server.MainServer;

import static client.JavaFXids.VALID_PASSWORD;
import static client.JavaFXids.VALID_USERNAME;
import static org.junit.Assert.assertTrue;

/**
 * Testclass for running tests on the StartViewController.
 *
 * @author Daniel Wikander
 */
public class StartViewControllerTest extends TestFXBase {

    private LoginViewInterface lvi = new LoginViewInterface(this);
    private NewUserViewInterface nuvi = new NewUserViewInterface(this);
    private StartViewInterface svi = new StartViewInterface(this);

    /**
     * Constructor that starts a server in preparation for the tests.
     */
    public StartViewControllerTest() {
        String[] args = null;
        MainServer.main(args);
    }

    /**
     * Attempts to log in with valid info.
     * Asserts that the startView opens after
     * the user has successfully logged in.
     */
    @Test
    public void logInToStartView() {
        lvi.login(VALID_USERNAME,VALID_PASSWORD);
        sleep(1000);
        assertTrue("The startview did not successfully load", svi.getStartViewLabel().isVisible());
    }

}