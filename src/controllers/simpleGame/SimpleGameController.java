package controllers.simpleGame;

import controllers.GameSelectController;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.*;
import lib.Parser;
import views.GameView;
import javafx.scene.control.Alert.AlertType;

import java.util.*;


public class SimpleGameController implements GameController {
    private Game gameModel;
    private GameView gameView;
    private HashMap gameInfo;
    private ArrayList<Rectangle> listRectangles;
    private ArrayList<Label> labels;
    private String opponent;
    private Stage primaryStage;


    ClientCommands commands = new ClientCommands();

    public SimpleGameController(Game model, Stage primaryStage, GameView gameView, HashMap info) {
        this.gameModel = model;
        this.gameView = gameView;
        this.gameInfo = info;
        this.primaryStage = primaryStage;
        gameView.setGrid(generateGrid(gameModel.getPlayField()));
        new Thread(new checkMoves()).start();
        primaryStage.setScene(this.gameView);
    }


    private GridPane generateGrid(int[] playField) {
        GridPane grid = new GridPane();
        listRectangles = new ArrayList<>();
        labels = new ArrayList<>();
        Iterator it = gameInfo.entrySet().iterator();
        int count = 9;
        Label player = new Label("NAME: ");
        Label name = new Label(Player.getInstance().getName());
        grid.add(player, 0, count);
        grid.add(name, 1, count);
        count++;
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Label info = new Label(pair.getKey().toString());
            Label value = new Label(pair.getValue().toString());
            labels.add(value);
            grid.add(info, 0, count);
            grid.add(value, 1, count);
            System.out.println(pair.getKey() + " = " + pair.getValue());
            count++;
        }
        opponent = gameInfo.get("OPPONENT").toString();

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
                        /*String value  = Double.toString(r.getX());*/
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
        // pass
    }

    public void updateTurn(String name) {
        Platform.runLater(() ->{
            labels.get(0).setText(name);
        });
    }

    public void getScore() {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            String response = Client.getInstance().getScore().pop();
            if (response.contains("WIN")) {
                alert.setHeaderText("Congratulations " + Player.getInstance().getName() + ", you won!!");
            } else if (response.contains("DRAW")) {
                alert.setHeaderText("The game between you and " + opponent + " ended in a draw.");
            } else {
                alert.setHeaderText("Sorry " + Player.getInstance().getName() + ", you lost..");
            }
            alert.setTitle("Score");
            alert.setContentText("You can back to the game select room.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                GameSelectController controller = new GameSelectController(primaryStage);
            }
        });
    }


    class checkMoves implements Runnable {

        boolean running = true;

        @Override
        public void run() {
            while(running) {
                if (Client.getInstance().getMoves().size() > 0) {
                    HashMap info = Parser.parse(Client.getInstance().getMoves());

                    if(!Player.getInstance().getName().equals(info.get("PLAYER").toString())) {
                        fillRectangle(Integer.valueOf((String) info.get("MOVE")));
                        updateTurn(Player.getInstance().getName());
                    } else {
                        updateTurn(opponent);
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
