package client.controllers;

import client.Main;
import javafx.application.Platform;
import models.*;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Handles the initial connection to the server. Sets up a socket and input /
 * output streams.
 * @author Eric, David.
 */
public class ConnectionController {
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private LoginViewController loginViewController;
	private NewUserViewController newUserViewController;
	private static ConnectionController connectionController;
	private MainViewController mainViewController;
	private boolean serverAvailable;
	private User currentUser;
	private String serverIp = "localhost";
	private int port = 5483;

	/**
	 * Sets up input / output streams and starts a new {@link ConnectionHandler}
	 * that handles the incoming responses from the server.
	 */
	private ConnectionController() {
		try {
			this.socket = new Socket(serverIp, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			serverAvailable = true;
		} catch (IOException e) {
			serverAvailable = false;
		}
		new ConnectionHandler().start();
	}

	/**
	 * Sets newly logged in user.
	 * @param user	The newly logged in user.
	 */
	public void setUser(User user) {
		this.currentUser = user;
	}

	/**
	 * Keeps track of the server status.
	 * @return	Returns true if the server is available.
	 */
	public boolean isServerAvailable() {
		return serverAvailable;
	}

	/**
	 * Creates a singleton instance of the class.
	 * 
	 * @return Returns instance of the class.
	 */
	public static ConnectionController getInstance() {
		if (connectionController == null) {
			connectionController = new ConnectionController();
		}
		return connectionController;
	}

	/**
	 * Sets the loginViewController.
	 * 
	 * @param loginViewController
	 *            The loginViewController to use.
	 */
	void setLoginViewController(LoginViewController loginViewController) {
		this.loginViewController = loginViewController;
	}

	/**
	 * Sets the newUserViewController.
	 * 
	 * @param newUserViewController
	 *            The newUserViewController to use.
	 */
	void setNewUserViewController(NewUserViewController newUserViewController) {
		this.newUserViewController = newUserViewController;
	}

	/**
	 * Sets the main View Controller
	 * @param mainViewController	The mainViewController to use.
	 */
	void setMainViewController(MainViewController mainViewController) {
		this.mainViewController = mainViewController;
	}

	/**
	 * Gets the main View Controller
	 * @return The mainViewController to return.
	 */
	public MainViewController getMainViewController() {
		return mainViewController;
	}

	/**
	 * Sends a login request to the server.
	 * 
	 * @param user
	 *            The login information to send.
	 */
	protected void login(User user) {
		try {
			oos.writeObject(user);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a new user request to the server.
	 * 
	 * @param newUserRequest
	 *            The new user information to send.
	 */
	void newUser(NewUserRequest newUserRequest) {
		try {
			oos.writeObject(newUserRequest);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a plant that the user wishes to save to the server.
	 * @param plant	The plant that the user wishes to save.
	 */
	void addPlant(Plant plant){
		try {
			AddPlantRequest plantToAdd = new AddPlantRequest(plant);
			oos.writeObject(plantToAdd);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a request to change the plants information in the database.
	 * @param plant The plant to change.
	 */
	void changePlant(Plant plant) {
		try {
			ChangePlantRequest plantToChange = new ChangePlantRequest(plant);
			oos.writeObject(plantToChange);
			oos.flush();
			} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a request to delete the plant in the database.
	 * @param plant
	 */
	void removePlant(Plant plant) {
		try {
			RemovePlantRequest plantToRemove = new RemovePlantRequest(plant);
			oos.writeObject(plantToRemove);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the current user.
	 * @return	The user to return.
	 */
	public User getUser() {
	    return currentUser;
    }

	/**
	 * Closes the socket.
	 */
	public void closeSocket() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handles the incoming connection information. Checks if the login or new
	 * user information sent is valid.
	 */
	private class ConnectionHandler extends Thread {

		public void run() {
			while (!socket.isClosed()) {
				Object obj = null;
				try {
					obj = ois.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SocketException e) {
					System.out.println("client socket closed");
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (obj instanceof User) {
					User user = (User) obj;
					loginViewController.validateLogin(user);
					if (user.isLoggedIn()) {
					    currentUser = user;
						requestUsersPlantInfo(new DataRequest(user));
					}
				}
				else if (obj instanceof NewUserRequest) {
					NewUserRequest newUserRequest = (NewUserRequest) obj;
					newUserViewController.validateNewUser(newUserRequest);
				}
				else if (obj instanceof ArrayList<?>) {
					@SuppressWarnings("unchecked")
					ArrayList<Plant> newList = (ArrayList<Plant>) obj;
					mainViewController.setPlantList(newList);
					Platform.runLater(() -> {
					try {
						Main.showStartView();
					} catch (IOException e) {
						e.printStackTrace();
					}
					});
				}
				else if (obj instanceof AddPlantRequest){
					AddPlantRequest request = (AddPlantRequest)obj;
					if (request.isMacAvailable()){
						Platform.runLater(() -> {
							try {
								Main.showGraphView(request.getPlantToAdd());
							} catch (IOException e) {
								e.printStackTrace();
							}	
						});
					} else {
						System.out.print("MAC-Address taken.\n");
						JOptionPane.showMessageDialog(null, "MAC-adress är upptagen.");
					}
				}
				
			}
		}
	}

	/**
	 * Retrieves plant information from the server.
	 * 
	 * @param dataRequest
	 *            The request for data.
	 */
	public void requestUsersPlantInfo(DataRequest dataRequest) {
		try {
			oos.writeObject(dataRequest);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
