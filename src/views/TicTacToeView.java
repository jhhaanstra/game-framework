package views;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class TicTacToeView extends Scene implements GameView {
    private GridPane grid;

    public TicTacToeView(GridPane grid) {
        super(grid);
    }

    @Override
    public void setGrid(GridPane grid) {
        this.grid = grid;
    }

    @Override
    public GridPane getGrid() {
        return grid;
    }
}
