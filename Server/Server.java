import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private int port;

    public Server(int port) {
        this.port = port;

        try {
            serverSocket = new ServerSocket(port);
            while(true) {
                clientSocket = serverSocket.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                while (br.readLine() != null) {
                    System.out.println(br.readLine());
                }
            }
        } catch (IOException e)  { }

    }

    public static void main(String[] args) {
        Server server = new Server(80);

    }
}