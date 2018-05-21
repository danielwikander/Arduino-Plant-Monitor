package client.controllers;

import client.TestFXBase;
import client.interactionInterfaces.AddViewInterface;
import client.interactionInterfaces.LoginViewInterface;
import client.interactionInterfaces.MainViewInterface;
import client.interactionInterfaces.StartViewInterface;
import javafx.scene.control.TextField;
import org.junit.Test;
import server.MainServer;

import static client.JavaFXids.VALID_PASSWORD;
import static client.JavaFXids.VALID_USERNAME;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Testclass for running tests on the StartViewController.
 *
 * @author Daniel Wikander
 */
public class StartViewControllerTest extends TestFXBase {

    private LoginViewInterface lvi = new LoginViewInterface(this);
    private StartViewInterface svi = new StartViewInterface(this);
    private MainViewInterface mvi = new MainViewInterface(this);
    private AddViewInterface avi = new AddViewInterface(this);

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

    /**
     * Verifies that a user can go from the startview
     * to the addview without clicking on a plant.
     */
    @Test
    public void goFromStartViewToAddView() {
        lvi.login(VALID_USERNAME, VALID_PASSWORD);
        sleep(1000);
        mvi.clickAddPlant();
        sleep(500);
        TextField aliasTextField = avi.getAliasTextField();
        assertNotNull(aliasTextField);
    }

    /**
     * Verifies that a user can't go to the changeview
     * from the startview without clicking on a plant
     * in the list first.
     */
    @Test
    public void verifyCantEnterChangeViewFromStartView() {
        lvi.login(VALID_USERNAME, VALID_PASSWORD);
        sleep(1000);
        mvi.clickModifyPlant();
        sleep(500);
        assertTrue("The startview did not successfully load", svi.getStartViewLabel().isVisible());
    }

}