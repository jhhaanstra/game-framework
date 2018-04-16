package controllers.simpleGame;

import java.util.*;

import controllers.AIController;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import lib.Parser;
import models.*;
import views.GameView;

public class ReversiController extends SimpleGameController {
    //private Game gameModel;
    int[] directions = {-9, -8, -7, -1, 1, 7, 8, 9};
    int[] leftDir = {-9, -1, 7, -8, 8};
    int[] rightDir = {-7, 1, 9, -8, 8};
    private AIController ai;

    public ReversiController(Game model, Stage primaryStage, GameView gameView, HashMap info) {
        super(model, primaryStage, gameView, info);
        if (!gameModel.isYourTurn()) {
            gameModel.setPlayFieldAtIndex(27, 1);
            gameModel.setPlayFieldAtIndex(28, 2);
            gameModel.setPlayFieldAtIndex(35, 2);
            gameModel.setPlayFieldAtIndex(36, 1);
        } else {
            gameModel.setPlayFieldAtIndex(27, 2);
            gameModel.setPlayFieldAtIndex(28, 1);
            gameModel.setPlayFieldAtIndex(35, 1);
            gameModel.setPlayFieldAtIndex(36, 2);
        }

        occupied.add(27);
        occupied.add(28);
        occupied.add(35);
        occupied.add(36);

        primaryStage.setTitle("Reversi!");
        super.updateGame();
        new Thread(new MoveListener()).start();
    }

    public List getMovesList(int index) {
        //System.out.println("Dit is de waarde: " + index);
        List toChange = new ArrayList();
        if (super.legalMove(index)) {
            for (int i : directions) {
                try {
                    if (gameModel.getPlayField()[index + i] == (gameModel.isYourTurn() ? 2 : 1))
                        toChange.addAll(checkDir(i, index));
                } catch (ArrayIndexOutOfBoundsException e) {}
            }
        }
        return toChange;
    }

    public List getMoveReceivesList(int index) {
        List toChange = new ArrayList();
        for (int i : directions) {
            try {
                if (gameModel.getPlayField()[index + i] == (gameModel.isYourTurn() ? 2 : 1))
                    toChange.addAll(checkDir(i, index));
            } catch (ArrayIndexOutOfBoundsException e) {}
        }
        return toChange;
    }


    protected void setOnClick(int i, List toChange) {
        Rectangle r = (Rectangle) gameView.getGrid().getChildren().get(i);
        if (!occupied.contains(i)) {
            r.setFill(Color.YELLOW);
        }

        r.setOnMouseClicked(e -> {
            try {
                ClientCommands.sendMove(i);
                updateBoard(i, toChange);
                //gameView.setTurn(gameModel.getOpponent());
                gameModel.setYourTurn(false);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            r.setDisable(true);
        });
    }

    public void updateBoard(int i, List toChange) {
        gameModel.updatePlayField(i);
        gameModel.updatePlayField(toChange);
        gameModel.incrementTurn();
        occupied.add(i);
        updateBoard2();
        System.out.println("Hier ben ik");
        /*gameModel.setYourTurn(true);
        updateGame();*/
        possMoves.clear();
    }

    public Set getPossibleList() {
        Set check = new HashSet();
        for (int item : occupied) {
            if (item % 8 == 0) {
                for (int i : rightDir) {
                    try {
                        if (gameModel.getPlayField()[item + i] == 0) check.add(item + i);
                    } catch (ArrayIndexOutOfBoundsException e) {}
                }
            } else if (item + 1 % 8 == 0) {
                for (int i : leftDir) {
                    try {
                        if (gameModel.getPlayField()[item + i] == 0) check.add(item + i);
                    } catch (ArrayIndexOutOfBoundsException e) {}
                }
            } else {
                for (int i : directions) {
                    try {
                        if (gameModel.getPlayField()[item + i] == 0) check.add(item + i);
                    } catch (ArrayIndexOutOfBoundsException e) {}
                }
            }
        }
        possMoves.addAll(check);
        return check;
    }

    public void setAi(AIController ai) {
        this.ai = ai;
    }

    private List checkDir(int dir, int current) {
        try {
            ArrayList series = new ArrayList();
            current += dir;
            int currentValue = gameModel.getPlayField()[current];
            while (currentValue == (gameModel.isYourTurn() ? 2 : 1)) {
                if (current % 8 == 0 || (current + 1) % 8 == 0) {
                    if (dir == 8 || dir == -8) {
                        series.add(current);
                        current += dir;
                        currentValue = gameModel.getPlayField()[current];
                    } else
                        break;
                } else {
                    series.add(current);
                    current += dir;
                    currentValue = gameModel.getPlayField()[current];
                }
            }
            if (gameModel.getPlayField()[current] == (gameModel.isYourTurn() ? 1 : 2))
                return series;
        } catch (ArrayIndexOutOfBoundsException e) {}
        return new ArrayList();
    }

    public void updateBoard2() {
        super.updateGame();
    }

    public List getOccupied() {
        return occupied;
    }

    public void updateGame() {
        Platform.runLater(() -> {
            if (Settings.getInstance().getAI()) {
                while (ai == null) {}
                ai.doTurn();
                gameView.setTurn(gameModel.getOpponent());
                gameModel.setYourTurn(false);
            } else {
                getPossibleList();
                for (int i = 0; i < possMoves.size(); i++) {
                    List toChange = getMovesList(possMoves.get(i));
                    if (!toChange.isEmpty()) {
                        setOnClick(possMoves.get(i), toChange);
                    }
                }
            }
        });
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
                                updateBoard(index, getMoveReceivesList(index));
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
