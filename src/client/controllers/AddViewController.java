package client.controllers;

import models.Plant;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import models.Plant;
import javafx.scene.web.WebEngine;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Main;

public class AddViewController implements Initializable {
	@FXML
	WebView wikiWebView;

	WebEngine webEngine;

	@FXML
	ChoiceBox<String> speciesChoiceBox;

	ObservableList<String> speciesListData = FXCollections.observableArrayList();

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
		populateSpeciesList();
		initializeChoiceBoxListener();
		webEngine = wikiWebView.getEngine();
		webEngine.load("https://sv.m.wikipedia.org/wiki/Växt");
	}

	private void populateSpeciesList() {
		speciesListData.add("Broccoli");
		speciesListData.add("Chili");
		speciesListData.add("Gurka");
		speciesListData.add("Jordgubbe");
		speciesListData.add("Morot");
		speciesListData.add("Potatis");
		speciesListData.add("Tomat");
		speciesListData.add("Egen");
		System.out.println(speciesListData);
		System.out.println(speciesChoiceBox);
		speciesChoiceBox.setItems(speciesListData);
	}

	@FXML
	private void cancel() throws IOException{
		Main.showStartView();
	}

	@FXML
    public void savePlant() {
        Plant newPlant = new Plant(macAddressTextField.getText(), USEREMAIL, speciesChoiceBox.getValue(), plantAliasTextField.getText(), plantNotifierCheckBox.isSelected());
    }

	private void initializeChoiceBoxListener() {
		speciesChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
				if (newValue.equals("Egen")){
					webEngine.load("https://sv.m.wikipedia.org/wiki/Växt");
				} else {
					webEngine.load("https://sv.m.wikipedia.org/wiki/" + newValue);
				}
			}

		});
	}
}
