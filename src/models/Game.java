package models;

import java.util.List;

public class Game {
    private int turn;
    private boolean yourTurn;
    private int gridWidth;
    private int gridHeight;
    private static int[] playField;
    private String opponent;

    public Game(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.playField = new int[gridWidth * gridHeight];
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getOpponent() {
        return opponent;
    }

    public int getTurn() {
        return turn;
    }

    public boolean isYourTurn() {
        return yourTurn;
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

    public static int getPlayFieldAtIndex(int index){
        if (index < 0 || index > 63)return 1;
        else return playField[index];
    }
    
    public void setPlayFieldAtIndex(int index, int person){
        playField[index] = person;
    }

    public void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public void updatePlayField(int index) {
        playField[index] = (isYourTurn()) ? 1 : 2;
    }

    public void updatePlayField(List indexes) {
        indexes.forEach(e -> { playField[(int) e] = (isYourTurn()) ? 1 : 2; });
    }
}
