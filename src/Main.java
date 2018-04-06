import controllers.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import models.Client;

public class Main extends Application {
    public void start(Stage primaryStage) {
        new LoginController(primaryStage);
        primaryStage.show();
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