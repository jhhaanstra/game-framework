package controllers.simpleGame;

import java.util.HashMap;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.Game;
import views.GameView;

public class ReversiController extends SimpleGameController{
    //private Game gameModel;

public ReversiController(Game model, Stage primaryStage, GameView gameView, HashMap info, int size) {
        super(model, primaryStage, gameView, info, size);
	primaryStage.setTitle("Reversi!");
	//gameModel = Game.getInstance();
    }

   public boolean legalMove(int index) {
        if (super.legalMove(index))
            return true;
        return false;
    }

    public void updateGame() {
        super.updateGame();
        for (int i = 0; i < listRectangles.size(); i++) {
            if (legalMove(i)) {
                setOnClick(i);
            }
        }
    }

    public void updateView() {
        super.updateView();
        // Update je score
    }
    
    public boolean nextTooEnemy(int index){
	
	if(gameModel.getPlayFieldAtIndex(index + 1) == 2){
	    return true;
	} else if(gameModel.getPlayFieldAtIndex(index - 1) == 2){
	    return true;
	} else if(gameModel.getPlayFieldAtIndex(index + 8) == 2){
	    return true;
	} else if(gameModel.getPlayFieldAtIndex(index - 8) == 2){
	    return true;
	} else if(gameModel.getPlayFieldAtIndex(index + 7) == 2){
	    return true;
	} else if(gameModel.getPlayFieldAtIndex(index - 7) == 2){
	    return true;
	} else if(gameModel.getPlayFieldAtIndex(index + 9) == 2){
	    return true;
	} else if(gameModel.getPlayFieldAtIndex(index - 9) == 2){
	    return true;
	} else {
	return false;
	}
    }
}
