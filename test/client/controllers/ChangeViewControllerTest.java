package client.controllers;

import client.Main;
import client.TestFXBase;
import client.interactionInterfaces.*;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import server.MainServer;

import static client.JavaFXids.*;
import static org.junit.Assert.*;

/**
 * Testclass for running tests on the ChangeView.
 *
 * @author Daniel Wikander
 */
public class ChangeViewControllerTest extends TestFXBase {

    /* These interfaces enable interactions with the JavaFX scenes */
    private LoginViewInterface lvi = new LoginViewInterface(this);
    private MainViewInterface mvi = new MainViewInterface(this);
    private GraphViewInterface gvi = new GraphViewInterface(this);
    private ChangeViewInterface cvi = new ChangeViewInterface(this);
    private StartViewInterface svi = new StartViewInterface(this);
    private AddViewInterface avi = new AddViewInterface(this);
    private ConfirmRemoveDialogInterface crdi = new ConfirmRemoveDialogInterface(this);

    /**
     * Constructor that starts a server in preparation for the tests.
     */
    public ChangeViewControllerTest() {
        String[] args = null;
        MainServer.main(args);
    }

    /**
     * Logs in and attempts to enter a plants settings view.
     */
    @Test
    public void enterSettingsView() {
        lvi.login(VALID_USERNAME,VALID_PASSWORD);
        sleep(2000);
        mvi.clickOnFirstPlant();
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
        sleep(500);
        mvi.clickModifyPlant();
        sleep(500);
        String newName = RANDOM_NEW_PLANTNAME;
        cvi.enterNewAlias(newName);
        cvi.clickSave();
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
        sleep(2000);
        String graphViewNameLabelText = gvi.getPlantNameLabel().getText();
        assertEquals("The names are not equal.", newName, graphViewNameLabelText);
    }

    /**
     * Logs in and attempts to change a plants notification setting.
     * Restarts the application and asserts that the setting has changed.
     */
    @Test
    public void changePlantNotificationSetting() throws Exception {
        lvi.login(VALID_USERNAME,VALID_PASSWORD);
        sleep(2000);
        mvi.clickOnFirstPlant();
        sleep(500);
        mvi.clickModifyPlant();
        sleep(500);
        // Removes "Inställningar för: " from the labels String.
        String plantName = cvi.getAliasLabel().getText().substring(19);
        boolean notify = cvi.getNotifierCheckBox().isSelected();
        if(notify) {
            cvi.unCheckNofitierCheckbox();
        } else {
            cvi.checkNofitierCheckbox();
        }
        cvi.clickSave();
        sleep(500);
        mvi.clickOnSecondPlant();
        sleep(500);
        mvi.clickOnSpecificPlant(plantName);
        sleep(500);
        mvi.clickModifyPlant();
        sleep(500);
        assertEquals("The notification hasn't changed.", !notify, cvi.getNotifierCheckBox().isSelected() );
    }

    ////////////////////////////////////////////// MISC TESTS //////////////////////////////////////////////////////////

    /**
     * Selects the first plant in the list and removes it.
     * Asserts that the plant is removed by attempting to
     * click it in the list, and then checking if the startview
     * is presented or not. The startview is presented if the
     * plant is removed and cannot be found in the list.
     */
    @Test
    public void removePlant() {
        lvi.login(VALID_USERNAME,VALID_PASSWORD);
        sleep(1500);
        mvi.clickOnFirstPlant();
        mvi.clickModifyPlant();
        String plantName = cvi.getAliasLabel().getText();
        cvi.clickRemove();
        mvi.clickOnSpecificPlant(plantName);
        assertNotNull(svi.getStartViewLabel());
    }


    /**
     * Adds and then removes a plant.
     * Asserts that the plant is removed from the list by
     * attempting to select the plant in the list.
     * If it cannot be selected the startview will be active.
     * @throws Exception
     */
    @Test
    public void addAndRemovePlantWithoutLoggingOut() throws Exception {
        lvi.login(VALID_USERNAME,VALID_PASSWORD);
        sleep(1500);
        mvi.clickAddPlant();
        String newPlantName = RANDOM_NEW_PLANTNAME;
        String newMacAddress = RANDOM_NEW_MAC_ADDRESS;
        avi.addNewPlant(newPlantName, newMacAddress);
        sleep(1500);
        mvi.clickOnSpecificPlant(newPlantName);
        mvi.clickModifyPlant();
        cvi.clickRemove();
        sleep(500);
        crdi.confirmRemove();
        sleep(500);
        mvi.clickOnSpecificPlant(newPlantName);
        assertNotNull(svi.getStartViewLabel());
    }

}