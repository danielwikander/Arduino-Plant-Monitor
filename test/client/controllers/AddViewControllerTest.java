package client.controllers;

import client.TestFXBase;
import client.interactionInterfaces.*;
import org.junit.Test;
import server.MainServer;

import static client.JavaFXids.*;
import static org.junit.Assert.*;

/**
 * Testclass for running tests on the AddView.
 *
 * @author Daniel Wikander
 */
public class AddViewControllerTest extends TestFXBase {

    /* These interfaces enable interactions with the JavaFX scenes */
    private LoginViewInterface lvi = new LoginViewInterface(this);
    private MainViewInterface mvi = new MainViewInterface(this);
    private GraphViewInterface gvi = new GraphViewInterface(this);
    private ChangeViewInterface cvi = new ChangeViewInterface(this);
    private AddViewInterface avi = new AddViewInterface(this);

    /**
     * Constructor that starts a server in preparation for the tests.
     */
    public AddViewControllerTest() {
        String[] args = null;
        MainServer.main(args);
    }

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
        sleep(500);
        String newPlantName = RANDOM_NEW_PLANTNAME;
        String newMacAddress = RANDOM_NEW_MAC_ADDRESS;
        avi.addNewPlant(newPlantName, newMacAddress);
        sleep(2000);
        mvi.clickOnSpecificPlant(newPlantName);
        mvi.clickModifyPlant();
        sleep(2000);
        String changeViewNameLabelText = cvi.getAliasLabel().getText();
        String changeViewMacLabelText = cvi.getMacLabel().getText();
        assertEquals("The names are not equal.", "Inställningar för: " + newPlantName, changeViewNameLabelText);
        assertEquals("The MAC-addresses are not equal.", newMacAddress, changeViewMacLabelText);
    }

}