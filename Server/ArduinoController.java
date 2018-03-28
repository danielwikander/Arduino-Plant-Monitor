import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class ArduinoController implements Runnable {
	private ServerSocket serverSocket;
	private Connection conn;

	// TODO: Ska vi lagra arduinoklienter tillsammans med användarnamn i samma hashmap?
	private HashMap<String, ArduinoHandler> arduinoClients;

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

	public void run() {
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				ArduinoHandler arduinoHandler = new ArduinoHandler(socket);
				arduinoHandler.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public class ArduinoHandler extends Thread {
		private Socket socket;
		private String macAddress;
		private int soilMoistureLevel;
		private int lightLevel;
		private int airHumidityLevel;
		private int airTemperature;
		private String timestamp;

		public ArduinoHandler(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			try (DataInputStream dis = new DataInputStream(socket.getInputStream())) {
				
				macAddress = dis.readUTF();
				System.out.println(macAddress);
				soilMoistureLevel = dis.readInt();
				System.out.println(soilMoistureLevel);
				lightLevel = dis.readInt();
				System.out.println(lightLevel);
				airHumidityLevel = dis.readInt();
				System.out.println(airHumidityLevel);
				airTemperature = dis.readInt();
				System.out.println(airTemperature);
				timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				System.out.println(timestamp);
				
				if(checkMacAddress(macAddress)) {
					insertValues();
				}
				dis.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// Check if the Arudino exists in database
		private boolean checkMacAddress(String mac) {
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT mac FROM apm_plant WHERE mac = '" + macAddress + "';");
				if(rs.next()) {
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
		
		// Inserts new values for the Arduino into database
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