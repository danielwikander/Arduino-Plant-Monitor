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
 * Handles clients connecting to the server by setting up a serversocket and
 * starting a {@link ClientHandler}.
 * @author Anton, David, Daniel, Eric.
 */
public class ClientController implements Runnable {
	private ServerSocket serverSocket;

	/**
	 * Constructor that sets up a serversocket and starts a thread listening for
	 * clients.
	 * 
	 * @param port
	 *            The port that the server listens to.
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
	 * Thread that listens to clients. If a client is found, it starts a new
	 * ClientHandler.
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
	 * Handles newly connected clients. Starts new socket connection with input
	 * and output streams. Checks if the client wants to log in or create a new
	 * user, and handles the request accordingly. Also handles datarequests when
	 * a user wishes to retrieve data.
	 */
	private class ClientHandler extends Thread {
		private Socket socket;
		private User user;
		private NewUserRequest newUserRequest;
		private Connection conn;
		private String databaseURL = "REPLACE WITH DATABASE URL";
		private String databaseUser = "REPLACE WITH DATABASE USERNAME";
		private String databasePassword = "REPLACE WITH DATABASE PASSWOWRD";

		/**
		 * Sets the clients socket.
		 * 
		 * @param socket
		 *            The clients socket.
		 */
		private ClientHandler(Socket socket) {
			this.socket = socket;
		}

