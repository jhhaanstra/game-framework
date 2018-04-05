package controllers;

import javafx.stage.Stage;
import models.ClientCommands;
import javafx.scene.control.Label;
import models.Player;
import views.LoginView;

public class LoginController {
    private LoginView view;

    ClientCommands commands = new ClientCommands();

    public LoginController(Stage primaryStage){
        view = new LoginView();

        view.getLoginButton().setOnMouseClicked(e -> {
            String response = commands.login(view.getTextField().getText());
            Player.getInstance().setName(view.getTextField().getText());
            if (response.contains("ERR")) {
                Label error = new Label(response);
                view.getPane().add(error, 0, 3);
            } else {
                GameSelectController controller = new GameSelectController(primaryStage);
                primaryStage.show();
            }
        });

        primaryStage.setTitle("Login");
        primaryStage.setScene(view);
    }
}