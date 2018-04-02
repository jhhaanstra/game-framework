package controllers;

import controllers.simpleGame.SimpleGameController;
import javafx.stage.Stage;
import models.Game;
import views.GameSelectView;

public class GameSelectController {
    private GameSelectView view;

    public GameSelectController(Stage primaryStage) {
        view = new GameSelectView();
        view.getTttButton().setOnMouseClicked(e -> {
            new SimpleGameController(new Game(3, 3), primaryStage);
        });
        primaryStage.setTitle("Choose a game!"); // Set the stage title
        primaryStage.setScene(view); // Place the scene in the stage
    }

    public GameSelectView getView() {
        return view;
    }
}
