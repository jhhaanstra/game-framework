package controllers.simpleGame;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import models.*;
import lib.Parser;
import views.GameView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public abstract class SimpleGameController {
    protected Game gameModel;
    protected GameView gameView;
    protected HashMap gameInfo;
    protected ArrayList<Rectangle> listRectangles;
    ClientCommands commands = new ClientCommands();

    public SimpleGameController(Game model, Stage primaryStage, GameView gameView, HashMap info) {
        gameModel = model;
        this.gameView = gameView;
        gameInfo = info;
        gameView.setGrid(generateGrid(gameModel.getPlayField()));
        new Thread(new checkMoves()).start();
        primaryStage.setScene(this.gameView);
    }

    protected void setOnClick(int i) {
        Rectangle r = listRectangles.get(i);
        r.setOnMouseClicked(e -> {
            r.setFill(Color.RED);
            try {
                System.out.println(commands.sendMove(i));
                gameModel.incrementTurn();
                gameModel.updatePlayField(i, (gameModel.getTurn() % 2 == 0));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            r.setDisable(true);
        });
    }

    protected GridPane generateGrid(int[] playField) {
        GridPane grid = new GridPane();
        listRectangles = new ArrayList<>();
        Iterator it = gameInfo.entrySet().iterator();
        int count = 9;
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Label info = new Label(pair.getKey().toString());
            Label value = new Label(pair.getValue().toString());
            grid.add(info, 0, count);
            grid.add(value, 1, count);
            System.out.println(pair.getKey() + " = " + pair.getValue());
            count++;
        }

        for (int y = 0; y < gameModel.getGridHeight(); y++) {
            for (int x = 0; x < gameModel.getGridWidth(); x++) {
                int index = (y * 3) + x;
                Rectangle r = new Rectangle(100, 100);
                switch (playField[index]) {
                    case 0:
                        r.setFill(Color.WHITE);
                        break;
                    case 1:
                        r.setFill(Color.BLUE);
                        break;
                    case 2:
                        r.setFill(Color.RED);
                        break;
                }
                r.setStroke(Color.BLACK);
                listRectangles.add(r);
                grid.add(r, x, y);
            }
        }
        return grid;
    }

    protected boolean legalMove(int index) {
        return true;
    }

    public void updateGame() {
        gameView.setGrid(generateGrid(gameModel.getPlayField()));
    }

    public void updateView() {

    }

    public void fillRectangle(int index) {
        listRectangles.get(index).setFill(Color.BLUE);
        listRectangles.get(index).setDisable(true);
        System.out.println("Vakje " + index + " gekleurd voor de tegenstander.");
    }

    class checkMoves implements Runnable {
        @Override
        public void run() {
            while(true) {
                if (Client.getInstance().getMoves().size() > 0) {
                    HashMap info = Parser.parse(Client.getInstance().getMoves());

                    if (!Player.getInstance().getName().equals(info.get("PLAYER").toString())) {
                        System.out.println(info.get("PLAYER"));
                        System.out.println(info.get("MOVE"));
                        fillRectangle(Integer.valueOf((String) info.get("MOVE")));
                    }
                }
            }
        }
    }
}
