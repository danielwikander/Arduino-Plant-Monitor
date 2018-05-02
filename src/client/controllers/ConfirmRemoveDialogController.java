package client.controllers;


import client.Main;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.DataRequest;
import models.Plant;
import models.User;

import java.io.IOException;

public class ConfirmRemoveDialogController {
    @FXML
    Button cancelButton;
    @FXML
    Button removeButton;
    @FXML
    Label textLabel;
    @FXML
    BorderPane borderPane;

    Plant plant;

    public void initialize(Plant plant) {
        this.plant = plant;
        textLabel.setText("Är du säker på att du vill ta bort " + plant.getAlias() + "?");
    }

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

    public void closeDialog() {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }
}
