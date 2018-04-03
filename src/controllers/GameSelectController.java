package controllers;

import javafx.stage.Stage;
import models.Client;
import models.Server;
import views.GameSelectView;

public class GameSelectController {
    private GameSelectView view;
    private Server server;

    public GameSelectController(Stage primaryStage) {
        //server = new Client("127.0.0.1", 7789);
        //server.send("get gamelist");
        view = new GameSelectView();
        view.getTttButton().setOnMouseClicked(e -> {
            //new SimpleGameController(new Game(3, 3), primaryStage);
            Client.getInstance().send("login pascal");
        });
        primaryStage.setTitle("Choose a game!"); // Set the stage title
        primaryStage.setScene(view); // Place the scene in the stage
    }

    public GameSelectView getView() {
        return view;
    }
}
