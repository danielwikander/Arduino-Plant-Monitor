package client.controllers;

import client.Main;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import models.Plant;

import java.io.IOException;

/**
 * The controller for the Change View.
 * This controller handles the logic for the view that the user is
 * presented with then they wish to change their settings for a plant.
 */
public class ChangeViewController {
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
     * @param plant The plant to change settings for.
     */
    public void initialize(Plant plant) {
        topPanelHBox.setStyle("-fx-background-color: #a8cb9c;");
        settingsForLabel.setText("Inställningar för: " + plant.getAlias());
        macAddressTextField.setText(plant.getMac());
        plantAliasTextField.setText(plant.getAlias());
        plantNotifierCheckBox.setSelected(plant.monitoringSoilMoisture());

    }

    /**
     * Returns the user to the start view if they press the 'avbryt' button.
     * @throws IOException
     */
    @FXML
    private void cancel() throws IOException {
        Main.showStartView();
    }
}
