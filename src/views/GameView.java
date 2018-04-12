package views;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import models.Player;

public class GameView extends Scene {
    BorderPane pane;
    Label opponent = new Label();
    Label turn = new Label();
    Label name = new Label(Player.getInstance().getName());

    public GameView() {
        super(new BorderPane());
        pane = (BorderPane) super.getRoot();

        // Create Scoreboard
        GridPane score = new GridPane();
        //score.setPrefSize(500,500); // added width and height
        score.add(new Label("Naam: "), 0, 0);
	//name.setTextFill(Color.WHITE);
        score.add(name, 1, 0);

        score.add(new Label("Tegenstander: "), 0, 1);
	//opponent.setTextFill(Color.BLACK);
        score.add(opponent, 1, 1);

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
}
