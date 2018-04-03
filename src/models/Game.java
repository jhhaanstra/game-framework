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
        //int[] playField = {1, 0, 2, 0, 0, 2, 1, 0, 1};
        //this.playField = playField;
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
    
    public int getIndex(int y, int x){
	int sum = 0;
	switch(y) {
	    case 0:
		switch(x) {
		    case 0: sum = 0; break;
		    case 1: sum = 1; break;
		    case 2: sum = 2; break;
		} break;
	    case 1:
		switch(x) {
		    case 0: sum = 3; break;
		    case 1: sum = 4; break;
		    case 2: sum = 5; break;	
		} break;
	    case 2:
		switch(x) {
		    case 0: sum = 6; break;
		    case 1: sum = 7; break;
		    case 2: sum = 8; break;		
		} break;
	}
	return sum;
    }
    
    public int getPlayFieldAtIndex(int index){
	return playField[index];
    }

    public void updatePlayField(int y, int x, boolean player) {
        if(player == false){
	    playField[getIndex(y, x)] = 1;
	} else {
	    playField[getIndex(y,x)] = 2;
	}
    }
}
