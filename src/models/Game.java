package models;

public class Game {
    private int turn;
    private int gridWidth;
    private int gridHeight;

    public Game(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
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
