package controllers;

import controllers.simpleGame.SimpleGameController;
import controllers.simpleGame.TicTacToeController;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import models.Client;
import models.ClientCommands;
import models.Game;
import views.GameLobbyView;
import views.GameView;
import views.TicTacToeView;

public class GameStartController {
    private GameLobbyView view;
    private Stage ticTacToe;

    ClientCommands commands = new ClientCommands();

    public GameStartController(Stage primaryStage){
        view = new GameLobbyView();

        new Thread(new updateLobby()).start();
        ticTacToe = primaryStage;

        view.getStartButton().setOnMouseClicked(e -> {
            new TicTacToeController(new SimpleGameController(new Game(3, 3), primaryStage, (GameView) new TicTacToeView()));
        });

        view.getChallengeButton().setOnMouseClicked(e -> {
            commands.challengePlayer(view.getPlayer());
            System.out.println(commands.challengePlayer(view.getPlayer()));
        });

        primaryStage.setTitle("lobby");
        primaryStage.setScene(view);
    }

    public void createTicTacToe() {
        new TicTacToeController(new SimpleGameController(new Game(3, 3), ticTacToe, (GameView) new TicTacToeView()));
    }

    class updateLobby implements Runnable {

        private int turnBit = 0;

        @Override
        public void run() {
            while (true) {
                Platform.runLater(() -> {
                    commands.getPlayers();
                    String result = commands.getPlayers();
                    String[] names = result.substring(16, result.length() - 2).split(", ");
                    for (String name: names) {
                        if (!view.getList().contains(name.replace("\"", "") + "\n")) {
                            view.getList().add(name.replace("\"", "") + "\n");
                        }
                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}