package client.controllers;

import client.Main;
import models.Plant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The start view that the user is presented with after they have logged in.
 */
public class MainViewController implements Initializable {

	@FXML
	private Button addButton;
	@FXML
	private Button changeButton;
	@FXML
	private Button removeButton;
	@FXML
	private ListView<Plant> plantList;
	private ObservableList<Plant> plantListData = FXCollections.observableArrayList();
	private ConnectionController connectionController;

	/**
	 * Initializes the main view.
	 * @param url 	The location of the FXML document to use.
	 * @param rb 	Resources for the JavaFX view.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		connectionController = ConnectionController.getInstance();
		connectionController.setMainViewController(this);
		
		changeButton.setDisable(true);
		removeButton.setDisable(true);
		initializeListViewListener();
		
		plantListData.clear();
		
		this.plantList.setCellFactory(new Callback<ListView<Plant>,ListCell<Plant>>() {

			@Override
			public ListCell<Plant> call(ListView<Plant> arg0) {
				ListCell<Plant> cell = new ListCell<Plant>() {
					@Override
					protected void updateItem(Plant c, boolean bt1) {
						super.updateItem(c, bt1);
						if(c != null) {
							Image image = new Image(getClass().getClassLoader().getResource(c.getPlantIconFile()).toExternalForm(), 30, 30, false, true);
							ImageView imageView = new ImageView(image);
							setGraphic(imageView);
							setText(c.getAlias());
						}
					}
				};
				return cell;
			}		
		});
		

	}

	/**
	 * Presents the add view, where users can add a new plant.
	 * @throws IOException Throws exception if the add view cannot be found.
	 */
	@FXML
	private void goAdd() throws IOException {
		addButton.setDisable(true);
		Main.showAddView();
	}

	/**
	 * Presents the change view, where users can change plant values and information.
	 * @throws IOException	Throws exception if the change view cannot be found.
	 */
	@FXML
	private void goChange() throws IOException {
		Main.showChangeView();
	}

	/**
	 * Initializes the list so that when users click on a plant,
	 * they are presented with the graph view for that plant.
	 */
	private void initializeListViewListener() {
		plantList.getSelectionModel().selectedItemProperty().addListener((v) -> {
			changeButton.setDisable(false);
			removeButton.setDisable(false);
			addButton.setDisable(false);
			try {
				Main.showGraphView(plantList.getSelectionModel().getSelectedItem());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public void setPlantList(ArrayList<Plant> plantList) {
		plantListData = FXCollections.observableArrayList(plantList);
		this.plantList.setItems(plantListData);
	}
}