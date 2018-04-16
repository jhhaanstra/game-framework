package views;

import controllers.simpleGame.SimpleGameController;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import models.Game;
import models.Player;

public class GameView extends Scene {
    BorderPane pane;
    String playerColor;
    int playerAmount = 0;
    String opponentColor;
    int opponentAmount = 0;
    Label opponent = new Label();
    Label turn = new Label();

    public GameView() {
        super(new BorderPane());
        pane = (BorderPane) super.getRoot();
	
	setPlayerColor(SimpleGameController.getStartColor());
	setOpponentColor(SimpleGameController.getOpponentColor());

        // Create Scoreboard
        GridPane score = new GridPane();
        //score.setPrefSize(500,500); // added width and height
        score.add(new Label("Naam: "), 0, 0);
        score.add(new Label(Player.getInstance().getName()), 1, 0);
	score.add(new Label(playerColor), 2, 0);

        score.add(new Label("Tegenstander: "), 0, 1);
        score.add(opponent, 1, 1);
	score.add(new Label(opponentColor), 2, 1);
	
        score.add(new Label("Aan de beurt: "), 0, 2);
        score.add(turn, 1, 2);
        pane.setTop(score);
        pane.setPrefSize(500,500);
        pane.setCenter(new GridPane());
    }

    public void setGrid(GridPane grid) {
        pane.setCenter(grid);
    }

    public GridPane getGrid() {
        return (GridPane) pane.getCenter();
    }

    public void setOpponent(String name) {
        opponent.setText(name);
    }

    public void setTurn(String name) {
        turn.setText(name);
    }
    
    public void setAmount(int[] playField){
	for(int value : playField){
	    if(value == 1){
		playerAmount += 1;
	    } else if (value == 2) {
		opponentAmount +=1;
	    }
	}
    }
    
    public void setPlayerColor(Color color){
        if(color == Color.WHITE){
            playerColor = " White";
        } else {
            playerColor = " Black";
        }
    /*	if(color == Color.BLACK){
            playerColor = " Black";
        } else {
            playerColor = " White";
        }*/
    }
    
    public void setOpponentColor(Color color){
        if(color == Color.WHITE){
            opponentColor = " Black";
        } else {
            opponentColor = " White";
        }
/*        if(color == Color.BLACK){
            opponentColor = " White";
        } else  {
            opponentColor = " Black";
        }*/
    }
}
