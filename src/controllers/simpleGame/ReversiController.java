package controllers.simpleGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import lib.Parser;
import models.Client;
import models.ClientCommands;
import models.Game;
import models.Player;
import views.GameView;

public class ReversiController extends SimpleGameController{
    //private Game gameModel;
    int[] positionsArr = {-9, -8, -7 , -1, 1, 7, 8, 9};
    int[] positionLeft = {-8, -7, 1, 8, 9};
    int[] positionRight = {-9, -8, -1, 7, 8};
    int[] zero = {1, 8, 9};
    int[] fiftysix = {1, -8, -7};
    int[] seven = {-1, 7, 8};
    int[] lowestboundary = {7, 8, 9, -1 , 1};
    int[] sixtythree = {-1, -9, -8};
    int[] highestBoundary = {-1, -9, -8, -7, 1};

    private int opponentNumber;

public ReversiController(Game model, Stage primaryStage, GameView gameView, HashMap info) {
        super(model, primaryStage, gameView, info);
        if (!gameModel.isYourTurn()) {
            gameModel.setPlayFieldAtIndex(27, 1);
            occupied.add(27);
            gameModel.setPlayFieldAtIndex(28, 2);
            occupied.add(28);
            gameModel.setPlayFieldAtIndex(35, 2);
            occupied.add(35);
            gameModel.setPlayFieldAtIndex(36, 1);
            occupied.add(36);
        } else {
            gameModel.setPlayFieldAtIndex(27, 2);
            occupied.add(27);
            gameModel.setPlayFieldAtIndex(28, 1);
            occupied.add(28);
            gameModel.setPlayFieldAtIndex(35, 1);
            occupied.add(35);
            gameModel.setPlayFieldAtIndex(36, 2);
            occupied.add(36);
        }
        new Thread(new MoveListener()).start();
	    primaryStage.setTitle("Reversi!");
        updateGame();
	//gameModel = Game.getInstance();
    }

   public List getMovesList(int index) {
    //System.out.println("Dit is de waarde: " + index);
        if (super.legalMove(index)) {
            List toChange = new ArrayList();
            try {
                if (gameModel.getPlayField()[index - 9]  == (gameModel.isYourTurn() ? 2 : 1)) {
                    //System.out.println(-9);
                    toChange.addAll(checkDir(-9, index));
                }
            } catch (ArrayIndexOutOfBoundsException e) {}
            try {
                if (gameModel.getPlayField()[index - 8] == (gameModel.isYourTurn() ? 2 : 1)) {
                    //System.out.println(-8);
                    toChange.addAll(checkDir(-8, index));
                }
            } catch (ArrayIndexOutOfBoundsException e) {}
            try {
                if (gameModel.getPlayField()[index - 7] == (gameModel.isYourTurn() ? 2 : 1)) {
                    //System.out.println(-7);
                    toChange.addAll(checkDir(-7, index));
                }
            } catch (ArrayIndexOutOfBoundsException e) {}
            try {
                if (gameModel.getPlayField()[index - 1] == (gameModel.isYourTurn() ? 2 : 1)) {
                    //System.out.println(-1);
                    toChange.addAll(checkDir(-1, index));
                }
            } catch (ArrayIndexOutOfBoundsException e) {}
            try {
                if (gameModel.getPlayField()[index + 1] == (gameModel.isYourTurn() ? 2 : 1)) {
                    //System.out.println(1);
                    toChange.addAll(checkDir(1, index));
                }
            } catch (ArrayIndexOutOfBoundsException e) {}
            try {
                if (gameModel.getPlayField()[index + 7] == (gameModel.isYourTurn() ? 2 : 1)) {
                    //System.out.println(7);
                    toChange.addAll(checkDir(7, index));
                }
            } catch (ArrayIndexOutOfBoundsException e) {}
            try {
                if (gameModel.getPlayField()[index + 8] == (gameModel.isYourTurn() ? 2 : 1)) {
                    //System.out.println(8);
                    toChange.addAll(checkDir(8, index));
                }
            } catch (ArrayIndexOutOfBoundsException e) {}
            try {
                if (gameModel.getPlayField()[index + 9] == (gameModel.isYourTurn() ? 2 : 1)) {
                    //out.println(9);
                    toChange.addAll(checkDir(9, index));
                }
            } catch (ArrayIndexOutOfBoundsException e) {}

            return toChange;

        }

       return new ArrayList();
    }

