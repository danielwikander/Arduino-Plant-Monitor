package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
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
    @FXML
    HBox topPanelHBox;



    public void initialize(Plant plant) {
        topPanelHBox.setStyle("-fx-background-color: #a8cb9c;");
        settingsForLabel.setText("Inställningar för: " + plant.getAlias());
        WebEngine webEngine = wikiWebView.getEngine();
        webEngine.load("https://sv.m.wikipedia.org/wiki/Växt");
    }
}
