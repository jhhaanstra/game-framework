package controllers.simpleGame;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.Game;
import views.GameView;
import views.TicTacToeView;

public class SimpleGameController implements GameController {
    private Game gameModel;
    private GameView gameView;

    public SimpleGameController(Game model, Stage primaryStage) {
        this.gameModel = model;
        this.gameView = new TicTacToeView(generateGrid(gameModel.getPlayField()));
        primaryStage.setScene((Scene) this.gameView);
    }

    private GridPane generateGrid(int[] playField) {
        GridPane grid = new GridPane();

        for (int y = 0; y < gameModel.getGridHeight(); y++) {
            for (int x = 0; x < gameModel.getGridWidth(); x++) {
                Rectangle r = new Rectangle(100, 100);
                switch (playField[(y * 3) + x]) {
                    case 0:
                        r.setFill(Color.WHITE);
                        break;
                    case 1:
                        r.setFill(Color.BLUE);
                        break;
                    case 2:
                        r.setFill(Color.RED);
                        break;
                }

                r.setStroke(Color.BLACK);
                r.setOnMouseClicked(e -> {
                    r.setFill((gameModel.getTurn() % 2 == 0) ? Color.BLUE : Color.RED);
                    gameModel.incrementTurn();
                });
                grid.add(r, x, y);
            }
        }
        return grid;
    }

    @Override
    public boolean legalMove() {
        return true;
    }

    @Override
    public void updateGame() {

    }

    @Override
    public void updateView() {
        //pass
    }
}
