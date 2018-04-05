package controllers;

import javafx.stage.Stage;
import models.ClientCommands;
import models.Player;
import views.GameSelectView;

public class GameSelectController {
    private GameSelectView view;
    ClientCommands commands = new ClientCommands();

    public GameSelectController(Stage primaryStage) {
        //server = new Client("127.0.0.1", 7789);
        //server.send("get gamelist");
        view = new GameSelectView();
        view.getTttButton().setOnMouseClicked(e -> {
            commands.subscribeGame("Tic-tac-toe");
            Player.getInstance().setGame("Tic-tac-toe");
            controllers.GameStartController controller = new controllers.GameStartController(primaryStage);
            primaryStage.show();
        });
	
	view.getReversiButton().setOnMouseClicked(e -> {
	    commands.subscribeGame("Reversi");
        Player.getInstance().setGame("Reversi");
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