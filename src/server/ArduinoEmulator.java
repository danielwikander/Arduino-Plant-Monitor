package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

/**
 * An arduino emulator.
 * The class emulates values sent from an arduino for testing purposes.
 */
public class ArduinoEmulator {
	private final String MAC_ADDRESS = "2C:3A:E8:43:59:F0";
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
			System.out.println("Test");
			try {			
				while(true) {
					socket = new Socket(ip, port);
					DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
					dos.writeUTF(MAC_ADDRESS);
					dos.writeInt(random(20, 90));	//soilMoistureLevel
					dos.writeInt(random(0, 100));	//LightLevel
					dos.writeInt(random(15, 80));	//airHumidityLevel
					dos.writeInt(random(5, 35));	//airTemperature
					dos.flush();
					Thread.sleep(2500);
					dos.close();
					socket.close();
				}
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
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
			return min + rand.nextInt(max + min +1);
		}
	}
}