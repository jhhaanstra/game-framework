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

    public AIController(ReversiController gameController) {
        this.gameController = gameController;
        this.game = gameController.getGameModel();
        start();
    }

    public void doTurn() {
        ArrayList<Integer> choices = new ArrayList<>();
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
            } else {
                Random rand = new Random();
                int x = rand.nextInt(choices.size());
                System.out.println("This is the random number " + x);
                ClientCommands.sendMove(choices.get(x));
                gameController.updateBoard(choices.get(x), save.get(choices.get(x)));
            }
        }
    }
}