package server.controllers;

import models.Login;
import models.NewUser;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

/**
 * Handles clients connecting to the server by setting
 * up a serversocket and starting a {@link ClientHandler}.
 */
public class ClientController implements Runnable {
	private ServerSocket serverSocket;

	/**
	 * Constructor that sets up a serversocket and starts a
	 * thread listening for clients.
	 * @param port	The port that the server listens to.
	 */
	public ClientController(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("client server initiated. Listening on:" + port + " IP address: "
					+ InetAddress.getLocalHost().getHostAddress());
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Thread(this).start();
	}

	/**
	 * Thread that listens to clients.
	 * If a client is found, it starts a new ClientHandler.
	 */
	public void run() {
		while (!serverSocket.isClosed()) {
			try {
				Socket socket = serverSocket.accept();
				ClientHandler handler = new ClientHandler(socket);
				handler.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Handles newly connected clients.
	 * Starts new socket connection with input and output streams.
	 * Checks if the client wants to log in or create a new user,
	 * and handles the request accordingly.
	 */
	private class ClientHandler extends Thread {
		private Socket socket;
		private Login login;
		private NewUser newUser;
		private Connection conn;

		/**
		 * Sets the clients socket.
		 * @param socket	The clients socket.
		 */
		private ClientHandler(Socket socket) {
			this.socket = socket;
		}

		/**
		 * Thread that checks if the connected client wants to log in or create a new user.
		 */
		public void run() {
			try {
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				Object obj;
				while(!socket.isClosed()) {
					obj = ois.readObject();
					if(obj instanceof Login) {
						login = (Login) obj;
						login.setIsLoggedIn(validateLogin(login));
						oos.writeObject(login);
						oos.flush();
					}
					else if(obj instanceof NewUser) {
						newUser = (NewUser) obj;
						newUser.setNewUserStatus(validateNewUser(newUser));
						oos.writeObject(newUser);
						oos.flush();
					}
				}
			} catch (IOException e) {
				System.out.println("Thread " + Thread.currentThread().getId() + " disconnected");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}			
		}

		/**
		 * Validates the login information by checking the database.
		 * @param login	The log in information from the user.
		 * @return		Returns true if the login is valid, else returns false.
		 */
		private boolean validateLogin(Login login) {
			ResultSet rs;
			try {
				conn = DriverManager.getConnection("jdbc:postgresql://35.230.133.109:5432/apmdb1", "postgres", "Passw0rd1234!");
			} catch (SQLException e) {
				System.out.println("Unable to connect to database");
				e.printStackTrace();
			}
			try {
				rs = conn.createStatement().executeQuery(
						"select email, password\n" + 
						"from apm_user\n" + 
						"where email = '" + login.getEmail() + "'\n" + 
						"and password = '" + login.getPassword() + "';");
				if(rs.next()) {
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}

		/**
		 * Validates new user information by checking the database.
		 * If the users email is already logged in the database,
		 * the method returns false and the user is prompted to
		 * @param newUser	The new user information.
		 * @return			Returns true if the email is new to the database,
		 * 					else returns false.
		 */
		private boolean validateNewUser(NewUser newUser) {
			Statement statement;
			try {
				this.conn = DriverManager.getConnection("jdbc:postgresql://35.230.133.109:5432/apmdb1", "postgres", "Passw0rd1234!");
				statement = conn.createStatement();
				statement.executeUpdate(
						"insert into apm_user(email, password, first_name, last_name) values\n" + 
						"	('" + newUser.getEmail() + "', '" + newUser.getPassword() + "', '" + newUser.getFirstName() + "', '" + newUser.getLastName() + "');");
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}
}