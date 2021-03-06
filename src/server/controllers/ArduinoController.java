package server.controllers;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/**
 * Handles arduinos connecting to the server by setting up a serversocket and
 * starting an {@link ArduinoHandler}.
 * @author Anton, David, Daniel, Eric.
 */
public class ArduinoController implements Runnable {
	private ServerSocket serverSocket;
	private Connection conn;
	private String databaseURL = "REPLACE WITH DATABASE URL";
	private String databaseUser = "REPLACE WITH DATABASE USERNAME";
	private String databasePassword = "REPLACE WITH DATABASE PASSWOWRD";

	/**
	 * Sets up a connection with the database. Sets up a serversocket for arduinos.
	 * Starts a thread that listens for connecting arduinos.
	 * 
	 * @param port
	 *            The port the server listens to.
	 */
	public ArduinoController(int port) {
		try {
			this.conn = DriverManager.getConnection(databaseURL, databaseUser,
					databasePassword);
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
	 * Thread that listens after connecting arduinos, sets up a socket for them, and
	 * starts an {@link ArduinoHandler}.
	 */
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

	/**
	 * Thread that handles arduino requests. The class receives values from the
	 * arduino, and stores and handles them accordingly.
	 */
	private class ArduinoHandler extends Thread {
		private Socket socket;
		private String macAddress;
		private int soilMoistureLevel;
		private int lightLevel;
		private int airHumidityLevel;
		private int airTemperature;
		private String timestamp;
		private final int TIMES_SOILMOISTURE_HAS_BEEN_LOW = 2;

		/**
		 * Establishes the socket for the arduino connection.
		 * 
		 * @param socket
		 *            The socket to set.
		 */
		private ArduinoHandler(Socket socket) {
			this.socket = socket;
		}

		/**
		 * This thread reads the values sent from the arduino, sends them to the
		 * database and closes the socket.
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

				if (checkMacAddress()) {
					insertValues();
					checkValues();
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
				if (rs.next()) {
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
				String sql = "INSERT INTO apm_value(date, mac, soil_moisture, humidity, temperature, light_exposure) VALUES ('"
						+ this.timestamp + "', '" + this.macAddress + "', " + this.soilMoistureLevel + ", "
						+ this.airHumidityLevel + ", " + this.airTemperature + ", " + this.lightLevel + ");";
				stmt.executeUpdate(sql);
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Method that checks if the soil moisture level is too low and takes action depending on each condition
		 */
		private void checkValues() {
			boolean notifyUser = false;
			try {
				Statement stmt = conn.createStatement();
				ResultSet allowEmailResultSet = stmt.executeQuery("select allowemail from apm_arduino where mac = '" + macAddress + "'");
				if(allowEmailResultSet.next()) {
					// Checks if email has already been sent to user regarding low soil moisture level
					//TODO fix if statement
//					if(allowEmailResultSet.getBoolean(1)) {
						// Checks current soil moisture level
						if (soilMoistureLevel < 15) {
							System.out.println("allow email3");
							try {
								// Checks if user has set soil moisture monitor to true
								Statement stmt2 = conn.createStatement();
								ResultSet soilMoistureMonitorResultSet = stmt2.executeQuery(
										"SELECT soil_moisture_monitor FROM apm_arduino WHERE mac = '" + macAddress + "';");
								if(soilMoistureMonitorResultSet.next()) {
									notifyUser = soilMoistureMonitorResultSet.getBoolean(1);
								}							
								if(notifyUser) {
									// Check if there are at least 24 soil moisture records
									Statement stmt3 = conn.createStatement();
									ResultSet countSoilMoistureRecordsResultSet = stmt3.executeQuery("select count(soil_moisture) from apm_value where mac = '" + macAddress + "';");
									if(countSoilMoistureRecordsResultSet.next()) {
										int rows = countSoilMoistureRecordsResultSet.getInt(1);
										if(rows >= TIMES_SOILMOISTURE_HAS_BEEN_LOW) {
											// Checks last 24 soil moisture level records
											Statement stmt4 = conn.createStatement();
											ResultSet soilMoistureResultSet = stmt4.executeQuery("select soil_moisture from apm_value where mac = '"
													+ macAddress + "' order by date desc limit 24;");
											while (soilMoistureResultSet.next()) {
												// If all last 24 records are below 15 -> notifyUser is true
												if (soilMoistureResultSet.getInt(1) > 15) {
													notifyUser = false;
													break;
												}
											}
										} else {
											notifyUser = false;
										}
									}
								}
								if (notifyUser) {
									notifyUserEmail();
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
//						}
					// If soil moisture monitoring is true and level is more than 15 -> allowemail is true
					} else if (soilMoistureLevel>15) {
						Statement stmt5 = conn.createStatement();
						stmt5.executeUpdate(
								"update apm_arduino set allowemail = true where mac = '" + macAddress + "';");
						
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		/**
		 * Method which notifies the user if a plant needs water.
		 */
		private void notifyUserEmail() {
			final String username = "REPLACE WITH NOTIFICATION EMAIL";
			final String password = "REPLACE WITH PASSWORD FOR NOTIFICATION EMAIL";
			final String bannerLocation = "REPLACE WITH FULL PATH FOR BANNER PIC";
			String emailAddress = null;
			String plantName = null;
			String plantAlias = null;
			String firstName = null;
			@SuppressWarnings("unused")
			String lastName = null;
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from apm_arduino where mac = '" + macAddress + "';");
				if(rs.next()) {
					emailAddress = rs.getString(2);
					plantName = rs.getString(3);
					plantAlias = rs.getString(4);
				}
				stmt = conn.createStatement();
				ResultSet rs2 = stmt.executeQuery("select * from apm_user where email = '" + emailAddress + "';");
				
				if(rs2.next()) {
					firstName = rs2.getString(3);
					lastName = rs2.getString(4);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			try {
				// Creates a message & sets recipient etc.
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddress));
				message.setSubject(plantAlias + " är törstig");
				// Creates html for the message.
				BodyPart messageBodyPart = new MimeBodyPart();
				MimeMultipart multipart = new MimeMultipart("Content");
				String htmlText = "<img src=\"cid:image\"><br><H1>Hej " + firstName + "! </H1>" +
						"<p>Din växt " + plantAlias + "(" + plantName + ") är törstig! :)</p><br><p>Mvh. Arduino Plant Monitor team</p>";
				messageBodyPart.setContent(htmlText, "text/html");
				// Adds HTML to message
				multipart.addBodyPart(messageBodyPart);
				// Adds image as a reference for HTML.
				messageBodyPart = new MimeBodyPart();
				// TODO: Filepath has to be absolute for FileDataSource?
				DataSource fds = new FileDataSource(bannerLocation);
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setHeader("Content-ID", "<image>");
				// Adds text part of message
				multipart.addBodyPart(messageBodyPart);
				// Adds all the content to the message.
				message.setContent(multipart);
				// Sends email.
				Transport.send(message);
				System.out.println("Sent email");
				try {
					Statement stmt2 = conn.createStatement();
					stmt2.executeUpdate(
							"update apm_arduino set allowemail = false where mac = '" + macAddress + "';");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}
	}
}