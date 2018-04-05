package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import SharedResources.Login;
import SharedResources.NewUser;

public class ClientController implements Runnable {
	private ServerSocket serverSocket;

	public ClientController(int port) {
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
		private Login login;
		private NewUser newUser;
		private Connection conn;
		
		public ClientHandler(Socket socket) {
			this.socket = socket;
		}
		
		public void run() {
			try {
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				Object obj;
				while(!socket.isClosed()) {
					obj = ois.readObject();
					if(obj instanceof Login) {
						login = (Login) obj;
						login.setLoginStatus(validateLogin(login));
						oos.writeObject(login);
						oos.flush();
					}
					else if(obj instanceof NewUser) {
						newUser = (NewUser) obj;
						newUser.setNewUserStatus(validateNewUser(newUser));
						oos.writeObject(newUser);
						oos.flush();
					}
					
				}
			} catch (IOException e) {
				System.out.println("Thread " + Thread.currentThread().getId() + " disconnected");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}			
		}
		
		public boolean validateLogin(Login login) {
			ResultSet rs = null;
			try {
				this.conn = DriverManager.getConnection("jdbc:postgresql://35.230.133.109:5432/apmdb1", "postgres", "Passw0rd1234!");
			} catch (SQLException e) {
				System.out.println("Unable to connect to database");
				e.printStackTrace();
			}
			try {
				rs = conn.createStatement().executeQuery(
						"select email, password\n" + 
						"from apm_user\n" + 
						"where email = '" + login.getEmail() + "'\n" + 
						"and password = '" + login.getPassword() + "';");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(rs.next()) {
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}			
			return false;		
		}
		
		public boolean validateNewUser(NewUser newUser) {
			Statement statement = null;
			try {
				this.conn = DriverManager.getConnection("jdbc:postgresql://35.230.133.109:5432/apmdb1", "postgres", "Passw0rd1234!");
				statement = conn.createStatement();
				statement.executeUpdate(
						"insert into apm_user(email, password, first_name, last_name) values\n" + 
						"	('" + newUser.getEmail() + "', '" + newUser.getPassword() + "', '" + newUser.getFirstName() + "', '" + newUser.getLastName() + "');");
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}
}