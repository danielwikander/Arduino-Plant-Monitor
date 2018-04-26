package client;

import client.controllers.ConnectionController;
import client.controllers.GraphViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.Login;
import models.Plant;
import java.io.IOException;

/**
 * Sets all JavaFX views and starts the application.
 */
public class Main extends Application {

	private static Stage primaryStage;
	private static BorderPane mainLayout;
	private static String user;
	

	/**
	 * Starts applications primary stage and presents the login view.
	 * Closes socket when the primary stage is closed.
	 * @param primaryStage	The primary view to present.
	 * @throws IOException	Throws exception if the stage is invalid.
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		Main.primaryStage = primaryStage;
		Main.primaryStage.setTitle("Arduino Plant Monitor");
		showLoginView();
		Main.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                ConnectionController cc = ConnectionController.getInstance();
                cc.closeSocket();
            }
        });
	}

	/**
	 * Presents the login view.
	 * A window where users can log in.
	 * @throws IOException	Throws if the loader cannot load the LoginView.
	 */
	public static void showLoginView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("views/LoginView.fxml"));
		BorderPane loginLayout = loader.load();
		Scene scene = new Scene(loginLayout);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Presents the new user view.
	 * A window where users can create a new account.
	 * @throws IOException	Throws if the loader cannot load the NewUserView.
	 */
	public static void showNewUserView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("views/NewUserView.fxml"));
		BorderPane newUserLayout = loader.load();
		Scene scene = new Scene(newUserLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Presents the main view.
	 * @throws IOException	Throws if the loader cannot load the MainView.
	 */
	public static void showMainView() {
		Platform.runLater(() -> {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("views/MainView.fxml"));
			try {
				mainLayout = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Scene scene = new Scene(mainLayout);
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();
			try {
				showStartView();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Presents the start view.
	 * @throws IOException	Throws if the loader cannot load the StartView.
	 */
	public static void showStartView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("views/StartView.fxml"));
		BorderPane startLayout = loader.load();
		mainLayout.setCenter(startLayout);
	}

	/**
	 * Presents the add view.
	 * @throws IOException	Throws if the loader cannot load the AddView.
	 */
	public static void showAddView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("views/AddView.fxml"));
		BorderPane addLayout = loader.load();
		mainLayout.setCenter(addLayout);
		
		
	}

	/**
	 * Presents the change view.
	 * @throws IOException	Throws if the loader cannot load the ChangeView.
	 */
	public static void showChangeView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("views/ChangeView.fxml"));
		BorderPane changeLayout = loader.load();
		mainLayout.setCenter(changeLayout);
	}

	/**
	 * Presents the graph view.
	 * @throws IOException	Throws if the loader cannot load the GraphView.
	 */
	public static void showGraphView(Plant plant) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("views/GraphView.fxml"));
		BorderPane graphLayout = loader.load();
		mainLayout.setCenter(graphLayout);
		GraphViewController gvc = loader.getController();
		gvc.initialize(plant);
	}

	public static void setLoggedInUser(String email) {
		user = email;
	}
	
	public static String getLoggedInUser() {
		return user;
	}	
	
	/**
	 * Launches the application.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}