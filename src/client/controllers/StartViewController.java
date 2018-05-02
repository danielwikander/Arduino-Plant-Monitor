package client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The view the user is presented with when they have logged in.
 */
public class StartViewController implements Initializable {

    @FXML
    Label startViewText = new Label();
    @FXML
    HBox topPanelHBox;

    /**
     * Initializes the start view.
     * Sets the background color and displays a logo.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topPanelHBox.setStyle("-fx-background-color: #a8cb9c;");
        startViewText.setText("Välkommen " + ConnectionController.getInstance().getUser().getFirstName() + "!" +
                "\nÄr det första gången du använder APM? Lägg till din växt med '+' ikonen ovanför listan till vänster.");
    }
}

