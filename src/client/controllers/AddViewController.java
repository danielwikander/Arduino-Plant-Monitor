package client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AddViewController implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        WebEngine webEngine = wikiWebView.getEngine();
        webEngine.load("https://sv.m.wikipedia.org/wiki/Tomat");
    }
}