		/**
		 * Thread that checks what the connected client wishes to do.
		 * The thread checks what type of object that the client sent and
		 * reacts accordingly.
		 */
		public void run() {
			try {
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				Object obj;
				while (!socket.isClosed()) {
					obj = ois.readObject();
					if (obj instanceof User) {
						user = (User) obj;
						user.setIsLoggedIn(validateLogin(user));
						user = getUserInfo(user);
						oos.writeObject(user);
						oos.flush();

					} else if (obj instanceof NewUserRequest) {
						newUserRequest = (NewUserRequest) obj;
						newUserRequest.setNewUserStatus(validateNewUser(newUserRequest));
						oos.writeObject(newUserRequest);
						oos.flush();

					} else if (obj instanceof DataRequest) {
						DataRequest dataRequest = (DataRequest) obj;
						ArrayList<Plant> plantList = getPlants(dataRequest.getRequestingUser());
						oos.writeObject(plantList);
						oos.flush();

					} else if (obj instanceof AddPlantRequest) {
						AddPlantRequest plantAddRequest = (AddPlantRequest) obj;
						Plant plantToAdd = plantAddRequest.getPlantToAdd();
						if(!macAddressTaken(plantToAdd)) {
							addPlant(plantToAdd);
							plantAddRequest.setMacAvailable(true);
						}
						oos.writeObject(plantAddRequest);
					} else if (obj instanceof ChangePlantRequest) {
						ChangePlantRequest changePlantRequest = (ChangePlantRequest) obj;
						Plant plantToChange = changePlantRequest.getPlantToChange();
						if(macAddressMatchEmail(plantToChange)) {
							changePlant(plantToChange);
						} else {
							System.out.println("Not this users plant.");
						}

					} else if (obj instanceof RemovePlantRequest) {
						RemovePlantRequest removePlantRequest = (RemovePlantRequest) obj;
						Plant plantToRemove = removePlantRequest.getPlantToRemove();
						if(macAddressMatchEmail(plantToRemove)) {
							removePlant(plantToRemove);
						} else {
							System.out.println("Not this users plant.");
						}
					}
				}
			} catch (IOException e) {
				System.out.println("Thread " + Thread.currentThread().getId() + " disconnected");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Adds a plant to the database.
		 * @param plant	The plant to add.
		 */
		private void addPlant(Plant plant) {
			try {
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(
						"insert into apm_arduino(mac, email, plant_name, plant_alias, soil_moisture_monitor, soil_moisture_max, soil_moisture_min, humidity_monitor, humidity_max, humidity_min, temperature_monitor, temperature_max, temperature_min) values\n"
								+ "	('" + plant.getMac() + "', '" + plant.getEmail() + "', '" + plant.getPlantSpecies()
								+ "', '" + plant.getAlias() + "', " + plant.monitoringSoilMoisture() 
								+ ", " + plant.getSoilMoistureMax() + ", " + plant.getSoilMoistureMin() 
								+ ", " + plant.monitoringHumidity() + ", " + plant.getHumidityMax() 
								+ ", " + plant.getHumidityMin() + ", " + plant.monitoringTemperature() 
								+ ", " + plant.getTemperatureMax() + ", " + plant.getTemperatureMin() + ");");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		private void changePlant(Plant plant) {
			try {
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(
						"update apm_arduino\n"
								+ " set plant_alias = '" + plant.getAlias() + "',"
								+ " soil_moisture_monitor = " + plant.monitoringSoilMoisture()
								+ " where mac = '" + plant.getMac() + "';");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		private void removePlant(Plant plant) {
			try {
				Statement stmtRemoveData = conn.createStatement();
				Statement stmtRemovePlant = conn.createStatement();
				stmtRemoveData.executeUpdate(
						"delete from apm_value\n"
						+ "where mac = '" + plant.getMac() + "';");

				stmtRemovePlant.executeUpdate(
						"delete from apm_arduino\n"
								+ "where mac = '" + plant.getMac() + "';");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Checks if the MacAddress sent is used by a arduino from the same user or not.
		 * If the MAC-Address is already used by a different user it will not allow an overwrite.
		 * @param plant	The plant which contains the MAC-address and email.
		 * @return		Returns true if the MAC-address is already used by the same user.
		 */
		private boolean macAddressMatchEmail(Plant plant) {
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT mac FROM apm_arduino WHERE email = '" + plant.getEmail()
						+ "' and mac = '" + plant.getMac() + "';");
				if (rs.next()) {
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}

		/**
		 * Checks if the MAC-address is already in the database.
		 * @param plant	The plant which holds the MAC-address to check.
		 * @return		Returns true if taken else returns false.
		 */
		private boolean macAddressTaken(Plant plant) {
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT mac FROM apm_arduino WHERE mac = '" + plant.getMac() + "';");
				if (rs.next()) {
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}

		/**
		 * Retrieves first and last name from the database.
		 * @param user The user to retrieve first and last name from.
		 * @return Returns the user object including the first and last name.
		 */
		private User getUserInfo(User user) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT first_name, last_name FROM apm_user WHERE email = '" + user.getEmail() + "';");
                if (rs.next()) {
                    user.setFirstName(rs.getString(1));
                    user.setLastName(rs.getString(2));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return user;
        }

		/**
		 * Validates the login information by checking the database.
		 * 
		 * @param user
		 *            The log in information from the user.
		 * @return Returns true if the login is valid, else returns false.
		 */
		private boolean validateLogin(User user) {
			ResultSet rs;
			try {
				conn = DriverManager.getConnection(databaseURL, databaseUser,
						databasePassword);
			} catch (SQLException e) {
				System.out.println("Unable to connect to database");
				e.printStackTrace();
			}
			try {
				rs = conn.createStatement()
						.executeQuery("select email, password\n" + "from apm_user\n" + "where email = '"
								+ user.getEmail() + "'\n" + "and password = '" + user.getPassword() + "';");
				if (rs.next()) {
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}

		/**
		 * Validates new user information by checking the database. If the users
		 * email is already logged in the database, the method returns false and
		 * the user is prompted to
		 * 
		 * @param newUserRequest
		 *            The new user information.
		 * @return Returns true if the email is new to the database, else
		 *         returns false.
		 */
		private boolean validateNewUser(NewUserRequest newUserRequest) {
			Statement statement;
			try {
				this.conn = DriverManager.getConnection(databaseURL,databaseUser,
						databasePassword);
				statement = conn.createStatement();
				statement.executeUpdate("insert into apm_user(email, password, first_name, last_name) values\n" + "	('"
						+ newUserRequest.getUser().getEmail() + "', '" + newUserRequest.getUser().getPassword() + "', '" + newUserRequest.getUser().getFirstName() + "', '"
						+ newUserRequest.getUser().getLastName() + "');");
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

		/**
		 * Retrieves a users plants and their information from the server. The
		 * information includes things like MAC-address, notification levels
		 * etc.
		 * 
		 * @param user
		 *            The user who wishes to retrieve plant information.
		 * @return Returns a list of the users plants and their data values.
		 */
		private ArrayList<Plant> getPlants(User user) {
			ResultSet rs;
			ArrayList<Plant> plantList = new ArrayList<Plant>();
			try {
				conn = DriverManager.getConnection(databaseURL,databaseUser,
						databasePassword);
			} catch (SQLException e) {
				System.out.println("Unable to connect to database");
				e.printStackTrace();
			}
			try {
				// Gets information about arduino and the plants notification
				// values.
				rs = conn.createStatement()
						.executeQuery("select " + "mac, " + "email, " + "plant_name, " + "plant_alias, "
								+ "soil_moisture_monitor, " + "soil_moisture_max, " + "soil_moisture_min, "
								+ "humidity_monitor, " + "humidity_max, " + "humidity_min, " + "temperature_monitor, "
								+ "temperature_max, " + "temperature_min\n" + "from apm_arduino\n" + "where email = '"
								+ user.getEmail() + "';");
				while (rs.next()) {
					plantList.add(new Plant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getBoolean(5), rs.getInt(6), rs.getInt(7), rs.getBoolean(8), rs.getInt(9), rs.getInt(10),
							rs.getBoolean(11), rs.getInt(12), rs.getInt(13)));
				}
				for (Plant p : plantList) {
					p.setDataPoints(getPlantData(p));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return plantList;
		}

		/**
		 * Fills the plant object with data from the server.
		 * 
		 * @param plant
		 *            The plant to fill.
		 * @return The list of datapoints for the plant.
		 */
		private ArrayList<DataPoint> getPlantData(Plant plant) {
			ResultSet rs;
			ArrayList<DataPoint> plantListData = new ArrayList<DataPoint>();

			try {
				rs = conn.createStatement()
						.executeQuery("select " + "date, " + "mac, " + "soil_moisture, " + "humidity, "
								+ "temperature, " + "light_exposure\n" + "from apm_value\n" + "where mac = '"
								+ plant.getMac() + "';");

				while (rs.next()) {
					plantListData.add(
							new DataPoint(rs.getString(1), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return plantListData;
		}
	}
}