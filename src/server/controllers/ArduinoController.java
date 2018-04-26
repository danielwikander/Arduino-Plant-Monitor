package server.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles arduinos connecting to the server by setting
 * up a serversocket and starting an {@link ArduinoHandler}.
 */
public class ArduinoController implements Runnable {
	private ServerSocket serverSocket;
	private Connection conn;

	/**
	 * Sets up a connection with the database. Sets up a serversocket for arduinos.
	 * Starts a thread that listens for connecting arduinos.
	 * @param port	The port the server listens to.
	 */
	public ArduinoController(int port) {
		try {
			this.conn = DriverManager.getConnection("jdbc:postgresql://35.230.133.109:5432/apmdb1", "postgres", "Passw0rd1234!");
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Unable to connect to database");
		}
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Arduino server initiated. Listening on:" + port + " IP address: "
					+ InetAddress.getLocalHost().getHostAddress());
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Thread(this).start();
	}

	/**
	 * Thread that listens after connecting arduinos, sets up a socket for them,
	 * and starts an {@link ArduinoHandler}.
	 */
	public void run() {
		while (!serverSocket.isClosed()) {
			try {
				Socket socket = serverSocket.accept();
				ArduinoHandler arduinoHandler = new ArduinoHandler(socket);
				arduinoHandler.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Thread that handles arduino requests.
	 * The class receives values from the arduino,
	 * and stores and handles them accordingly.
	 */
	private class ArduinoHandler extends Thread {
		private Socket socket;
		private String macAddress;
		private int soilMoistureLevel;
		private int lightLevel;
		private int airHumidityLevel;
		private int airTemperature;
		private String timestamp;

		/**
		 * Establishes the socket for the arduino connection.
		 * @param socket The socket to set.
		 */
		private ArduinoHandler(Socket socket) {
			this.socket = socket;
		}

		/**
		 * This thread reads the values sent from the arduino,
		 * sends them to the database and closes the socket.
		 */
		public void run() {
			try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
				
				macAddress = br.readLine();
				System.out.println(macAddress);
				soilMoistureLevel = Integer.parseInt(br.readLine());
				System.out.println(soilMoistureLevel);
				lightLevel = Integer.parseInt(br.readLine());
				System.out.println(lightLevel);
				airHumidityLevel = Integer.parseInt(br.readLine());
				System.out.println(airHumidityLevel);
				airTemperature = Integer.parseInt(br.readLine());
				System.out.println(airTemperature);
				timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				System.out.println(timestamp);
				
				if(checkMacAddress()) {
					insertValues();
				}
				br.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Checks if the arduino exists in database.
		 */
		private boolean checkMacAddress() {
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT mac FROM apm_arduino WHERE mac = '" + macAddress + "';");
				if(rs.next()) {
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}

		/**
		 * Inserts the retrieved values into the database.
		 */
		private void insertValues() {
			try {
				Statement stmt = conn.createStatement();
				String sql = "INSERT INTO apm_value(date, mac, soil_moisture, humidity, temperature, light_exposure) VALUES ('" + this.timestamp + "', '" + this.macAddress + "', " + this.soilMoistureLevel + ", " + this.airHumidityLevel + ", " + this.airTemperature + ", " + this.lightLevel + ");";
				stmt.executeUpdate(sql);
				stmt.close();			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}	
}