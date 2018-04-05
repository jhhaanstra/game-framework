package controllers.simpleGame;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.*;
import views.GameView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SimpleGameController implements GameController {
    private Game gameModel;
    private GameView gameView;
    private HashMap gameInfo;
    private ArrayList<Rectangle> listRectangles;


    ClientCommands commands = new ClientCommands();

    public SimpleGameController(Game model, Stage primaryStage, GameView gameView, HashMap info) {
        this.gameModel = model;
        this.gameView = gameView;
        this.gameInfo = info;
        gameView.setGrid(generateGrid(gameModel.getPlayField()));
        new Thread(new checkMoves()).start();
        primaryStage.setScene(this.gameView);
    }


    private GridPane generateGrid(int[] playField) {
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
	        int Height = y;
            for (int x = 0; x < gameModel.getGridWidth(); x++) {
                int index = (y * 3) + x;
		        int Width = x;
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

                r.setOnMouseClicked(e -> {
                    r.setFill(Color.RED);
                    try {
                        String value  = Double.toString(r.getX());
                        System.out.println(commands.sendMove(index));
                        gameModel.incrementTurn();
                        gameModel.updatePlayField(index, (gameModel.getTurn() % 2 == 0));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    r.setDisable(true);
                });
                grid.add(r, x, y);
            }
        }
        return grid;
    }

    @Override
    public boolean legalMove() {
        return true;
    }

    @Override
    public void updateGame(int index, int value) {
        if (legalMove()) {
            //gameModel.updatePlayField(index, value);
            gameView.setGrid(generateGrid(gameModel.getPlayField()));
        }

    }

    public void fillRectangle(int index) {
        listRectangles.get(index).setFill(Color.BLUE);
        listRectangles.get(index).setDisable(true);
        System.out.println("Vakje " + index + " gekleurd voor de tegenstander.");
    }

    @Override
    public void updateView() {
        //pass
    }


    class checkMoves implements Runnable {

        @Override
        public void run() {
            while(true) {
                if (Client.getInstance().getMoves().size() > 0) {
                    HashMap info = new HashMap();
                    String query = Client.getInstance().getMoves().pop();
                    String subsetValues = query.substring(1, query.length() - 1);
                    String strippedValues = subsetValues.replace("\"", "");
                    strippedValues = strippedValues.replace(",", "");
                    strippedValues = strippedValues.replace(":", "");
                    System.out.println(strippedValues);
                    String[] values = strippedValues.split(" ");

                    for (int x = 0; x < values.length - 1; x++) {
                        String value = values[x + 1];
                        info.put(values[x], value);
                        x++;
                    }

                    Iterator it = info.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry)it.next();
                        //System.out.println(pair.getKey() + " = " + pair.getValue());
                    }

                    if(!Player.getInstance().getName().equals(info.get("PLAYER").toString())) {
                        System.out.println(info.get("PLAYER"));
                        System.out.println(info.get("MOVE"));
                        fillRectangle(Integer.valueOf((String) info.get("MOVE")));
                    }

                }
            }
        }
    }
}
