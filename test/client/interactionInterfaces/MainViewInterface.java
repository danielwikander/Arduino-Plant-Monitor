package client.interactionInterfaces;

import client.TestFXBase;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import models.Plant;

import static client.JavaFXids.*;

/**
 * Interface for interacting with the MainView.
 *
 * @author Daniel Wikander
 */
public class MainViewInterface {

    private final TestFXBase driver;

    /**
     * Constructor that sets the base driver for user
     * interactions such as clicking on buttons etc.
     * @param driver    The driver to set.
     */
    public MainViewInterface(TestFXBase driver) {
        this.driver = driver;
    }

    /**
     * Clicks the add Plant button (+).
     * @return          The action.
     */
    public MainViewInterface clickAddPlant() {
        driver.clickOn(MAIN_ADD_BUTTON);
        return this;
    }

    /**
     * Clicks the modify plant button (cogwheel).
     * @return          The action.
     */
    public MainViewInterface clickModifyPlant() {
        driver.clickOn(MAIN_SETTINGS_BUTTON);
        return this;
    }

    /**
     * Attempts to click on the first plant in the
     * users plantlist.
     * @return          The action.
     */
    public MainViewInterface clickOnFirstPlant() {
        ListView list = driver.find(MAIN_PLANT_LIST);
        Platform.runLater(() -> {
            list.getSelectionModel().selectFirst();
        });
        return this;
    }

    /**
     * Clicks ona  specific plant from the users plantlist.
     * @param plantName The name of the plant to click on.
     * @return          The action.
     */
    public MainViewInterface clickOnSpecificPlant(String plantName) {
        ListView list = driver.find(MAIN_PLANT_LIST);
        Platform.runLater(() -> {
            ObservableList obsPlantList = list.getItems();
            for(int i = 0 ; i < obsPlantList.size() ; i++) {
                Plant plant = (Plant)obsPlantList.get(i);
                if (plant.getAlias().equals(plantName)) {
                    list.getSelectionModel().select(i);
                }
            }
        });
        return this;
    }

    /**
     * Attempts to click on the second plant in the
     * users plantlist.
     * @return          The action.
     */
    public MainViewInterface clickOnSecondPlant() {
        ListView list = driver.find(MAIN_PLANT_LIST);
        Platform.runLater(() -> {
            list.getSelectionModel().select(1);
        });
        return this;
    }
}
