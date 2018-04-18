package models;

public class Player {
    private static Player player;
    private static String name;
    private static String game;

    private Player() {}

    public static synchronized Player getInstance() {
        if (player == null) {
            player = new Player();
        }
        return player;
    }

    public void setName(String playerName) {
        name = playerName;
    }

    public void setGame (String newGame) { game = newGame;}

    public String getName() {
        return name;
    }

    public String getGame() {
        return game;
    }
}
