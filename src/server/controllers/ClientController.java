package server.controllers;

import models.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

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
	 * and handles the request accordingly. Also handles datarequests
	 * when a user wishes to retrieve data.
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
					} else if(obj instanceof NewUser) {
						newUser = (NewUser) obj;
						newUser.setNewUserStatus(validateNewUser(newUser));
						oos.writeObject(newUser);
						oos.flush();
					} else if(obj instanceof DataRequest) {
						DataRequest dataRequest = (DataRequest)obj;
						ArrayList<Plant> plantList = getPlants(dataRequest.getRequestingUser());
						oos.writeObject(plantList);
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

		private ArrayList<Plant> getPlants(Login login) {
			ResultSet rs;
			ArrayList<Plant> plantList = new ArrayList<Plant>();
			try {
				conn = DriverManager.getConnection("jdbc:postgresql://35.230.133.109:5432/apmdb1", "postgres", "Passw0rd1234!");
			} catch (SQLException e) {
				System.out.println("Unable to connect to database");
				e.printStackTrace();
			}
			try {
				// Gets information about arduino and the plants notification values.
				rs = conn.createStatement().executeQuery(
						"select " +
								"mac, " +
								"email, " +
								"plant_name, " +
								"plant_alias, " +
								"soil_moisture_monitor, " +
								"soil_moisture_max, " +
								"soil_moisture_min, " +
								"humidity_monitor, " +
								"humidity_max, " +
								"humidity_min, " +
								"temperature_monitor, " +
								"temperature_max, " +
								"temperature_min\n" +
								"from apm_arduino\n" +
								"where email = '" + login.getEmail() + "';");
				while(rs.next()) {
					plantList.add(new Plant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getBoolean(5), rs.getInt(6), rs.getInt(7),
							rs.getBoolean(8), rs.getInt(9), rs.getInt(10),
							rs.getBoolean(11), rs.getInt(12), rs.getInt(13)));
				}
				for(Plant p : plantList) {
					p.setDataPoints(getPlantData(p));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return plantList;
		}


		private ArrayList<DataPoint> getPlantData(Plant plant) {
			ResultSet rs;
			ArrayList<DataPoint> plantListData = new ArrayList<DataPoint>();

			try {
				rs = conn.createStatement().executeQuery(
						"select " +
								"date, " +
								"mac, " +
								"soil_moisture, " +
								"humidity, " +
								"temperature, " +
								"light_exposure\n" +
								"from apm_value\n" +
								"where mac = '"+ plant.getMac() + "';");

				while(rs.next()) {
					plantListData.add(new DataPoint(rs.getString(1), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return plantListData;
		}
	}
}