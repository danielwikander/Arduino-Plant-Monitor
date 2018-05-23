package client.controllers;

import client.Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import models.DataRequest;
import models.Plant;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The start view that the user is presented with after they have logged in.
 * @author Eric, Anton.
 */
public class MainViewController implements Initializable {

	@FXML
	private HBox topPanelHBox;
	@FXML
	private ImageView addButtonIcon;
	@FXML
	private ImageView settingsButtonIcon;
	@FXML
	private ImageView refreshButtonIcon;
	@FXML
	private Image settingsIcon = new Image("client/images/settings.png");
	@FXML
	private Image settingsIconGrey = new Image("client/images/settingsGrey.png");
	@FXML
	private Image addIcon = new Image("client/images/add.png");
	@FXML
	private Image addIconGrey = new Image("client/images/addGrey.png");
	@FXML
	private Image refreshIcon = new Image("client/images/refresh.png");
	@FXML
	private ListView<Plant> plantList;
	private static ObservableList<Plant> plantListData = FXCollections.observableArrayList();
	private ConnectionController connectionController;

	/**
	 * Initializes the main view.
	 * @param url 	The location of the FXML document to use.
	 * @param rb 	Resources for the JavaFX view.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		topPanelHBox.setStyle("-fx-background-color: #a8cb9c;");
		connectionController = ConnectionController.getInstance();
		connectionController.setMainViewController(this);
		initializeListViewListener();
		enableAddButton();
		disableSettingsButton();
		
		plantListData.clear();
		
		this.plantList.setCellFactory(new Callback<ListView<Plant>,ListCell<Plant>>() {

			@Override
			public ListCell<Plant> call(ListView<Plant> arg0) {
				ListCell<Plant> cell = new ListCell<Plant>() {
					@Override
					protected void updateItem(Plant c, boolean empty) {
						super.updateItem(c, empty);
						if(empty){
							setGraphic(null);
							setText(null);
						} else {
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
		disableAddButton();
		enableSettingsButton();
		Main.showAddView();
	}

	/**
	 * Presents the change view, where users can change plant values and information.
	 * @throws IOException	Throws exception if the change view cannot be found.
	 */
	private void goChange(Plant plant) throws IOException {
		Main.showChangeView(plant);
		disableSettingsButton();
		enableAddButton();
	}
	
	@FXML
	private void goRefresh(){
        connectionController.requestUsersPlantInfo(new DataRequest(connectionController.getUser()));
		disableSettingsButton();
	}

	/**
	 * Initializes the list so that when users click on a plant,
	 * they are presented with the graph view for that plant.
	 */
	private void initializeListViewListener() {
		plantList.getSelectionModel().selectedItemProperty().addListener((v) -> {
			enableAddButton();
			enableSettingsButton();
			try {
				if(plantList.getSelectionModel().getSelectedItem() != null) {
				Main.showGraphView(plantList.getSelectionModel().getSelectedItem());
				}
			} catch (IOException e ) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Sets the list of plants available to the user.
	 * The list is presented on the left at all times when the
	 * user is logged in.
	 * Platform.runLater() is used to make sure that only the JavaFX
	 * thread handles the operation.
	 * @param plantList The list of plants to set.
	 */
	public void setPlantList(ArrayList<Plant> plantList) {
		Platform.runLater(() -> {
			plantListData = FXCollections.observableArrayList(plantList);
			this.plantList.setItems(plantListData);
		});
		disableSettingsButton();
	}

	/**
	 * Disables the settings button.
	 * This method should be invoked when the user enters the
	 * settings view for a selected plant.
	 */
	public void disableSettingsButton() {
		settingsButtonIcon.setImage(settingsIconGrey);
		settingsButtonIcon.setOnMouseClicked(null);
		settingsButtonIcon.setOpacity(0.3);
		settingsButtonIcon.setCursor(Cursor.DEFAULT);
	}

	/**
	 * Enables the settings button.
	 */
	public void enableSettingsButton() {
		settingsButtonIcon.setImage(settingsIcon);
		settingsButtonIcon.setOpacity(1);
		settingsButtonIcon.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
			try {
				goChange(plantList.getSelectionModel().getSelectedItem());
			} catch (IOException e) {
				e.printStackTrace();
			}
			}
		});
		settingsButtonIcon.setCursor(Cursor.OPEN_HAND);
	}

	/**
	 * Disables the add button.
	 */
	public void disableAddButton() {
		addButtonIcon.setImage(addIconGrey);
		addButtonIcon.setOnMouseClicked(null);
		addButtonIcon.setOpacity(0.3);
		addButtonIcon.setCursor(Cursor.DEFAULT);
	}

	/**
	 * Enables the add button.
	 */
	public void enableAddButton() {
		addButtonIcon.setImage(addIcon);
		addButtonIcon.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				try {
					goAdd();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		addButtonIcon.setOpacity(1);
		addButtonIcon.setCursor(Cursor.OPEN_HAND);
	}
}