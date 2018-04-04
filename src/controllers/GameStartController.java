package controllers;

import controllers.simpleGame.SimpleGameController;
import controllers.simpleGame.TicTacToeController;
import javafx.stage.Stage;
import models.ClientCommands;
import models.Game;
import views.GameLobbyView;
import views.GameView;
import views.TicTacToeView;

public class GameStartController {
    private GameLobbyView view;

    ClientCommands commands = new ClientCommands();

    public GameStartController(Stage primaryStage){
        view = new GameLobbyView();
        commands.getPlayers();
        String result = commands.getPlayers();
        String[] names = result.substring(16, result.length() - 2).split(", ");
        for (String name: names) {
            if (!view.getList().contains(name)){
                view.getList().add(name.replace("\"", "") + "\n");
            }
        }

        view.getStartButton().setOnMouseClicked(e -> {
            new TicTacToeController(new SimpleGameController(new Game(3, 3), primaryStage, (GameView) new TicTacToeView()));
        });

        primaryStage.setTitle("lobby");
        primaryStage.setScene(view);
    }

}