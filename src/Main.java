import controllers.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import models.Client;

import java.io.File;

public class Main extends Application {
    String musicFile = "src/mp3/8bitintro.mp3";     // For example

    public void start(Stage primaryStage) {
        new LoginController(primaryStage);
        primaryStage.show();
        primaryStage.setResizable(false); // added line to disable resizeable windows...


    }

    public static void main(String[] args){
        try {
            Client.getInstance().startConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
        launch(args);
    }
}