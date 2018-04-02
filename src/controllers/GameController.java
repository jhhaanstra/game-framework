package controllers;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.Game;
import views.GameView;
import views.TicTacToeView;

public class GameController {
    private Game gameModel;
    private GameView gameView;

    public GameController(Game model, Stage primaryStage) {
        this.gameModel = model;
        this.gameView = new TicTacToeView(generateGrid());
        primaryStage.setTitle("Tic tac toe");
        primaryStage.setScene((Scene) this.gameView);
    }

    private GridPane generateGrid() {
        GridPane grid = new GridPane();

        for (int y = 0; y < gameModel.getGridHeight(); y++) {
            for (int x = 0; x < gameModel.getGridWidth(); x++) {
                Rectangle r = new Rectangle(100, 100);
                r.setFill(Color.WHITE);
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

    public GameView getGameView() {
        return gameView;
    }
}
