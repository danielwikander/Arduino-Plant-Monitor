package Client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class StartViewController implements Initializable {

	@FXML
	private ListView<PlantCell> plantList;
	private final ObservableList<PlantCell> plantListData = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		plantListData.clear();
		plantListData.add(new PlantCell("images/broccoli.png", "Vardagsrum", "Broccoli"));
		plantListData.add(new PlantCell("images/carrot.png", "Köket", "Morot"));
		plantListData.add(new PlantCell("images/chili.png", "Sovrum", "Chili"));
		
		plantList.setCellFactory(new Callback<ListView<PlantCell>,ListCell<PlantCell>>() {

			@Override
			public ListCell<PlantCell> call(ListView<PlantCell> arg0) {
				ListCell<PlantCell> cell = new ListCell<PlantCell>() {
					@Override
					protected void updateItem(PlantCell c, boolean bt1) {
						super.updateItem(c, bt1);
						if(c != null) {
							Image image = new Image(getClass().getResource(c.getPlantFile()).toExternalForm(), 30, 30, false, true);
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
}