package views;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class GameView extends Scene {
    BorderPane pane;

    public GameView() {
        super(new BorderPane());
        pane = (BorderPane) super.getRoot();
        pane.setCenter(new GridPane());
    }

    public void setGrid(GridPane grid) {
        pane.setCenter(grid);
    }

    public GridPane getGrid() {
        return (GridPane) pane.getCenter();
    }
}