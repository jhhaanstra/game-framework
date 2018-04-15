package controllers;

import controllers.simpleGame.ReversiController;
import controllers.simpleGame.SimpleGameController;
import javafx.application.Platform;
import lib.GameStateNode;
import models.ClientCommands;
import models.Game;

import java.util.*;

public class AIController extends Thread{
    private boolean playing = true;
    private ReversiController gameController;
    private Game game;
    private ArrayList<Integer> choices;
    private HashMap<Integer, List> save;
    private Set<Integer> possMoves;

    public AIController(ReversiController gameController) {
        this.gameController = gameController;
        this.game = gameController.getGameModel();
        choices = new ArrayList<>();
        save = new HashMap<>();
        possMoves = gameController.getPossibleList();
        start();
    }

    public void doTurn() {
        //ArrayList<Integer> choices = new ArrayList<>();
        //HashMap<Integer, List> save = new HashMap<>();

        //Set<Integer> possMoves = gameController.getPossibleList();
        for (int i : possMoves) {
            List toChange = gameController.getMovesList(i);
            if (!toChange.isEmpty()) {
                save.put(i, toChange);
                choices.add(i);
            }
        }
        if (!choices.isEmpty()) {
            if (choices.contains(0)) {
                ClientCommands.sendMove(0);
                gameController.updateBoard(0, save.get(0));
                emptyLists();
            } else if (choices.contains(7)) {
                ClientCommands.sendMove(7);
                gameController.updateBoard(7, save.get(7));
                emptyLists();
            } else if (choices.contains(56)) {
                ClientCommands.sendMove(56);
                gameController.updateBoard(56, save.get(56));
                emptyLists();
            } else if (choices.contains(63)) {
                ClientCommands.sendMove(63);
                gameController.updateBoard(63, save.get(63));
                emptyLists();
            } else if (Game.getPlayFieldAtIndex(27) == 2 && checkLineContains(27) != 0) {
                ClientCommands.sendMove(checkLineContains(27));
                gameController.updateBoard(checkLineContains(27), save.get(checkLineContains(27)));
                emptyLists();
            } else if (Game.getPlayFieldAtIndex(28) == 2 && checkLineContains(28) != 0) {
                ClientCommands.sendMove(checkLineContains(28));
                gameController.updateBoard(checkLineContains(28), save.get(checkLineContains(28)));
                emptyLists();
            } else if (Game.getPlayFieldAtIndex(35) == 2 && checkLineContains(35) != 0) {
                ClientCommands.sendMove(checkLineContains(35));
                gameController.updateBoard(checkLineContains(35), save.get(checkLineContains(35)));
                emptyLists();
            } else if (Game.getPlayFieldAtIndex(36) == 2 && checkLineContains(36) != 0) {
                ClientCommands.sendMove(checkLineContains(36));
                gameController.updateBoard(checkLineContains(36), save.get(checkLineContains(36)));
                emptyLists();
            }


            // Hier moeten de andere functies komen, dus check inner 4 en check inner 16 bijv...

            else {
                Random rand = new Random();
                int x = rand.nextInt(choices.size());
                System.out.println("This is the random number " + x);
                ClientCommands.sendMove(choices.get(x));
                gameController.updateBoard(choices.get(x), save.get(choices.get(x)));
                emptyLists();
            }
        }
    }
    
    public int checkLineContains(int index){
        for(int choice : choices){
                    if(gameController.getMoveReceivesList(choice).contains(index)){
                        return choice;
                    } 
                }
        return 0;
    }
    
    public void emptyLists(){
        choices.clear();
        save.clear();
        possMoves = gameController.getPossibleList();
    }
}