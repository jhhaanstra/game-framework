package controllers;

import controllers.simpleGame.SimpleGameController;
import controllers.simpleGame.TicTacToeController;
import javafx.application.Platform;
import javafx.stage.Stage;
import lib.Parser;
import models.Client;
import models.ClientCommands;
import models.Game;
import views.GameLobbyView;
import views.TicTacToeView;

import java.util.*;

public class GameStartController {
    private GameLobbyView view;
    private Stage ticTacToe;
    private Thread lobbyListener;;


    ClientCommands commands = new ClientCommands();

    public GameStartController(Stage primaryStage){
        view = new GameLobbyView();

        lobbyListener = new Thread(new updateLobby());
        lobbyListener.start();
        ticTacToe = primaryStage;

        view.getStartButton().setOnMouseClicked(e -> {
           //
        });

        view.getChallengeButton().setOnMouseClicked(e -> {
            commands.challengePlayer(view.getPlayer());
            System.out.println(commands.challengePlayer(view.getPlayer()));
        });

        primaryStage.setTitle("lobby");
        primaryStage.setScene(view);
    }

    public HashMap getGameInfo() {
        int stackSize = Client.getInstance().getMatch().size();
        if (stackSize != 1) {
            Client.getInstance().getMatch().pop().replace("\"", "");
        }

        HashMap info = Parser.parse(Client.getInstance().getMatch());

        return info;


    }

    public void createTicTacToe() {
            HashMap gameInfo = getGameInfo();
            new TicTacToeController(new SimpleGameController(
                    new Game(3, 3), ticTacToe, new TicTacToeView(), gameInfo));
    }

    class updateLobby implements Runnable {

        private boolean running;

        @Override
        public void run() {
            running = true;
            while (running) {
                Platform.runLater(() -> {
                    commands.getPlayers();
                    String result = commands.getPlayers();
                    String[] names = result.substring(16, result.length() - 2).split(", ");
                    for (String name: names) {
                        if (!view.getList().contains(name.replace("\"", "") + "\n")) {
                            view.getList().add(name.replace("\"", "") + "\n");
                        }
                    }

                    try {
                        if (!Client.getInstance().getMatch().isEmpty()) {
                            running = false;
                            createTicTacToe();
                        }
                    } catch (EmptyStackException e) {

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