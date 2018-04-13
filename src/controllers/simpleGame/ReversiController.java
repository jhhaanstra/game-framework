package controllers.simpleGame;

import java.util.*;
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
    private ArrayList<Integer> choices = new ArrayList<>();
    private HashMap<Integer, List> save = new HashMap<>();

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

    public void updateGame() {
        super.updateGame();

        if (gameModel.isYourTurn()) {
            Platform.runLater(() -> {
                if (Settings.getInstance().getAI()) {
                    getPossibleList();
                    for (int i = 0; i < possMoves.size(); i++) {
                        List toChange = getMovesList(possMoves.get(i));
                        if (!toChange.isEmpty()) {
                            setHasHMap(possMoves.get(i), toChange);
                            choices.add(possMoves.get(i));
                        }
                    }
                    if (!choices.isEmpty()) {
                        if (choices.contains(0)) {
                            ClientCommands.sendMove(0);
                            updateGameState(0, save.get(0));
                        } else if (choices.contains(7)) {
                            ClientCommands.sendMove(7);
                            updateGameState(7, save.get(7));
                        } else if (choices.contains(56)) {
                            ClientCommands.sendMove(56);
                            updateGameState(56, save.get(56));
                        } else if (choices.contains(63)) {
                            ClientCommands.sendMove(63);
                            updateGameState(63, save.get(63));
                        } else {
                            Random rand = new Random();
                            int x = rand.nextInt(choices.size());
                            System.out.println("This is the random number " + x);
                            ClientCommands.sendMove(choices.get(x));
                            updateGameState(choices.get(x), save.get(choices.get(x)));
                        }
                        gameView.setTurn(gameModel.getOpponent());
                        gameModel.setYourTurn(false);
                    }

                    save.clear();
                    choices.clear();
                } else {
                    getPossibleList();
                    for (int i = 0; i < possMoves.size(); i++) {
                        List toChange = getMovesList(possMoves.get(i));
                        if (!toChange.isEmpty()) {
                            setOnClick(possMoves.get(i), toChange);
                        }
                    }
                    Player.getInstance().setTurn(false);
                    System.out.println("De beurt is " + Player.getInstance().getTurn());
                }

                check.clear();
                possMoves.clear();
            });
        }

    }


    public void setHasHMap(int index, List toChange) {
        save.put(index, toChange);
    }


    class MoveListener implements Runnable {
        boolean running = true;

        @Override
        public void run() {
            while(running) {
                if (!Client.getInstance().getMoves().empty()) {
                    HashMap info = Parser.parse(Client.getInstance().getMoves());
                    System.out.println("New Move");
                    System.out.println(info);
                    System.out.println("==================");
                    if (!info.get("PLAYER").equals(Player.getInstance().getName())) {
                        Platform.runLater(() -> {
                            int index = Integer.valueOf((String) info.get("MOVE"));
                            updateGameState(index, getMoveReceivesList(index));
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
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
