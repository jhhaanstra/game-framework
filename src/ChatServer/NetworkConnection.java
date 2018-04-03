package ChatServer;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

public abstract class NetworkConnection {

    private ConnectionThread connThread = new ConnectionThread();
    private Consumer<String> onReceiveCallback;

    public NetworkConnection(Consumer<String> onReceiveCallback) {
        this.onReceiveCallback = onReceiveCallback;
        connThread.setDaemon(true);
    }

    public void startConnection() throws Exception {
        connThread.start();
    }

    public void send(String data) throws Exception {
        connThread.out.println(data);
    }

    public void closeConnection() throws Exception {
        connThread.socket.close();
    }

    protected abstract String getIP();
    protected abstract int getPort();

    private class ConnectionThread extends Thread {

        private Socket socket;
        private PrintWriter out;

        @Override
        public void run() {
            try(Socket socket = new Socket("localhost", 7789);
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(socket.getInputStream())))
            {
                this.socket = socket;
                this.out = out;
                socket.setTcpNoDelay(true);

                while(true) {
                    String data = in.readLine();
                    onReceiveCallback.accept(data);
                }

            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
