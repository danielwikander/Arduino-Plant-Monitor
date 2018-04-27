package client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The view the user is presented with when they have logged in.
 */
public class StartViewController implements Initializable {

    @FXML
    Label splashLabel = new Label();
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
        Image splashIcon = new Image("client/images/mediumIcon.png");
        splashLabel.setGraphic(new ImageView(splashIcon));
    }
}

