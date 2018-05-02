package client.controllers;

import client.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import models.DataRequest;
import models.Plant;
import models.User;

import java.io.IOException;

/**
 * The controller for the Change View.
 * This controller handles the logic for the view that the user is
 * presented with then they wish to change their settings for a plant.
 */
public class ChangeViewController {

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
    Button removeButton;
    @FXML
    Label settingsForLabel;
    @FXML
    HBox topPanelHBox;
    private Plant plant;

    /**
     * Initializes the view.
     * Sets the background color of the top panel.
     * @param plant The plant to change settings for.
     */
    public void initialize(Plant plant) {
        this.plant = plant;
        topPanelHBox.setStyle("-fx-background-color: #a8cb9c;");
        settingsForLabel.setText("Inställningar för: " + plant.getAlias());
        macAddressTextField.setText(plant.getMac());
        macAddressTextField.setDisable(true);
        plantAliasTextField.setText(plant.getAlias());
        plantNotifierCheckBox.setSelected(plant.monitoringSoilMoisture());
    }

    /**
     * Saves the new plant.
     * The method sends the new plant to the server which inserts it into the database.
     */
    @FXML
    public void changePlant() {
        Plant newPlant = new Plant(plant.getMac(), plant.getEmail(), plant.getPlantSpecies(), plantAliasTextField.getText(), plantNotifierCheckBox.isSelected());
        ConnectionController.getInstance().changePlant(newPlant);
        ConnectionController.getInstance().requestUsersPlantInfo(new DataRequest(new User(Main.getLoggedInUser(), "")));
        try {
            Main.showGraphView(newPlant);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removePlant() {
        try {
            Main.showConfirmRemoveDialog(plant);
            Main.showStartView();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO: Show dialogue : "Plant successfully removed." ?
    }

    /**
     * Returns the user to the start view if they press the 'avbryt' button.
     * @throws IOException
     */
    @FXML
    private void cancel() throws IOException {
        Main.showStartView();
        ConnectionController.getInstance().getMainViewController().enableSettingsButton();
        ConnectionController.getInstance().getMainViewController().enableAddButton();
    }
}
