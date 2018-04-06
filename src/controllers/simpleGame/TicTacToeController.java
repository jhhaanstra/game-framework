package controllers.simpleGame;

import javafx.stage.Stage;
import models.Game;
import views.GameView;

import java.util.HashMap;

public class TicTacToeController extends SimpleGameController {

    public TicTacToeController(Game model, Stage primaryStage, GameView gameView, HashMap info) {
        super(model, primaryStage, gameView, info);
        updateGame();
    }

    public boolean legalMove(int index) {
        if (super.legalMove(index))
            return true;
        return false;
    }

    public void updateGame() {
        super.updateGame();
        for (int i = 0; i < gameView.getGrid().getChildren().size(); i++) {
            if (legalMove(i)) {
                setOnClick(i);
            }
        }
        primaryStage.setScene(this.gameView);

    }
}
