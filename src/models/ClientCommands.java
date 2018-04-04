package models;

public class ClientCommands {


    public String login(String name) {
        Client.getInstance().send("login " + name);
        return getReturnMessage();
    }

    public String subscribeGame(String game) {
        Client.getInstance().send("subscribe " + game);
        return getReturnMessage();
    }

    public String getPlayers() {
        Client.getInstance().send("get playerlist");
        return getInfo();
    }

    public String challengePlayer(String player) {
        Client.getInstance().send("challenge 'kees' 'Tic-tac-toe'");
        return getReturnMessage();
    }

    public String sendMove(int move) {
        Client.getInstance().send("move " + move);
        return getInfo();
    }

    public String getReturnMessage() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (Client.getInstance().getErorrs().isEmpty()) {
            return "true";
        }
        else return (String) Client.getInstance().getErorrs().pop();
    }

    public String getInfo() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (String) Client.getInstance().getInfo().pop();

    }

}
