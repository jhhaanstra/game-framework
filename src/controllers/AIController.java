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
    ArrayList<Integer> choices = new ArrayList<>();

    public AIController(ReversiController gameController) {
        this.gameController = gameController;
        this.game = gameController.getGameModel();
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
        if (!choices.isEmpty()) {
            if (choices.contains(0)) {
                ClientCommands.sendMove(0);
                gameController.updateBoard(0, save.get(0));
            } else if (choices.contains(7)) {
                ClientCommands.sendMove(7);
                gameController.updateBoard(7, save.get(7));
            } else if (choices.contains(56)) {
                ClientCommands.sendMove(56);
                gameController.updateBoard(56, save.get(56));
            } else if (choices.contains(63)) {
                ClientCommands.sendMove(63);
                gameController.updateBoard(63, save.get(63));
            }
            // Hier moeten de andere functies komen, dus check inner 4 en check inner 16 bijv...
            else {
                leftUpperCorner(gameController.getOccupied(), choices);
                rightUpperCorner(gameController.getOccupied(), choices);
                leftLowerCorner(gameController.getOccupied(), choices);
                rightLowerCorner(gameController.getOccupied(), choices);
                Random rand = new Random();
                int x = rand.nextInt(choices.size());
                System.out.println("This is the random number " + x);
                ClientCommands.sendMove(choices.get(x));
                gameController.updateBoard(choices.get(x), save.get(choices.get(x)));
            }
        }
        choices.clear();
    }

    public void leftUpperCorner(List occupied, List choices) {
        if (!occupied.contains(0)) {
            int[] items = {1, 8, 9};
            for (int index = 0; index < items.length; index++) {
                if (choices.contains(items[index]) && choices.size() > 1) {
                    System.out.println(choices.size());
                    choices.remove(choices.indexOf(items[index]));
                }
            }
        }
    }

    public void rightUpperCorner(List occupied, List choices) {
        if (!occupied.contains(7)) {
            int[] items = {6, 14, 15};
            for (int index = 0; index < items.length; index++) {
                if (choices.contains(items[index]) && choices.size() > 1) {
                    choices.remove(choices.indexOf(items[index]));
                    System.out.println(choices.size());
                }
            }
        }
    }

    public void leftLowerCorner(List occupied, List choices) {
        if (!occupied.contains(56)) {
            int[] items = {48, 49, 57};
            for (int index = 0; index < items.length; index++) {
                if (choices.contains(items[index]) && choices.size() > 1) {
                    choices.remove(choices.indexOf(items[index]));
                    System.out.println(choices.size());
                }
            }
        }
    }

    public void rightLowerCorner(List occupied, List choices) {
        if (!occupied.contains(63)) {
            int[] items = {54, 55, 62};
            for (int index = 0; index < items.length; index++) {
                if (choices.contains(items[index]) && choices.size() > 1) {
                    choices.remove(choices.indexOf(items[index]));
                    System.out.println(choices.size());
                }
            }
        }
    }

}