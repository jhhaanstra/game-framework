package models;

import java.util.Stack;
import java.util.function.Consumer;

public class Client extends Server {
    private static Client client;
    private String ip;
    private int port;
    private static Stack<String> messages = new Stack<>();

    public static Client getInstance() {
        if (client == null) {
            client = new Client("127.0.0.1", 7789, (data -> {
                messages.push(data);
            }));
        }
        return client;
    }

    private Client(String ip, int port, Consumer<String> onReceiveCallback) {
        super(onReceiveCallback);
        this.ip = ip;
        this.port = port;
    }

    public boolean login(String name) {
        send("login " + name);

            return true;
        //else return false;
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

