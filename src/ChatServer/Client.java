package ChatServer;

import java.util.Stack;
import java.util.function.Consumer;

public class Client extends NetworkConnection{

    private String ip;
    private int port;

    private static Client client;
    private static Stack<String> serverOutput = new Stack<>();

    private Client(String ip, int port, Consumer<String> onReceiveCallback) {
        super(onReceiveCallback);
        this.ip = ip;
        this.port = port;
    }

    public static synchronized Client getInstance() {
        if (client == null) {
            client = new Client("127.0.0.1", 7789, data -> {
                serverOutput.push(data);
            });
        }
        return client;
    }

    @Override
    protected String getIP() {
        return ip;
    }

    @Override
    protected int getPort() {
        return port;
    }

}
