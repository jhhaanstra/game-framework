package controllers;

import javafx.scene.layout.GridPane;
import views.GameSelectView;

public class GameSelectController {
    private GameSelectView view;

    public GameSelectController() {
        view = new GameSelectView(new GridPane());
        view.getTttButton().setOnMouseClicked(e -> {
            System.out.println("Start tic tac toe!");
        });
    }

    public GameSelectView getView() {
        return view;
    }
}
