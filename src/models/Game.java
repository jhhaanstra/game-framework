package models;

public class Game {
    private int turn;
    private int gridWidth;
    private int gridHeight;
    private Player[] players;
    private int[] playField;

    public Game(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.players = players;
        this.playField = new int[gridWidth * gridHeight];

        // Test :)
        // int[] playField = {1, 0, 2, 0, 0, 2, 1, 0, 1};
        // this.playField = playField;
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

    public int[] getPlayField() {
        return playField;
    }

    public void updatePlayField(int index, int value) {
        playField[index] = value;
    }
}
