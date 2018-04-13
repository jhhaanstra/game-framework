package controllers;

import controllers.simpleGame.TicTacToeController;
import controllers.simpleGame.ReversiController;

import javafx.application.Platform;
import models.*;
import views.ReversiView;
import javafx.stage.Stage;
import lib.Parser;
import views.GameLobbyView;
import views.TicTacToeView;

import java.util.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class GameStartController {
    private GameLobbyView view;
    private Stage stage;

    public GameStartController(Stage primaryStage){
        view = new GameLobbyView();
        new Thread(new LobbyListener()).start();
        updateListView();

        stage = primaryStage;


        view.getChallengeButton().setOnMouseClicked(e -> {
            ClientCommands.challengePlayer(view.getPlayer(), Player.getInstance().getGame());
            //System.out.println(ClientCommands.challengePlayer(view.getPlayer(), Player.getInstance().getGame()));
        });

        view.getRefreshButton().setOnMouseClicked(e -> {
            updateListView();
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
        TicTacToeController ttt = new TicTacToeController(new Game(3, 3), stage, new TicTacToeView(), gameInfo);
        /*if (Settings.getInstance().getAI()) {
            new AIController(ttt);
        }*/
    }

    public void createReversi() {
        HashMap gameInfo = getGameInfo();
        ReversiController reversi = new ReversiController(new Game(8, 8), stage, new ReversiView(), gameInfo);
        if (Settings.getInstance().getAI()) {
            reversi.setAi(new AIController(reversi));
        }
    }

    private void updateListView() {
        view.getList().clear();
        view.getListView().getItems().removeAll();

        String result = ClientCommands.getPlayers();
        // Fetch the names from the getPlayers command
        String[] names = result.substring(16, result.length() - 2).split(", ");
        for (String name: names) {
            name = name.replace("\"", "");
            if (!Player.getInstance().getName().equals(name)) {
                view.getList().add(name.replace("\"", "") + "\n");
            }
        }
    }
    
     public void challengeAlert() {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            HashMap info = Parser.parse(Client.getInstance().getChallenge());
            //String response = Client.getInstance().getChallenges().pop();
            alert.setTitle(info.get("CHALLENGER").toString() + " challenges you");
            alert.setHeaderText(info.get("CHALLENGER").toString() + " challenges you for " + info.get("GAMETYPE"));
            alert.setContentText("If you want to accept click ok");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Client.getInstance().send("challenge accept " + info.get("CHALLENGENUMBER"));
            }
        });
    } 

    class LobbyListener implements Runnable {
        private boolean running = true;
        @Override
        public void run() {
            while (running) {
                Platform.runLater(() -> {
                    try {
			if (!Client.getInstance().getChallenge().isEmpty()) {			    
			    challengeAlert();
			}
                        if (!Client.getInstance().getMatch().isEmpty()) {
                            running = false;
                            // Dit kan misschien weg, we weten nog niet of de server ook daadwerkelijk reversi stuurt als je ingeschreven staat op TicTacToe en vice versa...
                            if (Player.getInstance().getGame().equals("Tic-tac-toe")) {
                                createTicTacToe();
                            } else {
                                createReversi();
                            }
                        }
                    } catch (EmptyStackException e) {}
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