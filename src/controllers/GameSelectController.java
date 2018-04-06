package controllers;

import javafx.stage.Stage;
import models.ClientCommands;
import models.Player;
import views.GameSelectView;

public class GameSelectController {
    private GameSelectView view;

    public GameSelectController(Stage primaryStage) {
        view = new GameSelectView();
        view.getTttButton().setOnMouseClicked(e -> {
            Player.getInstance().setGame("Tic-tac-toe");
            ClientCommands.subscribeGame("Tic-tac-toe");
            new GameStartController(primaryStage);
            primaryStage.show();
        });
	
        view.getReversiButton().setOnMouseClicked(e -> {
            Player.getInstance().setGame("Reversi");
            ClientCommands.subscribeGame("Reversi");
            new GameStartController(primaryStage);
            primaryStage.show();
        });

        primaryStage.setTitle("Choose a game!");
        primaryStage.setScene(view);
    }

/*    public GameSelectView getView() {
        return view;
    }*/
}