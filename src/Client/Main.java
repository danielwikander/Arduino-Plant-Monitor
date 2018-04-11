package Client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;

public class Main extends Application {

	private static Stage primaryStage;
	private static BorderPane loginLayout;
	private static BorderPane newUserLayout;
	private static BorderPane startLayout;

	/**
	 * Starts applications primary stage and presents the login view.
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
		loader.setLocation(Main.class.getResource("LoginView.fxml"));
		loginLayout = loader.load();
		Scene scene = new Scene(loginLayout);
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
		loader.setLocation(Main.class.getResource("NewUserView.fxml"));
		newUserLayout = loader.load();
		Scene scene = new Scene(newUserLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Presents the main view.
	 * @throws IOException	Throws if the loader cannot load the StartView.
	 */
	public static void showMainView() throws IOException {
		Platform.runLater(() -> {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("StartView.fxml"));
			try {
				startLayout = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Scene scene = new Scene(startLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		});
	}

	/**
	 * Launches the application.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}