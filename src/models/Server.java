package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

public abstract class Server {
    private Server.ConnectionThread connThread = new Server.ConnectionThread();
    private Consumer<String> onReceiveCallback;

    /**
     * De constructor van de Server
     * @param onReceiveCallback
     * Deze is afkomstig van de subklasse Client.
     * Maak een Daemon thread
     */
    public Server(Consumer<String> onReceiveCallback) {
        this.onReceiveCallback = onReceiveCallback;
        connThread.setDaemon(true);
    }

    /**
     * Start de connection Thread.
     */
    public void startConnection() throws Exception {
        connThread.start();
    }

    /**
     * Deze functie stuurt een bericht naar de server.
     * @param data
     * Data is de String die je naar de server wil versturen.
     */
    public void send(String data) {
        try {
            connThread.out.println(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deze functie stopt de connectie.
     */
    public void closeConnection() throws Exception {
        connThread.socket.close();
    }

    /**
     * Dit is een inner class ConnectionThread die Thread extends.
     */
    private class ConnectionThread extends Thread {
        private Socket socket;
        private PrintWriter out;

        /**
         * De run methode van deze inner class.
         * In de try wordt de connnectie met de server opgezet, een PrintWriter en InputStreamReader.
         * Er wordt constant geluisterd of er berichten worden ontvangen.
         */
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
