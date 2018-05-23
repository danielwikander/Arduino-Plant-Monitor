package server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Random;

/**
 * An arduino emulator.
 * The class emulates values sent from an arduino for testing purposes.
 */
public class ArduinoEmulator {
	private final String MAC_ADDRESS = "2C:3A:E8:43:59:F5";
	private int port;
	private String ip;

	/**
	 * Sets up the port and ip to use in the emulator.
	 * @param port	The port to use.
	 * @param ip	The ip to use.
	 */
	public ArduinoEmulator(int port, String ip) {
		this.port=port;
		this.ip=ip;
		new Thread(new Connect()).start();
	}

	/**
	 * The class starts a connection and sends random values.
	 */
	public class Connect extends Thread {
		private Socket socket;

		/**
		 * The thread starts a connection and sends out random values emulating an arduino.
		 */
		public void run() {
			try {
				int soilMoisture = 70;
				int lightLevel = 60;
				int airTemp = 22;
				int airHumidity = 60;
				boolean day = false;
				while(true) {
					socket = new Socket(ip, port);
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					// Sends MAC
					bw.write(MAC_ADDRESS);
					bw.newLine();
					// Calculates and sends soilMoisture
					soilMoisture = random(soilMoisture -2, soilMoisture);
					if (soilMoisture < 10 ) {
						soilMoisture = 90;
					}
					bw.write("" + soilMoisture);
					bw.newLine();
					// Calculates and sends lightLevel
					if(day) {
						lightLevel += 4;
					} else {
						lightLevel -= 4;
					}
					if (lightLevel < 30) {
						day = true;
					} else if (lightLevel > 80) {
						day = false;
					}
					bw.write("" + lightLevel);
					bw.newLine();
					// Calculates and sends AirHumidity
					if(lightLevel > 50 && airHumidity < 80) {
						airHumidity++;
					} else if (lightLevel < 50 && airHumidity > 30) {
						airHumidity--;
					}
					bw.write("" + airHumidity);
					bw.newLine();
					// Calculates and sends airtemperature
					if(lightLevel > 50 && airTemp < 25) {
						airTemp += 1;
					} else if (lightLevel <50 && airTemp > 18) {
						airTemp -=1;
					}
					bw.write("" + airTemp);
					bw.newLine();
					bw.flush();
					System.out.println("Values sent.");
					System.out.println(soilMoisture + "\n" + lightLevel + "\n" +airHumidity + "\n" + airTemp);
					Thread.sleep(4000);
					bw.close();
					socket.close();
				}
			} catch (InterruptedException e ) {
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Server disconnected.");
			}
		}

		/**
		 * Returns a random number between two values.
		 * @param min	Minimum value
		 * @param max	Maximum value
		 * @return		Returns the random value.
		 */
		private int random(int min, int max) {
			Random rand = new Random();
			return min + rand.nextInt(max-min);
		}
	}
	
	public static void main(String[]args) {
		new ArduinoEmulator(5482, "localhost");
	}
}