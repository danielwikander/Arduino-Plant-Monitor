package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import models.Plant;

/**
 * The controller for the Change View.
 * This controller handles the logic for the view that the user is
 * presented with then they wish to change their settings for a plant.
 */
public class ChangeViewController {
    @FXML
    WebView wikiWebView;
    @FXML
    ChoiceBox plantChoiceBox;
    @FXML
    TextField macAddressTextField;
    @FXML
    TextField plantAliasTextField;
    @FXML
    CheckBox plantNotifierCheckBox;
    @FXML
    Button saveButton;
    @FXML
    Button cancelButton;
    @FXML
    Label settingsForLabel;
    @FXML
    HBox topPanelHBox;

    /**
     * Initializes the view.
     * Sets the background color of the top panel.
     * //TODO: Skapa ny vy.
     * @param plant
     */
    public void initialize(Plant plant) {
        topPanelHBox.setStyle("-fx-background-color: #a8cb9c;");
        settingsForLabel.setText("Inställningar för: " + plant.getAlias());
        WebEngine webEngine = wikiWebView.getEngine();
        webEngine.load("https://sv.m.wikipedia.org/wiki/Växt");
    }
}
