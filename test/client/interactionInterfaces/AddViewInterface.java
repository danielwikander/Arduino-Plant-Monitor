package client.interactionInterfaces;

import client.TestFXBase;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import static client.JavaFXids.*;

/**
 * Interface for interacting with the AddView.
 *
 * @author Daniel Wikander
 */
public class AddViewInterface {

    private final TestFXBase driver;

    /**
     * Constructor that sets the base driver for user
     * interactions such as clicking on buttons etc.
     * @param driver    The driver to set.
     */
    public AddViewInterface(TestFXBase driver) {
        this.driver = driver;
    }

    /**
     * Types text in the alias field.
     * @param alias     The text to type.
     * @return          The action.
     */
    public AddViewInterface typeInAlias(String alias) {
        driver.clickOn(ADDVIEW_PLANT_ALIAS_FIELD).write(alias);
        return this;
    }

    /**
     * Clicks the save button.
     * @return          The action.
     */
    public AddViewInterface saveNewPlant() {
        driver.clickOn(ADDVIEW_SAVE_BUTTON);
        return this;
    }

    /**
     * Types text in the mac-address field.
     * @param mac       The text to type.
     * @return          The action.
     */
    public AddViewInterface typeinMac(String mac) {
        driver.clickOn(ADDVIEW_MAC_ADDRESS_FIELD).write(mac);
        return this;
    }

    /**
     * Adds a new plant.
     * @param alias     The alias to type in.
     * @param mac       The mac-address to type in.
     * @return          The action.
     */
    public AddViewInterface addNewPlant(String alias, String mac) {
        typeInAlias(alias);
        typeinMac(mac);
        saveNewPlant();
        return this;
    }

    public TextField getAliasTextField() {
        return driver.find(ADDVIEW_PLANT_ALIAS_FIELD);
    }

//    /**
//     * Returns the wiki webview engine.
//     * @return          The webview engine.
//     */
//    public WebEngine getWebEngine() {
//        return driver.find(ADDVIEW_WIKI_WEB_VIEW);
//    }

}
