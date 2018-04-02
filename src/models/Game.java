package models;

public class Game {
    private int turn;
    private int gridWidth;
    private int gridHeight;
    private Player[] players;

    public Game(int gridWidth, int gridHeight, Player[] players) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.players = players;
    }

    public int getTurn() {
        return turn;
    }

    public int incrementTurn() {
        return ++turn;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }
}
