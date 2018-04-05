package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import SharedResources.Login;
import SharedResources.NewUser;

public class ConnectionController {
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private LoginViewController loginViewController;
	private NewUserViewController newUserViewController;
	private static ConnectionController connectionController;
	
	private ConnectionController() {
		try {
			this.socket = new Socket("127.0.0.1", 5483);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ConnectionHandler().start();
	}
	
	public static ConnectionController getInstance() {
		if(connectionController == null) {
			connectionController = new ConnectionController();
		}
		return connectionController;
	}
	
	public void setLoginViewController(LoginViewController loginViewController) {
		this.loginViewController = loginViewController;
	}
	
	public void setNewUserViewController(NewUserViewController newUserViewController) {
		this.newUserViewController = newUserViewController;
	}
	
	protected void login(Login login) {
		try {
			oos.writeObject(login);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void newUser(NewUser newUser) {
		try {
			oos.writeObject(newUser);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class ConnectionHandler extends Thread{
		
		public void run() {
			while(!socket.isClosed()) {
				Object obj = null;
				try {
					obj = ois.readObject();
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(obj instanceof Login) {
					Login login = (Login) obj;			
					loginViewController.validateLogin(login);
				}
				if(obj instanceof NewUser) {
					NewUser newUser = (NewUser) obj;
					newUserViewController.validateNewUser(newUser);
				}
			}
		}
	}

}
