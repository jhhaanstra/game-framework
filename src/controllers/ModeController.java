package controllers;

import javafx.stage.Stage;
import models.ClientCommands;
import models.Settings;
import views.ModeView;

public class ModeController {
    ModeView view;
    Stage stage;

    public ModeController(Stage stage) {
        view = new ModeView();

        view.getAIButton().setOnMouseClicked(e -> {
            Settings.getInstance().setAI(true);
            new GameSelectController(stage);
        });

        view.getPlayerButton().setOnMouseClicked(e -> {
            Settings.getInstance().setAI(false);
            new GameSelectController(stage);
        });
        
//        view.getBackButton().setOnMouseClicked(e -> {
//            ClientCommands.logout();           
//            new LoginController(stage);
//        });

        this.stage = stage;
        this.stage.setScene(view);
    }
}