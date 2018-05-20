package client.controllers;

import client.TestFXBase;
import client.interactionInterfaces.*;
import org.junit.Test;
import server.MainServer;

import static client.JavaFXids.VALID_PASSWORD;
import static client.JavaFXids.VALID_USERNAME;
import static org.junit.Assert.*;

/**
 * Testclass for running tests on the GraphView.
 *
 * @author Daniel Wikander
 */
public class GraphViewControllerTest extends TestFXBase {

    /* These interfaces enable interactions with the JavaFX scenes */
    private LoginViewInterface lvi = new LoginViewInterface(this);
    private MainViewInterface mvi = new MainViewInterface(this);
    private GraphViewInterface gvi = new GraphViewInterface(this);

    /**
     * Constructor that starts a server in preparation for the tests.
     */
    public GraphViewControllerTest() {
        String[] args = null;
        MainServer.main(args);
    }

    /**
     * Logs in and attempts to select a plant from the users PlantList.
     * The test asserts that the plants graph is presented.
     */
    @Test
    public void enterPlantsGraphView() {
        lvi.login(VALID_USERNAME,VALID_PASSWORD);
        sleep(2000);
        mvi.clickOnFirstPlant();
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
        sleep(500);
        String firstPlantName = gvi.getPlantNameLabel().getText();
        sleep(500);
        mvi.clickOnSecondPlant();
        sleep(500);
        String secondPlantName = gvi.getPlantNameLabel().getText();
        assertNotEquals("The selected plants are the same.", firstPlantName, secondPlantName);
    }
}