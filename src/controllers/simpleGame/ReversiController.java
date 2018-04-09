package controllers.simpleGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.stage.Stage;
import models.Game;
import views.GameView;

public class ReversiController extends SimpleGameController{
    //private Game gameModel;
    List<String> directions = new ArrayList<String>();

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
        if (super.legalMove(index) && nextToEnemy(index))
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
    }
    
    public boolean nextToEnemy(int index){
    if(gameModel.getPlayFieldAtIndex(index + 1) == 2){
	directions.add("EAST");
    }
    if(gameModel.getPlayFieldAtIndex(index - 1) == 2){
	directions.add("WEST");
    }
    if(gameModel.getPlayFieldAtIndex(index + 8) == 2){
	directions.add("SOUTH");
    } 
    if(gameModel.getPlayFieldAtIndex(index - 8) == 2){
	directions.add("NORTH");
    }
    if(gameModel.getPlayFieldAtIndex(index + 7) == 2){
	directions.add("SOUTH-WEST");
    }
    if(gameModel.getPlayFieldAtIndex(index - 7) == 2){
	directions.add("NORTH-EAST");
    }
    if(gameModel.getPlayFieldAtIndex(index + 9) == 2){
	directions.add("WEST-EAST");
    }
    if(gameModel.getPlayFieldAtIndex(index - 9) == 2){
	directions.add("NORTH-SOUTH");
    }
	return !(directions.isEmpty());
    }
    
    
    
    public boolean friendlyInLine(int index){
	if(directions.contains("WEST") || directions.contains("EAST")){
	     return checkHorizontal(index);
	}
	if(directions.contains("NORTH") || directions.contains("SOUTH")){
	    return checkHorizontal(index);
	}
	if(directions.contains("WEST") || directions.contains("SOUTH")){
	    return checkHorizontal(index);
	} else {
	    return false;
	}   
    }
    
    public boolean checkDiagonal(index){
        
    }
    
    public boolean checkVertical(index) {
    
    }
    
    // 8, 9, 10, 11, 12, 13, 14, 15
    public boolean checkHorizontal(int index) {
	int current = index;
	
    }
}
