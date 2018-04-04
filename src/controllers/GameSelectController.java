package controllers;

import controllers.simpleGame.SimpleGameController;
import javafx.stage.Stage;
import models.ClientCommands;
import models.Game;
import views.GameSelectView;

public class GameSelectController {
    private GameSelectView view;
    ClientCommands commands = new ClientCommands();

    public GameSelectController(Stage primaryStage) {
        view = new GameSelectView();
        view.getTttButton().setOnMouseClicked(e -> {
            commands.subscribeGame("Tic-tac-toe");
            controllers.GameStartController controller = new controllers.GameStartController(primaryStage);
            primaryStage.show();
        });
        primaryStage.setTitle("Choose a game!"); // Set the stage title
        primaryStage.setScene(view); // Place the scene in the stage
    }

    public GameSelectView getView() {
        return view;
    }
}