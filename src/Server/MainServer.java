package Server;

/**
 * The main class that starts the server.
 * Creats a new {@link ArduinoController} and a {@link ClientController}.
 */
public class MainServer {

    public static void main(String[] args) throws InterruptedException {
        new ArduinoController(5482);
        new ClientController(5483);
    }
}