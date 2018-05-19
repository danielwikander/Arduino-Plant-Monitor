package client.controllers;

import client.Main;
import client.TestFXBase;
import client.interactionInterfaces.*;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import server.MainServer;

import static client.JavaFXids.*;
import static org.junit.Assert.*;

/**
 * The testing class, all white-box tests within the project are run in this class.
 * It is named LoginViewControllerTest so that JUnit will connect it to the loginView.
 * This is important because the loginView is the first scene that is loaded when
 * the application is run. Initial tests run within the loginView, and the following
 * tests are run using interfaces created for each scene.
 *
 * @author Daniel Wikander
 */
public class LoginViewControllerTest extends TestFXBase {

    /* These interfaces enable interactions with the JavaFX scenes */
    private LoginViewInterface lvi = new LoginViewInterface(this);
    private NewUserViewInterface nuvi = new NewUserViewInterface(this);
    private StartViewInterface svi = new StartViewInterface(this);
    private MainViewInterface mvi = new MainViewInterface(this);
    private GraphViewInterface gvi = new GraphViewInterface(this);
    private ChangeViewInterface cvi = new ChangeViewInterface(this);
    private AddViewInterface avi = new AddViewInterface(this);
    private ConfirmRemoveDialogInterface crdi = new ConfirmRemoveDialogInterface(this);

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

    ///////////////////////////////////////////////// LOGIN TESTS //////////////////////////////////////////////////////

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
        ensureEventQueueComplete();
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
        ensureEventQueueComplete();
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
        ensureEventQueueComplete();
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

    ////////////////////////////////////////////////// NEW USER TESTS //////////////////////////////////////////////////

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

    /////////////////////////////////////////////// STARTVIEW TEST /////////////////////////////////////////////////////

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


    /////////////////////////////////////////////// GRAPHVIEW TESTS ////////////////////////////////////////////////////

    /**
     * Logs in and attempts to select a plant from the users PlantList.
     * The test asserts that the plants graph is presented.
     */
    @Test
    public void enterPlantsGraphView() {
        lvi.login(VALID_USERNAME,VALID_PASSWORD);
        sleep(2000);
        ensureEventQueueComplete();
        mvi.clickOnFirstPlant();
        ensureEventQueueComplete();
        sleep(500);
        assertNotNull("The graph is null.", gvi.getGraph() != null);
    }

    /**
     * Logs in and attempts to select a plant from the users plant list.
     * Then attempts to select the next plant from the list.
     * The test asserts that the plants graph is presented
     */
    @Test
    public void changeToOtherPlant() {
        lvi.login(VALID_USERNAME,VALID_PASSWORD);
        sleep(2000);
        mvi.clickOnFirstPlant();
        ensureEventQueueComplete();
        sleep(500);
        mvi.clickOnSecondPlant();
        ensureEventQueueComplete();
        sleep(500);
        assertNotNull("The graph is null.", gvi.getGraph() != null);
    }

    ////////////////////////////////////////////// CHANGEVIEW TESTS ////////////////////////////////////////////////////

    /**
     * Logs in and attempts to enter a plants settings view.
     */
    @Test
    public void enterSettingsView() {
        lvi.login(VALID_USERNAME,VALID_PASSWORD);
        sleep(2000);
        mvi.clickOnFirstPlant();
        ensureEventQueueComplete();
        sleep(500);
        mvi.clickModifyPlant();
        Label nameLabel = cvi.getAliasLabel();
        assertNotNull("The plant name label is null", nameLabel.getText());
    }

    /**
     * Logs in and attempts to change a plants name.
     * Restarts the application and asserts that
     * the name has changed.
     */
    @Test
    public void changePlantName() throws Exception {
        lvi.login(VALID_USERNAME,VALID_PASSWORD);
        sleep(2000);
        mvi.clickOnFirstPlant();
        ensureEventQueueComplete();
        sleep(500);
        mvi.clickModifyPlant();
        sleep(500);
        String newName = RANDOM_NEW_PLANTNAME;
        cvi.enterNewAlias(newName);
        cvi.clickSave();
        ensureEventQueueComplete();
        sleep(500);
        // Closes application
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        sleep(3000);
        // Launches application again
        ApplicationTest.launch(Main.class);
        lvi.login(VALID_USERNAME,VALID_PASSWORD);
        sleep(2000);
        mvi.clickOnSpecificPlant(newName);
        ensureEventQueueComplete();
        sleep(2000);
        String graphViewNameLabelText = gvi.getPlantNameLabel().getText();
        assertEquals("The names are not equal.", newName, graphViewNameLabelText);
    }

    /////////////////////////////////////////////// ADDVIEW TESTS //////////////////////////////////////////////////////

    /**
     * Adds a new plant, then selects the plant from the plantlist.
     * The test asserts that the name & mac field in the changeview are correct.
     * @throws Exception
     */
    @Test
    public void addNewPlant() throws Exception {
        lvi.login(VALID_USERNAME,VALID_PASSWORD);
        sleep(2000);
        mvi.clickAddPlant();
        ensureEventQueueComplete();
        sleep(500);
        String newPlantName = RANDOM_NEW_PLANTNAME;
        String newMacAddress = RANDOM_NEW_MAC_ADDRESS;
        avi.addNewPlant(newPlantName, newMacAddress);
        ensureEventQueueComplete();
        sleep(2000);
        mvi.clickOnSpecificPlant(newPlantName);
        mvi.clickModifyPlant();
        ensureEventQueueComplete();
        sleep(2000);
        String changeViewNameLabelText = cvi.getAliasLabel().getText();
        String changeViewMacLabelText = cvi.getMacLabel().getText();
        assertEquals("The names are not equal.", "Inställningar för: " + newPlantName, changeViewNameLabelText);
        assertEquals("The MAC-addresses are not equal.", newMacAddress, changeViewMacLabelText);
    }

    ////////////////////////////////////////////// MISC TESTS //////////////////////////////////////////////////////////

    /**
     * Adds and then removes a plant.
     * Asserts that the plant is removed from the list by
     * attempting to select the plant in the list.
     * If it cannot be selected the startview will be active.
     * @throws Exception
     */
    @Test
    public void addAndRemovePlant() throws Exception {
        lvi.login(VALID_USERNAME,VALID_PASSWORD);
        sleep(2000);
        mvi.clickAddPlant();
        ensureEventQueueComplete();
        sleep(500);
        String newPlantName = RANDOM_NEW_PLANTNAME;
        String newMacAddress = RANDOM_NEW_MAC_ADDRESS;
        avi.addNewPlant(newPlantName, newMacAddress);
        ensureEventQueueComplete();
        sleep(2000);
        mvi.clickOnSpecificPlant(newPlantName);
        mvi.clickModifyPlant();
        ensureEventQueueComplete();
        sleep(1000);
        cvi.clickRemove();
        ensureEventQueueComplete();
        crdi.confirmRemove();
        sleep(2000);
        ensureEventQueueComplete();
        mvi.clickOnSpecificPlant(newPlantName);
        sleep(2000);
        assertNotNull(svi.getStartViewLabel());
    }

}
