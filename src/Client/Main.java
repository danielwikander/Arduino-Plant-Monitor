package Client;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	private static Stage primaryStage;
	private static BorderPane loginLayout;
	private static BorderPane newUserLayout;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		Main.primaryStage = primaryStage;
		Main.primaryStage.setTitle("Arduino Plant Monitor");
		showLoginView();
	}
	
	public static void showLoginView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("LoginView.fxml"));
		loginLayout = loader.load();
		Scene scene = new Scene(loginLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void showNewUserView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("NewUserView.fxml"));
		newUserLayout = loader.load();
		Scene scene = new Scene(newUserLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}