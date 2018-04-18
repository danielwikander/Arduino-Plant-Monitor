package client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Plant;
import models.DataRequest;
import models.Login;
import models.NewUser;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Handles the initial connection to the server.
 * Sets up a socket and input / output streams.
 */
public class ConnectionController {
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private LoginViewController loginViewController;
	private NewUserViewController newUserViewController;
	private static ConnectionController connectionController;
	private MainViewController mainViewController;

	/**
	 * Sets up input / output streams and starts a new {@link ConnectionHandler}
	 * that handles the incoming responses from the server.
	 */
	private ConnectionController() {
		try {
			this.socket = new Socket("127.0.0.1", 5483);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Server not available.");
			// TODO: skapa error i GUI. röd text?
		}
		new ConnectionHandler().start();
	}

	/**
	 * Creates a singleton instance of the class.
	 * @return	Returns instance of the class.
	 */
	public static ConnectionController getInstance() {
		if(connectionController == null) {
			connectionController = new ConnectionController();
		}
		return connectionController;
	}

	/**
	 * Sets the loginViewController.
	 * @param loginViewController The loginViewController to use.
	 */
	void setLoginViewController(LoginViewController loginViewController) {
		this.loginViewController = loginViewController;
	}

	/**
	 * Sets the newUserViewController.
	 * @param newUserViewController The newUserViewController to use.
	 */
	void setNewUserViewController(NewUserViewController newUserViewController) {
		this.newUserViewController = newUserViewController;
	}

	/**
	 * Sends a login request to the server.
	 * @param login The login information to send.
	 */
	protected void login(Login login) {
		try {
			oos.writeObject(login);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a new user request to the server.
	 * @param newUser The new user information to send.
	 */
	void newUser(NewUser newUser) {
		try {
			oos.writeObject(newUser);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	 * Handles the incoming connection information.
	 * Checks if the login or new user information sent is valid.
	 */
	private class ConnectionHandler extends Thread {

		public void run() {
			while(!socket.isClosed()) {
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
				if(obj instanceof Login) {
					Login login = (Login) obj;			
					loginViewController.validateLogin(login);
					if(login.isLoggedIn()) {
						requestUsersPlantInfo(new DataRequest(login));
					}
				}
				if(obj instanceof NewUser) {
					NewUser newUser = (NewUser) obj;
					newUserViewController.validateNewUser(newUser);
				}
				if(obj instanceof ArrayList<?>) {
					if(((ArrayList<?>)obj).get(0) instanceof Plant) {
						ArrayList<Plant> newList = (ArrayList<Plant>)obj;
						System.out.println(newList.toString());
						mainViewController.setPlantList(newList);
					}
				}
			}
		}
	}

	/**
	 * Retrieves plant information from the server.
	 * @param dataRequest The request for data.
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
