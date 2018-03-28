package Server;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class ArduinoEmulator {
	private final String macAddress = "2C:3A:E8:43:59:F0";
	private int port;
	private String ip;
	
	public ArduinoEmulator(int port, String ip) {
		this.port=port;
		this.ip=ip;
		new Thread(new Connect()).start();
	}
	
	public class Connect extends Thread {
		private Socket socket;
		
		
		public void run() {
			System.out.println("Test");
			try {			
				while(true) {
					socket = new Socket(ip, port);
					DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
					dos.writeUTF(macAddress);
					dos.writeInt(random(20, 90));	//soilMoistureLevel
					dos.writeInt(random(0, 100));	//LightLevel
					dos.writeInt(random(15, 80));	//airHumidityLevel
					dos.writeInt(random(5, 35));	//airTemperature
					dos.flush();
					Thread.sleep(2500);
					dos.close();
					socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public int random(int min, int max) {
			Random rand = new Random();
			return min + rand.nextInt(max + min +1);
		}
	}
	

}