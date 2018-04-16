package controllers.simpleGame;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import lib.Parser;
import models.*;
import views.GameView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TicTacToeController extends SimpleGameController {

    public TicTacToeController(Game model, Stage primaryStage, GameView gameView, HashMap info) {
        super(model, primaryStage, gameView, info);
        primaryStage.setTitle("Tic-Tac-Toe!");
        super.updateGame();
        new Thread(new MoveListener()).start();
    }

    @Override
    public boolean legalMove(int index) {
        return super.legalMove(index);
    }


    @Override
    public void updateGame() {
        super.updateGame();

        if (Settings.getInstance().getAI()) {
            ArrayList<Integer> choices = new ArrayList<>();
            for (int i = 0; i < gameView.getGrid().getChildren().size(); i++) {
                if (legalMove(i)) {
                    choices.add(i);
                }
            }
            if (!choices.isEmpty()) {
                Random rand = new Random();
                int x = rand.nextInt(choices.size());
                ClientCommands.sendMove(choices.get(x));
                updateBoard(choices.get(x));
                gameModel.updatePlayField(choices.get(x));
                gameModel.setYourTurn(false);
                updateGame();
            }
        } else {
            for (int i = 0; i < gameView.getGrid().getChildren().size(); i++) {
                if (legalMove(i)) {
                    setOnClick(i);
                }
            }
        }
        primaryStage.setScene(this.gameView);
    }

    public void updateBoard(int i) {
        gameModel.updatePlayField(i);
        gameModel.incrementTurn();
    }


    class MoveListener implements Runnable {
        boolean running = true;

        @Override
        public void run() {
            while(running) {

                if (Client.getInstance().getTurn().size() > 0) {
                    if (Client.getInstance().getTurn().getFirst().contains("DETAILS")) {
                        HashMap info = Parser.parse(Client.getInstance().getTurn());
                        if (!info.get("PLAYER").equals(Player.getInstance().getName())) {
                            Platform.runLater(() -> {
                                int index = Integer.valueOf((String) info.get("MOVE"));
                                updateBoard(index);
                            });
                        }
                    } else {
                        Client.getInstance().getTurn().removeFirst();
                        gameModel.setYourTurn(true);
                        Platform.runLater(() -> {
                            updateGame();
                        });
                    }
                }

                if (Client.getInstance().getScore().size() > 0) {
                    getScore();
                    running = false;
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }

}
