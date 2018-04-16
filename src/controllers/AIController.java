package controllers;

import controllers.simpleGame.ReversiController;
import controllers.simpleGame.SimpleGameController;
import javafx.application.Platform;
import lib.GameStateNode;
import models.ClientCommands;
import models.Game;

import java.util.*;

public class AIController extends Thread{
    private ReversiController gameController;
    private ArrayList<Integer> choices;
    private ArrayList<Integer> innerST;
    private ArrayList<Integer> innerPoss;
    private HashMap<Integer, List> save;

    public AIController(ReversiController gameController) {
        this.gameController = gameController;
        choices = new ArrayList<>();
        innerST = new ArrayList<>();
        innerPoss = new ArrayList<>();
        innerST.addAll(Arrays.asList(19, 20, 26, 29, 34, 37, 43, 44));
        save = new HashMap<>();
        start();
    }

    public void doTurn() {
        HashMap<Integer, List> save = new HashMap<>();

        Set<Integer> possMoves = gameController.getPossibleList();
        for (int i : possMoves) {
            List toChange = gameController.getMovesList(i);
            if (!toChange.isEmpty()) {
                save.put(i, toChange);
                choices.add(i);
            }
        }

	for(int option : innerST){
	    if(Game.getPlayFieldAtIndex(option) == 2 && checkLineContains(option) != 0)
		    innerPoss.add(checkLineContains(option));
	}

        if (!choices.isEmpty()) {
            checkCorner(gameController.getOccupied(), 0, new int[] {1, 8, 9});
            checkCorner(gameController.getOccupied(), 7, new int[] {6, 14, 15});
            checkCorner(gameController.getOccupied(), 56, new int[] {48, 49, 57});
            checkCorner(gameController.getOccupied(), 63, new int[] {54, 55, 62});
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
            } else if (gameController.getOccupied().size() < 32) {
                for (int y = 2; y < 6; y++) {
                    for (int x = 2; x < 6; x++) {
                        if (Game.getPlayFieldAtIndex((y * 8) + x) == 2 && checkLineContains((y * 8) + x) != 0) {
                            ClientCommands.sendMove(checkLineContains((y * 8) + x));
                            gameController.updateBoard(checkLineContains((y * 8) + x), save.get(checkLineContains((y * 8) + x)));
                            emptyLists();
                        }
                    }
                }
            } else {
                Random rand = new Random();
                int x = rand.nextInt(choices.size());
                ClientCommands.sendMove(choices.get(x));
                gameController.updateBoard(choices.get(x), save.get(choices.get(x)));
                emptyLists();
            }
        }
    }

    private int checkLineContains(int index){
        for(int choice : choices){
            if(gameController.getMoveReceivesList(choice).contains(index)) return choice;
        }
        return 0;
    }
    
    private void emptyLists() {
        choices.clear();
        save.clear();
        innerPoss.clear();
    }

    private void checkCorner(List occupied, int startPoss, int[] items) {
        if (!occupied.contains(startPoss)) {
            for (int index = 0; index < items.length; index++) {
                if (choices.contains(items[index]) && choices.size() > 1) {
                    choices.remove(choices.indexOf(items[index]));
                }
            }
        }
    }
}