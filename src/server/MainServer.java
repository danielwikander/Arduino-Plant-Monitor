package server;

import server.controllers.ArduinoController;
import server.controllers.ClientController;

/**
 * The main class that starts the server.
 * Creates a new {@link ArduinoController} and a {@link ClientController}.
 * @author Anton.
 */
public class MainServer {

    public static void main(String[] args) {
        new ArduinoController(5482);
        new ClientController(5483);
    }
}