    public List getMoveReceivesList(int index) {
        List toChange = new ArrayList();
        try {
            if (gameModel.getPlayField()[index - 9]  == (gameModel.isYourTurn() ? 2 : 1)) {
                //System.out.println(-9);
                toChange.addAll(checkDir(-9, index));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
        try {
            if (gameModel.getPlayField()[index - 8] == (gameModel.isYourTurn() ? 2 : 1)) {
               // System.out.println(-8);
                toChange.addAll(checkDir(-8, index));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
        try {
            if (gameModel.getPlayField()[index - 7] == (gameModel.isYourTurn() ? 2 : 1)) {
               // System.out.println(-7);
                toChange.addAll(checkDir(-7, index));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
        try {
            if (gameModel.getPlayField()[index - 1] == (gameModel.isYourTurn() ? 2 : 1)) {
               // System.out.println(-1);
                toChange.addAll(checkDir(-1, index));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
        try {
            if (gameModel.getPlayField()[index + 1] == (gameModel.isYourTurn() ? 2 : 1)) {
               // System.out.println(1);
                toChange.addAll(checkDir(1, index));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
        try {
            if (gameModel.getPlayField()[index + 7] == (gameModel.isYourTurn() ? 2 : 1)) {
                //System.out.println(7);
                toChange.addAll(checkDir(7, index));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
        try {
            if (gameModel.getPlayField()[index + 8] == (gameModel.isYourTurn() ? 2 : 1)) {
                //System.out.println(8);
                toChange.addAll(checkDir(8, index));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
        try {
            if (gameModel.getPlayField()[index + 9] == (gameModel.isYourTurn() ? 2 : 1)) {
                //System.out.println(9);
                toChange.addAll(checkDir(9, index));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}

        //}

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
                updateGameState(i, toChange);
                gameView.setTurn(gameModel.getOpponent());
                gameModel.setYourTurn(false);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            r.setDisable(true);
        });
    }

    public void updateGameState(int i, List toChange) {
        gameModel.updatePlayField(i);
        gameModel.updatePlayField(toChange);
        gameModel.incrementTurn();
        occupied.add(i);
        gameModel.setYourTurn(true);
        updateGame();
        possMoves.clear();
    }

    public Set getPossibleList() {
        int current;
        for (int item : occupied) {
            if (item == 0 ) {
                for (int x = 0; x < zero.length; x++) {
                    current = item + zero[x];
                    if (gameModel.getPlayField()[current] == 0) {
                        check.add(current);
                    }
                }
            } else if (item == 7) {
                for (int x = 0; x < seven.length; x++) {
                    current = item + seven[x];
                    if (gameModel.getPlayField()[current] == 0) {
                        check.add(current);
                    }
                }
            } else if (item == 56) {
                for (int x = 0; x < fiftysix.length; x++) {
                    current = item + fiftysix[x];
                    if (gameModel.getPlayField()[current] == 0) {
                        check.add(current);
                    }
                }
            } else if (item == 63) {
                for (int x = 0; x < sixtythree.length; x++) {
                    current = item + sixtythree[x];
                    if (gameModel.getPlayField()[current] == 0) {
                        check.add(current);
                    }
                }
            } else if(item > 0 && item < 7) {
                for (int x = 0; x < lowestboundary.length; x++) {
                    current = item + lowestboundary[x];
                    if (gameModel.getPlayField()[current] == 0) {
                        check.add(current);
                    }
                }
            } else if (item > 56 && item < 63) {
                for (int x = 0; x < highestBoundary.length; x++) {
                    current = item + highestBoundary[x];
                    if (gameModel.getPlayField()[current] == 0) {
                        check.add(current);
                    }
                }
            } else if (item % 8 == 0) {
                for (int x = 0; x < positionLeft.length; x++) {
                    current = item + positionLeft[x];
                    if (gameModel.getPlayField()[current] == 0) {
                        check.add(current);
                    }
                }
            } else if ((item + 1) % 8 == 0) {
                for (int x = 0; x < positionRight.length; x++) {
                    current = item + positionRight[x];
                    if (gameModel.getPlayField()[current] == 0) {
                        check.add(current);
                    }
                }
            } else {
                for (int x = 0; x < positionsArr.length; x++) {
                    current = item + positionsArr[x];
                    if (gameModel.getPlayField()[current] == 0) {
                        if (current >= 0 && current <= 63) {
                            check.add(current);
                        }
                    }
                }
            }
        }
        possMoves.addAll(check);
        return check;
    }

    public List stripPossibleList() {

        int count = 0;
        int current;
        for (int i = 0; i < possMoves.size(); i++) {
            current = possMoves.get(i);
            if (current >= 0 && current <= 63) {
                if (current == 0) {
                    int test = current;
                    for (int x = 0; x < zero.length; x++) {
                        current = test;
                        current += zero[x];
                        if (gameModel.getPlayField()[current] == (gameModel.isYourTurn() ? 2 : 1)) {
                            count++;
                        }
                    }
                } else if (current == 7) {
                    int test = current;
                    for (int x = 0; x < seven.length; x++) {
                        current = test;
                        current += seven[x];
                        if (gameModel.getPlayField()[current] == (gameModel.isYourTurn() ? 2 : 1)) {
                            count++;
                        }
                    }
                }
                else if (current == 56) {
                    int test = current;
                    for (int x = 0; x < fiftysix.length; x++) {
                        current = test;
                        current += fiftysix[x];
                        if (gameModel.getPlayField()[current] == (gameModel.isYourTurn() ? 2 : 1)) {
                            count++;
                        }
                    }
                }
                else if (current == 63) {
                    int test = current;
                    for (int x = 0; x < sixtythree.length; x++) {
                        current = test;
                        current += sixtythree[x];
                        if (gameModel.getPlayField()[current] == (gameModel.isYourTurn() ? 2 : 1)) {
                            count++;
                        }
                    }
                }
                else if(current > 0 && current < 7) {
                    int test = current;
                    for (int x = 0; x < lowestboundary.length; x++) {
                        current = test;
                        current += lowestboundary[x];
                        if (gameModel.getPlayField()[current] == (gameModel.isYourTurn() ? 2 : 1)) {
                            count++;
                        }
                    }
                } else if (current > 56 && current < 63) {
                    int test = current;
                    for (int x = 0; x < highestBoundary.length; x++) {
                        current = test;
                        current += highestBoundary[x];
                        if (gameModel.getPlayField()[current] == (gameModel.isYourTurn() ? 2 : 1)) {
                            count++;
                        }
                    }
                } else if (current % 8 == 0) {
                    int test = current;
                    for (int x = 0; x < positionLeft.length; x++) {
                        current = test;
                        current += positionLeft[x];
                        if (gameModel.getPlayField()[current] == (gameModel.isYourTurn() ? 2 : 1)) {
                            count++;
                        }
                    }
                } else if ((current + 1) % 8 == 0) {
                    int test = current;
                    for (int x = 0; x < positionRight.length; x++) {
                        current = test;
                        current += positionRight[x];
                        if (gameModel.getPlayField()[current] == (gameModel.isYourTurn() ? 2 : 1)) {
                            count++;
                        }
                    }
                } else {
                    int test = current;
                    for (int x = 0; x < positionsArr.length; x++) {
                        current = test;
                        current = possMoves.get(i) - positionsArr[x];
                        if (gameModel.getPlayField()[current] == (gameModel.isYourTurn() ? 2 : 1)) {
                            count++;
                        }
                    }
                }
            }


            if (count == 0) {
                possMoves.remove(possMoves.get(i));
            }
            count = 0;
        }
        /*System.out.println();
        System.out.println("PRINT ALLE MOVES");
        for (int i = 0; i < possMoves.size(); i++) {
            if (gameModel.isYourTurn()) {
                System.out.print(possMoves.get(i) + " , ");
            }
        }*/

        return possMoves;
    }

    private List checkDir(int dir, int current) {
        try {
            ArrayList series = new ArrayList();
            current += dir;
            int currentValue = gameModel.getPlayField()[current];
            //System.out.println(current + " wordt vergeleken met " + currentValue);
            while (currentValue == (gameModel.isYourTurn() ? 2 : 1) && current % 8 != 0 && (current + 1) % 8 != 0) {
                series.add(current);
                current += dir;
                currentValue = gameModel.getPlayField()[current];
                //System.out.println(current + " wordt vergeleken met " + currentValue);
            }
            //System.out.println(current + " wordt vergeleken met " + currentValue);
            if (gameModel.getPlayField()[current] == (gameModel.isYourTurn() ? 1 : 2)) {
                return series;
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
        return new ArrayList();
    }

    private void switchDir(int dir, int current) throws ArrayIndexOutOfBoundsException {

    }

    public void checkLeftDir() {

    }

    public void checkRightDir() {

    }

    public void updateGame() {
        super.updateGame();

        if (gameModel.isYourTurn()) {
            Platform.runLater(() -> {
                getPossibleList();
                stripPossibleList();

                for (int i = 0; i < possMoves.size(); i++) {
                    List toChange = getMovesList(possMoves.get(i));
                    if (!toChange.isEmpty()) {
                        setOnClick(possMoves.get(i), toChange);
                    }
                }
            check.clear();
            possMoves.clear();
            });
        }


    }


    class MoveListener implements Runnable {
        boolean running = true;

        @Override
        public void run() {
            while(running) {
                if (!Client.getInstance().getMoves().empty()) {
                    HashMap info = Parser.parse(Client.getInstance().getMoves());
                    if (!info.get("PLAYER").equals(Player.getInstance().getName())) {
                        Platform.runLater(() -> {
                            int index = Integer.valueOf((String) info.get("MOVE"));
                            updateGameState(index, getMoveReceivesList(index));
                        });
                    }
                }
                if (Client.getInstance().getScore().size() > 0) {
                    getScore();
                    running = false;
                }
            }
        }
    }
    


}
