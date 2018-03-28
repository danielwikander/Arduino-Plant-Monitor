package Server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;


public class ClientController implements Runnable {
	private ServerSocket serverSocket;
	private Connection conn;

	// TODO: Ska vi lagra arduinoklienter tillsammans med användarnamn i samma hashmap?
	private HashMap<String, ClientHandler> arduinoClients;

	public ClientController(int port) {
		try {
			this.conn = DriverManager.getConnection("jdbc:postgresql://35.230.133.109:5432/apmdb1", "postgres", "Passw0rd1234!");
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Unable to connect to database");
		}
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Client server initiated. Listening on:" + port + " IP address: "
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
				ClientHandler handler = new ClientHandler(socket);
				handler.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public class ClientHandler extends Thread{
		private Socket socket;
		private String email;
		private String password;
		
		public ClientHandler(Socket socket) {
			this.socket = socket;
		}
		
		public void run() {
			try {
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				
				while(!socket.isClosed()) {
					email = ois.readUTF();
					password = ois.readUTF();
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}
}