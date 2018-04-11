package Client;

import SharedResources.Login;
import SharedResources.NewUser;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

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
	public void setLoginViewController(LoginViewController loginViewController) {
		this.loginViewController = loginViewController;
	}

	/**
	 * Sets the newUserViewController.
	 * @param newUserViewController The newUserViewController to use.
	 */
	public void setNewUserViewController(NewUserViewController newUserViewController) {
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
	protected void newUser(NewUser newUser) {
		try {
			oos.writeObject(newUser);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Closes the socket.
	 */
	protected void closeSocket() {
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
					System.out.println("Client socket closed");
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(obj instanceof Login) {
					Login login = (Login) obj;			
					loginViewController.validateLogin(login);
				}
				if(obj instanceof NewUser) {
					NewUser newUser = (NewUser) obj;
					newUserViewController.validateNewUser(newUser);
				}
			}
		}
	}
}
