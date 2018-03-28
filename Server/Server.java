import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server implements Runnable {
	private int port;
	private ServerSocket serverSocket;

	// TODO: Ska vi lagra arduinoklienter tillsammans med användarnamn i samma hashmap?
	private HashMap<String, ClientHandler> arduinoClients;
	private HashMap<String, ClientHandler> desktopClients;

	public Server(int port) {
		this.port = port;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server initiated. Listening on:" + port + " IP address: "
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
				ClientHandler clientHandler = new ClientHandler(socket);
				clientHandler.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	

	public class ClientHandler extends Thread {
		private Socket socket;
		private final String requestArduino = "Request: Arduino";


		public ClientHandler(Socket socket) {
			this.socket = socket;
		}


		public void run() {
			try (DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
					DataInputStream ois = new DataInputStream(socket.getInputStream())) {

				if (ois.readUTF().equals(requestArduino)) {
					//TODO: kolla om arduino finns i databasen
					
					//
				}

				while (!socket.isClosed()) {
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {

	}
}