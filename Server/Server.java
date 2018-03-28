public class Server {
	
	public static void main(String[] args) throws InterruptedException {
		new ArduinoController(5482);
//		new ClientController(5483);
		Thread.sleep(1000);
		new ArduinoEmulator(5482, "127.0.0.1");
	}
}