package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import models.Plant;

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


    public void initialize(Plant plant) {
        settingsForLabel.setText("Inställningar för: " + plant.getAlias());
        WebEngine webEngine = wikiWebView.getEngine();
        webEngine.load("https://sv.m.wikipedia.org/wiki/Växt");
    }
}
