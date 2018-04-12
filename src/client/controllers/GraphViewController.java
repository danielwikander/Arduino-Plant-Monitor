package client.controllers;

import models.Plant;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GraphViewController {
	
	@FXML
	private Label plantAliasLabel;

	public void initialize(Plant plant) {
		plantAliasLabel.setText(plant.getPlantAlias());
	}
}