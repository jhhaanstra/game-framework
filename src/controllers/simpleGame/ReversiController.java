package controllers.simpleGame;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.stage.Stage;
import models.Game;
import views.GameView;

public class ReversiController extends SimpleGameController{
    //private Game gameModel;

public ReversiController(Game model, Stage primaryStage, GameView gameView, HashMap info) {
        super(model, primaryStage, gameView, info);
        gameModel.setPlayFieldAtIndex(27, 1);
        gameModel.setPlayFieldAtIndex(28, 2);
        gameModel.setPlayFieldAtIndex(35, 2);
        gameModel.setPlayFieldAtIndex(36, 1);
	primaryStage.setTitle("Reversi!");
        updateGame();
	//gameModel = Game.getInstance();
    }

   public boolean legalMove(int index) {
        if (super.legalMove(index)) {

            for(int i = 0; i < gameModel.getPlayField().length; i++) {
               // System.out.println("Test");
                try {
                    if (gameModel.getPlayField()[index - 9] == 2) {
                        if (checkDir(-9, gameModel.getPlayField()[index - 9])) return true;
                    }
                    if (gameModel.getPlayField()[index - 8] == 2 ) {
                        if (checkDir(-8, gameModel.getPlayField()[index - 8])) return true;
                    }
                    if (gameModel.getPlayField()[index - 7] == 2 ) {
                        if (checkDir(-7, gameModel.getPlayField()[index - 7])) return true;
                    }
                    if (gameModel.getPlayField()[index - 1] == 2 ) {
                        if (checkDir(-1, gameModel.getPlayField()[index - 1])) return true;
                    }
                    if (gameModel.getPlayField()[index + 1] == 2 ) {
                        if (checkDir(1, gameModel.getPlayField()[index + 1])) return true;
                    }
                    if (gameModel.getPlayField()[index + 7] == 2 ) {
                        if (checkDir(7, gameModel.getPlayField()[index + 7])) return true;
                    }
                    if (gameModel.getPlayField()[index + 8] == 2 ) {
                        if (checkDir(8, gameModel.getPlayField()[index + 8])) return true;
                    }
                    if (gameModel.getPlayField()[index + 9] == 2 ) {
                        if (checkDir(9, gameModel.getPlayField()[index + 9])) return true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {}
            }
        }
        return false;
    }

    private boolean checkDir(int dir, int current) {
        try {
            ArrayList series = new ArrayList();
            series.add(current);
            current += dir;
            int currentValue = gameModel.getPlayField()[current];

            while (currentValue == 2) {
                series.add(gameModel.getPlayField()[current]);
                current += dir;
                currentValue = gameModel.getPlayField()[current];
            }

            if (gameModel.getPlayField()[current] == 1) {
                System.out.println("checkdir true");
                return true;
            }
            //    gameModel.updatePlayField(series);
        } catch (ArrayIndexOutOfBoundsException e) {}
        System.out.println("CheckDir false");
        return false;
    }

    private void switchDir(int dir, int current) throws ArrayIndexOutOfBoundsException {

    }

    public void checkLeftDir() {

    }

    public void checkRightDir() {

    }

    public void updateGame() {
        super.updateGame();
        for (int i = 0; i < gameView.getGrid().getChildren().size(); i++) {
            if (legalMove(i)) {
                setOnClick(i);
            }
        }
    }



    


}
