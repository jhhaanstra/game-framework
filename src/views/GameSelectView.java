package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class GameSelectView extends Scene {
    private GridPane pane;
    private Button tttButton;

    public GameSelectView() {
        super(new GridPane());
        pane = (GridPane) super.getRoot();
        pane.setPadding(new Insets(12.5, 12.5, 12.5, 12.5));
        pane.setVgap(5.5);
        pane.setAlignment(Pos.CENTER);
        pane.add(new Label("Choose your game"), 0, 0);
        tttButton = new Button("TicTacToe");
        pane.add(tttButton, 0, 1);
        pane.add(new Button("Reversi"), 0, 2);
        this.pane = pane;
    }

    public GridPane getPane() {
        return pane;
    }

    public Button getTttButton() {
        return tttButton;
    }
}
