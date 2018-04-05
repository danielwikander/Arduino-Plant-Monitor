package Server;

public class MainServer {

    public static void main(String[] args) throws InterruptedException {
        new ArduinoController(5482);
        new ClientController(5483);
    }
}