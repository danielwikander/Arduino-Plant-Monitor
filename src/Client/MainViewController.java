package Client;

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
import java.util.ResourceBundle;

/**
 * The start view that the user is presented with after they have logged in.
 */
public class MainViewController implements Initializable {

	@FXML
	private Button add;
	@FXML
	private Button change;
	@FXML
	private Button remove;
	@FXML
	private ListView<Plant> plantList;
	private final ObservableList<Plant> plantListData = FXCollections.observableArrayList();

	/**
	 * Initializes the main view.
	 * @param arg0	TODO: Explain FXML stuff?
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		change.setDisable(true);
		remove.setDisable(true);
		initializeListViewListener();
		
		plantListData.clear();
		plantListData.add(new Plant("images/broccoli.png", "Vardagsrum", "Broccoli"));
		plantListData.add(new Plant("images/carrot.png", "Köket", "Morot"));
		plantListData.add(new Plant("images/chili.png", "Sovrum", "Chili"));
		
		plantList.setCellFactory(new Callback<ListView<Plant>,ListCell<Plant>>() {

			@Override
			public ListCell<Plant> call(ListView<Plant> arg0) {
				ListCell<Plant> cell = new ListCell<Plant>() {
					@Override
					protected void updateItem(Plant c, boolean bt1) {
						super.updateItem(c, bt1);
						if(c != null) {
							Image image = new Image(getClass().getResource(c.getPlantIconFile()).toExternalForm(), 30, 30, false, true);
							ImageView imageView = new ImageView(image);
							setGraphic(imageView);
							setText(c.getPlantAlias());
						}
					}
				};
				return cell;
			}		
		});
		plantList.setItems(plantListData);
	}
	
	@FXML
	private void goAdd() throws IOException {
		add.setDisable(true);
		Main.showAddView();
	}
	
	@FXML
	private void goChange() throws IOException {
		Main.showChangeView();
	}
	
	public void initializeListViewListener() {
		plantList.getSelectionModel().selectedItemProperty().addListener((v) -> {
			change.setDisable(false);
			remove.setDisable(false);
			try {
				add.setDisable(false);
				Main.showGraphView(plantList.getSelectionModel().getSelectedItem());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}