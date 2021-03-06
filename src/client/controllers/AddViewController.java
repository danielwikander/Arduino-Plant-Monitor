package client.controllers;

import client.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import models.DataRequest;
import models.User;
import models.Plant;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for the Add View.
 * This controller handles the logic for the view the user is presented
 * with when they wish to add a new plant to the system.
 * @author Anton, David.
 */
public class AddViewController implements Initializable {

	@FXML
	private WebView wikiWebView;
	@FXML
	private WebEngine webEngine;
	@FXML
	private ChoiceBox<String> speciesChoiceBox;
	private ObservableList<String> speciesListData = FXCollections.observableArrayList();
	@FXML
	private TextField macAddressTextField;
	@FXML
	private TextField plantAliasTextField;
	@FXML
	private CheckBox plantNotifierCheckBox;
	@FXML
	private Button saveButton;
	@FXML
	private Button cancelButton;
	@FXML
	private HBox topPanelHBox;

	/**
	 * Initializes the view.
	 * Sets background color of the top panel and initializes the choicebox and webengine.
	 * @param url
	 * @param resourceBundle
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		topPanelHBox.setStyle("-fx-background-color: #a8cb9c;");
		populateSpeciesList();
		speciesChoiceBox.getSelectionModel().selectFirst();
		initializeChoiceBoxListener();
		webEngine = wikiWebView.getEngine();
		webEngine.load("https://sv.m.wikipedia.org/wiki/Växt");
	}

	/**
	 * Populates the list of species in the choicebox.
	 */
	private void populateSpeciesList() {
		speciesListData.add("Egen");
		speciesListData.add("Broccoli");
		speciesListData.add("Chili");
		speciesListData.add("Gurka");
		speciesListData.add("Jordgubbe");
		speciesListData.add("Morot");
		speciesListData.add("Potatis");
		speciesListData.add("Tomat");
		speciesChoiceBox.setItems(speciesListData);
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

	/**
	 * Saves the new plant.
	 * The method sends the new plant to the server which inserts it into the database.
	 */
	@FXML
    public void savePlant() {
        Plant newPlant = new Plant(macAddressTextField.getText(), Main.getLoggedInUser(), speciesChoiceBox.getValue(), plantAliasTextField.getText(), plantNotifierCheckBox.isSelected());
        ConnectionController.getInstance().addPlant(newPlant);
        ConnectionController.getInstance().requestUsersPlantInfo(new DataRequest(new User(Main.getLoggedInUser(), "")));
    }

	/**
	 * Initializes listener for the choicebox.
	 * The listener changes the wiki webview depending on what item the user chooses in the choicebox.
 	 */
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
