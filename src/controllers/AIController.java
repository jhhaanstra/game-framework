package controllers;

import controllers.simpleGame.ReversiController;
import controllers.simpleGame.SimpleGameController;
import javafx.application.Platform;
import lib.GameStateNode;
import models.ClientCommands;
import models.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AIController extends Thread{
    private boolean playing = true;
    private ReversiController gameController;
    private Game game;

    public AIController(ReversiController gameController) {
        this.gameController = gameController;
        this.game = gameController.getGameModel();
        start();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void calculateOptions(int[] playfield) {
        boolean tempTurn = game.isYourTurn();
        int turnCounter = game.getTurn();
        GameStateNode currentNode = new GameStateNode(playfield);

        for (int x = 0; x < playfield.length; x++) {
            if (gameController.legalMove(playfield[x], currentNode.getPlayfield())) {
                int[] temp = playfield;
                temp[x] = (tempTurn) ? 1 : 2;
                currentNode.addNode(x, new GameStateNode(temp));
            }
        }

        System.out.println(currentNode);
    }

    @Override
    public void run() {
        /*while (playing) {
            if (game.isYourTurn()) {
                gameController.updateGame();

                if (game.isYourTurn()) {
                    Platform.runLater(() -> {
                        ArrayList<Integer> possMoves = new ArrayList(gameController.getPossibleList());
                        for (int i = 0; i < possMoves.size(); i++) {
                            List toChange = gameController.getMovesList(possMoves.get(i));
                            if (!toChange.isEmpty()) {
                                ClientCommands.sendMove(possMoves.get(i));
                                gameController.updateGameState(possMoves.get(i), toChange);
                                //gameView.setTurn(game.getOpponent());
                                game.setYourTurn(false);
                                break;
                                //setOnClick(possMoves.get(i), toChange);
                            }
                        }
                        //check.clear();
                        possMoves.clear();
                    });
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}

        }*/
    }
}