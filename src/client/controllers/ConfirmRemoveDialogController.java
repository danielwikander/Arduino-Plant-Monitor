package client.controllers;

import client.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.DataRequest;
import models.Plant;
import models.User;

import java.io.IOException;

/**
 * This dialog class asks the user to confirm deletion of a plant.
 * @author Anton, Eric.
 */
public class ConfirmRemoveDialogController {
    
	@FXML
    private Button cancelButton;
    @FXML
    private Button removeButton;
    @FXML
    private Label textLabel;
    @FXML
    private BorderPane borderPane;
    private Plant plant;

    /**
     * Initializes the view.
     * Sets the plant object and text in dialog.
     * @param plant
     */
    public void initialize(Plant plant) {
        this.plant = plant;
        textLabel.setText("Är du säker på att du vill ta bort " + plant.getAlias() + "?");
    }

    /**
     * Sends a remove request for the current plant to the database.
     * Shows the start view.
     */
    public void remove() {
        ConnectionController.getInstance().removePlant(plant);
        ConnectionController.getInstance().requestUsersPlantInfo(new DataRequest(new User(Main.getLoggedInUser(), "")));
        try {
            Main.showStartView();
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeDialog();
    }

    /**
     * Closes the 'confirm remove dialog window'.
     */
    public void closeDialog() {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }
}
