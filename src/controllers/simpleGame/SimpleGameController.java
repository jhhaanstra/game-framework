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
	        int Height = y;
            for (int x = 0; x < gameModel.getGridWidth(); x++) {
                int index = (y * 3) + x;
		        int Width = x;
                Rectangle r = new Rectangle(100, 100);
                switch (playField[index]) {
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
                    if(gameModel.getPlayFieldAtIndex(index) == 0){
                        r.setFill((gameModel.getTurn() % 2 == 0) ? Color.BLUE : Color.RED);
                        gameModel.incrementTurn();
                        gameModel.updatePlayField(index, (gameModel.getTurn() % 2 == 0));
                    }
                    updateGame(index, 1);

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
    public void updateGame(int index, int value) {
        if (legalMove()) {
            //gameModel.updatePlayField(index, value);
            gameView.setGrid(generateGrid(gameModel.getPlayField()));
        }

    }

    @Override
    public void updateView() {
        //pass
    }
}
