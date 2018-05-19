package client.interactionInterfaces;

import client.TestFXBase;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import static client.JavaFXids.*;

/**
 * Interface to interact with the ChangeView.
 *
 * @author Daniel Wikander
 */
public class ChangeViewInterface {

    private final TestFXBase driver;

    /**
     * Constructor that sets the base driver for user
     * interactions such as clicking on buttons etc.
     * @param driver    The driver to set.
     */
    public ChangeViewInterface(TestFXBase driver) {
        this.driver = driver;
    }

    /**
     * Clicks on the save button.
     * @return          The action.
     */
    public ChangeViewInterface clickSave() {
        driver.clickOn(CHANGEVIEW_SAVE_BUTTON);
        return this;
    }

    /**
     * Clicks on the remove button.
     * @return          The action.
     */
    public ChangeViewInterface clickRemove() {
        driver.clickOn(CHANGEVIEW_REMOVE_BUTTON);
        return this;
    }

    /**
     * Clears the alias field.
     * @return          The action.
     */
    public ChangeViewInterface clearInputField() {
        driver.clickOn(CHANGEVIEW_PLANT_ALIAS_FIELD);
        for(int i = 0; i < 30; i++) {
            driver.press(KeyCode.RIGHT);
        }
        driver.eraseText(30);
        return this;
    }

    /**
     * Types in new alias.
     * @param newAlias  The alias to type.
     * @return          The action.
     */
    public ChangeViewInterface enterNewAlias(String newAlias) {
        clearInputField();
        driver.clickOn(CHANGEVIEW_PLANT_ALIAS_FIELD).write(newAlias);
        return this;
    }

    /**
     * Returns the alias label with the plants name.
     * @return          The alias label.
     */
    public Label getAliasLabel() {
        return driver.find(CHANGEVIEW_SETTINGS_FOR_LABEL);
    }

    /**
     * Returns the mac-address textfield.
     * @return          The mac-address textfield.
     */
    public TextField getMacLabel() {
        return driver.find(CHANGEVIEW_MAC_FIELD);
    }

}
