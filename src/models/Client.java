package models;

import java.util.LinkedList;
import java.util.Stack;
import java.util.function.Consumer;
import controllers.*;
import javafx.application.Platform;

public class Client extends Server {
    private static Client client;
    private String ip;
    private int port;
    private static LinkedList<String> errors = new LinkedList<>();
    private static LinkedList<String> info = new LinkedList<>();
    private static LinkedList<String> match = new LinkedList<>();
    private static LinkedList<String> moves = new LinkedList<>();
    private static LinkedList<String> challenge = new LinkedList<>();
    private static LinkedList<String> score = new LinkedList<>();
    private static LinkedList<String> turn = new LinkedList<>();
    private static LinkedList<String> playerlist = new LinkedList<>();


    public static Client getInstance() {
        if (client == null) {
            client = new Client("127.0.0.1", 7789, (data -> {
                if (data.contains("ERR")) errors.add(data);
                if (data.contains("SVR")) info.add(data);
                if (data.contains("SVR PLAYERLIST")) playerlist.add(data);
                if (data.contains("SVR GAME MATCH")) match.add("{" + data.split("\\{")[1]);
                //if (data.contains("SVR GAME MOVE")) moves.push("{" + data.split("\\{")[1]);
                if (data.contains("SVR GAME MOVE")) turn.add("{" + data.split("\\{")[1]);
        		if (data.contains("SVR GAME CHALLENGE")) challenge.add("{" + data.split("\\{")[1]);
                if (data.contains("WIN") | data.contains("DRAW") | data.contains("LOSS")) score.add(data);
                if (data.contains("YOURTURN")) { turn.add(data); }
                //System.out.println(data); // test
            }));
        }
        return client;
    }

    private Client(String ip, int port, Consumer<String> onReceiveCallback) {
        super(onReceiveCallback);
        this.ip = ip;
        this.port = port;
    }

    public LinkedList<String> getErorrs() {
        return errors;
    }

    public LinkedList<String> getInfo() {
        return info;
    }

    public LinkedList<String> getMatch() {
        return match;
    }

    public LinkedList<String> getMoves() { return moves; }
    
    public LinkedList<String> getChallenge() { return challenge; }

    public LinkedList<String> getScore() { return score; }

    public LinkedList<String> getTurn() { return turn; }

    public LinkedList<String> getPlayerlist() { return playerlist; }


}

