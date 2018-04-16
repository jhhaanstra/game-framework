package views;

import controllers.simpleGame.SimpleGameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import models.Game;
import models.Player;
import javafx.scene.text.Text;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;


public class GameView extends Scene {
    // create media for a sound
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

        Label Title = new Label("Re");
	
	setPlayerColor(SimpleGameController.getStartColor());
	setOpponentColor(SimpleGameController.getOpponentColor());

        // Create Scoreboard
        GridPane score = new GridPane();

        // CREATES LABELS
        Label name = new Label("Naam : ");
        Label aandebeurt = new Label("Aan de beurt: ");
        Label tegenstander = new Label("Tegenstander : ");
        Label footertext = new Label("Build by ReverseIT");

       // SETS COLOR
        tegenstander.setTextFill(Color.web("#0076a3"));
        footertext.setTextFill(Color.web("#0076a3"));
        tegenstander.setTextFill(Color.web("#0076a3"));
        name.setTextFill(Color.web("#0076a3"));
        aandebeurt.setTextFill(Color.web("#0076a3"));

        score.add(new Label(Player.getInstance().getName()), 1, 0);
        score.add(new Label(playerColor), 2, 0);

        // Adds the labels to the score GridPane
        score.add(name, 0,0);
        score.add(tegenstander, 0, 1);
        score.add(aandebeurt,0,2);
        score.add(opponent, 1, 1);

	    score.add(new Label(opponentColor), 2, 1);
        score.add(turn, 1, 2);
        score.setAlignment(Pos.TOP_LEFT);
        score.setMargin(name, new Insets(15,0,15,50));
        score.setMargin(tegenstander, new Insets(15,0,15,50));
        score.setMargin(aandebeurt, new Insets(15,0,15,50));


        // Create Footer
        GridPane footer = new GridPane();
        footer.setAlignment(Pos.CENTER);
        footer.add(footertext,0,0);
        footer.setMargin(footertext, new Insets(15,0,15,0));

        pane.setTop(score);
        pane.setPrefSize(500,600);
        pane.setCenter(new GridPane());
        pane.setBottom((footer));

    }

    public void setGrid(GridPane grid) {
        pane.setCenter(grid);
        grid.setAlignment(Pos.CENTER);

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
    }
    
    public void setOpponentColor(Color color){
        if(color == Color.WHITE){
            opponentColor = " Black";
        } else {
            opponentColor = " White";
        }
    }
}
