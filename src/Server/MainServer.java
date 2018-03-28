package Server;

public class MainServer {

    public MainServer() {

    }

    public static void main(String[] args) throws InterruptedException {
        new ArduinoController(5482);
        // new ClientController(5482);
        Thread.sleep(1000);
        new ArduinoEmulator(5482, "127.0.0.1");
    }
}
